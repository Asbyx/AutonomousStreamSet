package io.github.asbyx.autonomousstreamset.config;

import org.json.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Class that reads the config file
 * Contains two methods:
 * - readConfig: reads the config file and returns an array of strings containing the API token, tournament slug, and event slug
 * - writeConfig: writes the config file with the given API token, tournament slug, and event slug
*/
public class ConfigManager {
	private static final String CONFIG_FILE_PATH = "src/main/resources/io/github/asbyx/autonomousstreamset/config/config.json";

	public static String[] readConfig() {
		try {
			String content = new String(Files.readAllBytes(Paths.get(CONFIG_FILE_PATH)));
			JSONObject jsonObject = new JSONObject(content);

			String streamName = jsonObject.getString("stream_name");
			String apiToken = jsonObject.getString("api_token");
			String tournamentSlug = jsonObject.getString("tournament_slug");
			String eventSlug = jsonObject.getString("event_slug");

			return new String[]{streamName, apiToken, tournamentSlug, eventSlug};
		} catch (IOException e) { //todo: errors handling (file not found, wrong format, etc.)
			e.printStackTrace();
			return new String[]{"", "", "", ""};
		}
	}

	public static void writeConfig(String streamName, String apiToken, String tournamentSlug, String eventSlug) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("stream_name", streamName);
			jsonObject.put("api_token", apiToken);
			jsonObject.put("tournament_slug", tournamentSlug);
			jsonObject.put("event_slug", eventSlug);

			FileWriter fileWriter = new FileWriter(CONFIG_FILE_PATH);
			fileWriter.write(jsonObject.toString(4)); // 4 is the number of spaces for indentation
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
