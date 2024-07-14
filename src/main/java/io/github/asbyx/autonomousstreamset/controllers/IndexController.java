package io.github.asbyx.autonomousstreamset.controllers;

import io.github.asbyx.autonomousstreamset.Main;
import io.github.asbyx.autonomousstreamset.config.ConfigManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IndexController {
	@FXML
	public TextField streamName;

	@FXML
	public TextField APIToken;

	@FXML
	public TextField tournamentSlug;

	@FXML
	public TextField eventSlug;

	@FXML
	public void start(ActionEvent actionEvent) {
		// use the ConfigManager class to write the config file
		ConfigManager.writeConfig(streamName.getText(), APIToken.getText(), tournamentSlug.getText(), eventSlug.getText());

		Main.switchScene("running.fxml");
	}

	@FXML
	public void initialize() {
		// use the ConfigManager class to read the config file
		String[] config = ConfigManager.readConfig();
		streamName.setText(config[0]);
		APIToken.setText(config[1]);
		tournamentSlug.setText(config[2]);
		eventSlug.setText(config[3]);

	}
}