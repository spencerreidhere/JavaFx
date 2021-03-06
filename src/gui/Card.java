package gui;

import controller.MemoryController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Card extends StackPane {
	private final Text text = new Text();
	private final Rectangle border;
	private final EventHandler<? super MouseEvent> mouseClickEventHandler;
	private Integer memoryId;

	public Card(Integer memoryId, MemoryController controller, final MemoryBoard memoryBoard) {
		this.memoryId = memoryId;
		border = new Rectangle(200, 200);
		border.setFill(Color.WHITE);
		border.setStroke(Color.BLACK);

		text.setFont(Font.font(72));

		setAlignment(Pos.CENTER);
		getChildren().addAll(border);
		getChildren().addAll(text);

		setOnMouseClicked(event -> {
			text.setText("M " + this.memoryId);
			if (!controller.isCheckTime()) {
				controller.setFirstCard(this);
				controller.setCheckTime(true);
			} else {
				if (!controller.getFirstCard().equals(this)) {
					memoryBoard.disable(true);
					// wait 0,5 second
					final Timeline tl = new Timeline(new KeyFrame(Duration.millis(500), e -> {
						controller.setSecondCard(this);
						controller.check();
						controller.setCheckTime(false);

						memoryBoard.disable(false);
					}));
					tl.setCycleCount(1);
					tl.play();
				}
			}
		});

		mouseClickEventHandler = getOnMouseClicked();

	}

	protected void activate() {
		setOnMouseClicked(mouseClickEventHandler);
	}

	private void deactivate() {
		setOnMouseClicked(null);
	}

	public String getValue() {
		return text.getText();
	}

	public void setValue(final String text) {
		this.text.setText(text);
	}

	public Rectangle getRectangleBorder() {
		return border;
	}

	protected void updateMemoryId(final int newMemoryId) {
		this.memoryId = newMemoryId;
	}

	public void setFound() {
		border.setFill(Color.GREENYELLOW);
		deactivate();
	}

}
