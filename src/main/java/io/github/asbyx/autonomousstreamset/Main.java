package io.github.asbyx.autonomousstreamset;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	private static Stage stage;

	@Override
	public void start(Stage stage) throws IOException {
		Main.stage = stage;
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("index.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 600, 400);

		Main.stage.setTitle("Autonomous Stream Set!");
		Main.stage.setScene(scene);
		Main.stage.show();
	}

	public static void switchScene(String fxml) {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
		try {
			Scene scene = new Scene(fxmlLoader.load(), 600, 400);
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}
}