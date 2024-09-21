package io.github.asbyx.autonomousstreamset;

import io.github.asbyx.autonomousstreamset.controllers.RunningController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	private static Stage stage;
	private static Object currentController;

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
			currentController = fxmlLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}

	/**
	 * Handle the close of the application.
	 */
	@Override
	public void stop() {
		// If the scene is the running one, call the close method of the controller
		if (currentController instanceof RunningController) {
			((RunningController) currentController).close();
		}
		System.exit(0);
	}
}