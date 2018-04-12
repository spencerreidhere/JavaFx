package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GameGUI {

	public GameGUI(final BorderPane borderPane) {

		addTop(borderPane);
	}

	private void addTop(BorderPane borderPane) {
		final HBox hBox = new HBox();
		// style
		hBox.setPadding(new Insets(5));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color: #336699;");

		final Button button = new Button("Neues Spiel");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Neues Spiel");
			}
		});

		hBox.getChildren().add(button);
		borderPane.setTop(hBox);
	}

}
