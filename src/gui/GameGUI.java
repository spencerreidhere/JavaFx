package gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameGUI {

	private final BorderPane borderPane;
	private final Tile[][] board = new Tile[2][2];
	private final Pane pane = new Pane();

	private boolean checkState = false;

	public GameGUI(final BorderPane borderPane) {
		this.borderPane = borderPane;

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
				for (final Tile[] tiles : board) {
					for (final Tile tile : tiles) {
						tile.setValue("");
					}
				}
			}
		});

		hBox.getChildren().add(button);
		borderPane.setTop(hBox);
	}

	private void addCenter() {
		final List<Integer> memoryIds = Arrays.asList(1, 1, 2, 2);
		Collections.shuffle(memoryIds);
		Integer memoryId = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				final Tile tile = new Tile(memoryIds.get(memoryId));
				memoryId++;
				tile.setTranslateX(j * 200);
				tile.setTranslateY(i * 200);
				pane.getChildren().add(tile);
				board[j][i] = tile;
			}
		}
		borderPane.setCenter(pane);
	}

	private class Tile extends StackPane {
		private final Text text = new Text();

		public Tile(final Integer memoryId) {
			final Rectangle border = new Rectangle(200, 200);
			border.setFill(null);
			border.setStroke(Color.BLACK);

			text.setFont(Font.font(72));

			setAlignment(Pos.CENTER);
			getChildren().addAll(border);
			getChildren().addAll(text);

			setOnMouseClicked(event -> {
				text.setText("M " + memoryId);
				if (!checkState) {
					checkState = true;
				} else {
					checkState(this);
					checkState = false;
				}
			});

		}

		public String getValue() {
			return text.getText();
		}

		public void setValue(final String text) {
			this.text.setText(text);
		}

	}

	private void checkState(Tile tile) {
		final Tile tile1 = board[0][0];
		final Tile tile2 = board[0][1];
		final Tile tile3 = board[1][0];
		final Tile tile4 = board[1][1];

		final List<Tile> tiles = Arrays.asList(tile1, tile2, tile3, tile4);
		boolean found = false;
		for (final Tile testTile : tiles) {
			if (tile.getValue().equals(testTile.getValue()) && !tile.equals(testTile)) {
				found = true;
				break;
			}
		}
		if (!found) {
			for (final Tile testTile : tiles) {
				testTile.setValue("");
			}
		}

	}

}
