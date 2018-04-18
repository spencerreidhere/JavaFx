package gui;

import controller.MemoryController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Card extends StackPane {
	private final Text text = new Text();
	private final Rectangle border;

	public Card(final Integer memoryId, MemoryController controller, final MemoryBoard memoryBoard) {
		border = new Rectangle(200, 200);
		border.setFill(null);
		border.setStroke(Color.BLACK);

		text.setFont(Font.font(72));

		setAlignment(Pos.CENTER);
		getChildren().addAll(border);
		getChildren().addAll(text);

		setOnMouseClicked(event -> {
			text.setText("M " + memoryId);
			if (!controller.isCheckTime()) {
				controller.setFirstCard(this);
				controller.setCheckTime(true);
			} else {
				memoryBoard.disable(true);
				// wait 1 second
				final Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
					controller.setSecondCard(this);
					controller.check();
					controller.setCheckTime(false);

					memoryBoard.disable(false);
				}));
				tl.setCycleCount(1);
				tl.play();
			}
		});

	}

	public String getValue() {
		return text.getText();
	}

	public void setValue(final String text) {
		this.text.setText(text);
	}

	public void setFound() {
		border.setFill(Color.GREENYELLOW);
	}

}
