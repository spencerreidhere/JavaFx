package gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.MemoryController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MemoryBoard implements Observer {

	private final Card[][] boardCards = new Card[4][4];
	private final Pane pane = new Pane();
	private final MemoryController memoryController;
	private final BorderPane borderPane;

	public MemoryBoard(final BorderPane borderPane, final MemoryController memoryController) {
		this.borderPane = borderPane;
		this.memoryController = memoryController;

		memoryController.addObserver(this);

		addTop();
		addCenter();
	}

	private void addTop() {
		final HBox hBox = new HBox();
		hBox.setPadding(new Insets(5));
		hBox.setSpacing(10);
		hBox.setStyle("-fx-background-color: #336699;");

		final Button button = new Button("Neues Spiel");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (final Card[] cards : boardCards) {
					for (final Card card : cards) {
						card.setValue("");
					}
				}
			}
		});

		hBox.getChildren().add(button);
		borderPane.setTop(hBox);
	}

	private void addCenter() {
		final List<Integer> memoryIds = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8);
		Collections.shuffle(memoryIds);
		Integer memoryId = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				final Card card = new Card(memoryIds.get(memoryId), memoryController, this);
				memoryId++;
				card.setTranslateX(j * 200);
				card.setTranslateY(i * 200);
				pane.getChildren().add(card);
				boardCards[j][i] = card;
			}
		}
		borderPane.setCenter(pane);
	}

	protected void disable(final boolean disable) {
		for (final Card[] cards : boardCards) {
			for (final Card card : cards) {
				card.setDisable(disable);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (memoryController.getFirstCard().getValue().equals(memoryController.getSecondCard().getValue())) {
			memoryController.getFirstCard().setFound();
			memoryController.getSecondCard().setFound();
		} else {
			memoryController.getFirstCard().setValue("");
			memoryController.getSecondCard().setValue("");
		}
	}

}
