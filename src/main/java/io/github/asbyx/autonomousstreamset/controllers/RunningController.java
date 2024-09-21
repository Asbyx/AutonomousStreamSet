package io.github.asbyx.autonomousstreamset.controllers;

import io.github.asbyx.autonomousstreamset.Main;
import io.github.asbyx.autonomousstreamset.config.ConfigManager;
import io.github.asbyx.autonomousstreamset.obs_files.OBSFilesUpdater;
import io.github.asbyx.autonomousstreamset.queries.GraphQLManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class RunningController {
	@FXML
	public Text status;
	@FXML
	public Text logs;

	private Integer eventId;
	private String[] config;
	private final Thread streamInfoThread = new Thread(() -> {
		// call "getStreamedSet" every 2 seconds
		LocalTime lastUpdate = LocalTime.now();
		while (true) {
			String time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));

			String[] set = getStreamedSet(eventId, config[0]);
			if (set != null) {
				// logs
				updateLogs("[" + time + "] Streamed set found !");

				// update the files
				String error = OBSFilesUpdater.updateFiles(set);
				if (error == null) {
					status.setText("Updater running correctly !");
					lastUpdate = LocalTime.now();
				}
				else {
					status.setText("Error with the file updater.");
					updateLogs(error);
				}
			} else {
				updateLogs("[" + time + "] No streamed set found. Can take up to 1 minute.");
				// if the last update was more than 1 minute ago, display an error
				if (LocalTime.now().minusMinutes(1).isAfter(lastUpdate)) {
					status.setText("Troubles getting the streamed set...");
					updateLogs("No streamed set found for more than 1 minute. \n" +
							"Check on start.gg that you have launched the streamed set with the stream named \"" + config[0] + "\".\n" +
							"[" + time + "] No streamed set found.");
				}
			}

			// wait 2 seconds
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				break;
			}
		}
	});

	public void updateLogs(String log) {
		logs.setText(log);
	}

	@FXML
	public void back() {
		close();
		Main.switchScene("index.fxml");
	}

	/**
	 * Handles the close of the application.
	 */
	@FXML
	public void close() {
		streamInfoThread.interrupt();
		OBSFilesUpdater.close();
		System.out.println("Scene correctly closed");
	}


	/**
	 * Initializes the controller.
	 */
	@FXML
	public void initialize() {
		OBSFilesUpdater.start();

		// launch a thread to get the event id
		new Thread(() -> {
			config = ConfigManager.readConfig();
			GraphQLManager.setApiToken(config[1]);

			eventId = getEventId(config[2], config[3]);
			if (eventId == null) return;
			updateLogs("Event found ! ID: " + eventId);

			status.setText("Getting streamed set...");
			// launches the thread that will get information from the streamed set.
			streamInfoThread.start();
		}).start();
	}

	public Integer getEventId(String tournamentSlug, String eventSlug) {
		// make the query
		String query = """
				query getEventId($tournament: String){
				          tournament(slug: $tournament){
				            events {
				                slug
				                id
				            }
				          }
				        }""";
		Map<String, String> variables = Map.of("tournament", tournamentSlug);

		String response = null;
		try {
			response = GraphQLManager.executeGraphQLQuery(query, variables);
		} catch (Exception e) {
			status.setText("Error");
			updateLogs(e.getMessage());
			return null;
		}

		// parse the response
		JSONObject json = new JSONObject(response).getJSONObject("data");
		if (json.isNull("tournament")) {
			status.setText("Error");
			updateLogs("Tournament not found. Check the if the configurations are correct.");
			return null;
		}

		// get the event id from the response
		JSONArray events = json.getJSONObject("tournament").getJSONArray("events");
		Integer eventId = null;
		for (int i = 0; i < events.length(); i++) {
			JSONObject event = events.getJSONObject(i);
			if (event.getString("slug").substring(event.getString("slug").lastIndexOf("/") + 1).equals(eventSlug)) {
				eventId = event.getInt("id");
				break;
			}
		}

		if (eventId == null) {
			status.setText("Error");
			updateLogs(events.toString());
			updateLogs("Event not found. Check the if the configurations are correct.");
			return null;
		}
		return eventId;
	}

	public String[] getStreamedSet(Integer eventId, String streamName) {
		// make the query
		String query = """
				query getInProgressSets($id: ID) {
				        event(id: $id){
				            sets (filters: {
				                state: 2 # = in progress
				            }){
				                nodes {
				                    stream {
				                        streamName
				                    }
				                    fullRoundText
				                    slots {
				                        entrant {
				                            name
				                        }
				                        standing {
				                            stats {
				                                score {
				                                    value
				                                }
				                            }
				                        }
				                    }
				                }
				            }
				        }
				    }
				""";
		Map<String, String> variables = Map.of("id", eventId.toString());

		String response;
		try {
			response = GraphQLManager.executeGraphQLQuery(query, variables);
		} catch (Exception e) {
			status.setText("Error");
			updateLogs(e.getMessage());
			return null;
		}

		// parse the response
		JSONObject json = new JSONObject(response).getJSONObject("data");
		if (json.isNull("event")) {
			status.setText("Error");
			updateLogs("Event not found. Check the if the configurations are correct.");
			return null;
		}

		// check if a streamed set is found
		JSONArray sets = json.getJSONObject("event").getJSONObject("sets").getJSONArray("nodes");
		if (sets.isEmpty()) {
			updateLogs("No streamed set found.");
			return null;
		}

		// get the streamed set data: for each streamed set, check that it is the correct stream, and if yes get the entrants, scores and round
		for (int i = 0; i < sets.length(); i++) {
			JSONObject set = sets.getJSONObject(i);
			String setStreamName = set.getJSONObject("stream").getString("streamName");
			if (!Objects.equals(streamName, setStreamName)) continue;

			String round = set.getString("fullRoundText");
			JSONArray slots = set.getJSONArray("slots");

			String[] data = new String[5];
			for (int j = 0; j < 2; j++) {
				JSONObject slot = slots.getJSONObject(j);

				String entrant = slot.getJSONObject("entrant").getString("name");

				String score;
				if (slot.getJSONObject("standing").getJSONObject("stats").getJSONObject("score").isNull("value")) score = "0";
				else score = String.valueOf(slot.getJSONObject("standing").getJSONObject("stats").getJSONObject("score").getInt("value"));

				data[2*j] = entrant;
				data[2*j + 1] = score;
			}
			data[4] = round;
			return data;
		}

		// this means that no streamed set with the correct stream name was found
		updateLogs("No streamed set found.");
		return null;
	}
}
