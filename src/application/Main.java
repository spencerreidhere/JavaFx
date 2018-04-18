package application;

import controller.MemoryController;
import gui.MemoryBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Memory --v0.1SNAPSHOT");

			final BorderPane root = new BorderPane();
			final Scene scene = new Scene(root, 801, 838);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			new MemoryBoard(root, new MemoryController());

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
