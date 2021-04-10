package work.tinax.midij.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import work.tinax.midij.data.Event;
import work.tinax.midij.data.EventVisitor;
import work.tinax.midij.data.Track;

public class PianoRoll extends Pane {
	public static final int WHITE_KEYS = 69;
	
	private Canvas canvas;
	private GraphicsContext gc;
	private double scrollBarSize = 30;
	private double offsetX = 0, offsetY = 0;
	private double whiteWidth = 60.0,
			       whiteHeight = 30.0,
			       blackWidth = 25.0,
			       blackHeight = 20.0,
			       noteHeight = whiteHeight*WHITE_KEYS/128.0,
			       beatWidth = 75.0;
	private final double[] scaleHeightCache = new double[128];
	
	private Track currentTrack = null;
	
	public PianoRoll(double width, double height) {
		super();
		canvas = new Canvas(width, height);
		getChildren().add(canvas);
		
		gc = canvas.getGraphicsContext2D();
		
		setWidth(width);
		setHeight(height);
		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				canvas.setWidth(newValue.doubleValue());
				clearAndDraw();
			}
		});
		heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				canvas.setHeight(newValue.doubleValue());
				clearAndDraw();
			}
		});
		
		canvas.setOnMouseClicked(new OnMouseClickHandler());
		canvas.setOnScroll(new OnScrollHandler());
		
		clearAndDraw();
	}
	
	private void clearAndDraw() {
		gc.clearRect(0, 0, getWidth(), getHeight());
		drawScrollBar();
		drawKeyboard();
		drawTimeline();
		drawNotes();
		drawPendingNotes();
	}

	private void drawScrollBar() {
		double width = getWidth();
		double height = getHeight();
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);
		gc.strokeRect(0, height-scrollBarSize, width, scrollBarSize);
		gc.strokeRect(width-scrollBarSize, 0, scrollBarSize, height-scrollBarSize);
		
		gc.setStroke(Color.BLACK);
		final double fullHeight = whiteHeight * WHITE_KEYS;
		final double offsetYrate = offsetY / (fullHeight - height);
		final double yViewRate = height / fullHeight;
		final double verticalScrollBoxSize = height - scrollBarSize*3;
		final double verticalBoxSize = verticalScrollBoxSize * yViewRate;
		final double verticalBoxY = scrollBarSize * (1-offsetYrate) + (height-scrollBarSize*2-verticalBoxSize) * offsetYrate;
		gc.strokeRect(width-scrollBarSize, verticalBoxY, scrollBarSize, verticalBoxSize);
	}
	
	class NoteDrawer implements EventVisitor {
		
		private NoteDrawBounds bounds;

		NoteDrawer(NoteDrawBounds bounds) {
			this.bounds = bounds;
		}
		
		private NoteDrawnPosition drawNote(int scale, int startTick, int duration, NoteDrawBounds bounds) {
			double endCoord = calculateHCoordFromTick(startTick + duration);
			if (endCoord < bounds.left) {
				return NoteDrawnPosition.LEFT;
			}
			double startCoord = calculateHCoordFromTick(startTick);
			if (startCoord > bounds.right) {
				return NoteDrawnPosition.RIGHT;
			}
			double scaleHeight = calculateVCoordFromScale(scale);
			if (scaleHeight < bounds.upper) {
				return NoteDrawnPosition.UP;
			} else if (scaleHeight > bounds.lower) {
				return NoteDrawnPosition.DOWN;
			}
			if (startCoord < endCoord) {
				gc.fillRect(startCoord, scaleHeight, endCoord-startCoord, noteHeight);
				return NoteDrawnPosition.CORRECT;
			} else {
				return NoteDrawnPosition.INVALID;
			}
		}
		
		public boolean shouldBreak = false;

		@Override
		public void handleNote(Event event, int scale, int duration, int velocity) {
			var result = drawNote(scale, event.getAbsTick(), duration, bounds);
			if (result == NoteDrawnPosition.RIGHT) shouldBreak = true;
		}

		@Override
		public void handleGMReset(Event event) {
		}
		
	}
	
	private double calculateHCoordFromTick(int absTick) {
		int resolution = Event.TPQ;
		return beatWidth * (absTick / (double)resolution);
	}
	
	private double calculateVCoordFromScale(int scale) {
		return scaleHeightCache[1 + scale];
	}

	private void drawPendingNotes() {
		// TODO Auto-generated method stub
		
	}

	private void drawNotes() {
		if (currentTrack == null) return;
		var events = currentTrack.unmodifiableEvents();
		var drawer = new NoteDrawer(fullNoteDrawBounds());
		for (var event : events) {
			event.visit(drawer);
			if (drawer.shouldBreak) break;
		}
	}

	private NoteDrawBounds fullNoteDrawBounds() {
		return new NoteDrawBounds(
				offsetX, offsetX + getWidth(),
				offsetY, offsetY + getHeight()
				);
	}

	private void drawTimeline() {
		double interval = beatWidth;
		
		int count = 0;
		double x = interval * count + whiteWidth - offsetX;
		while (x < getWidth()-scrollBarSize) {
			if (whiteWidth <= x) {
				if (count % 4 != 0) {
					gc.setStroke(new Color(0.7, 0.7, 0.7, 0.7));
				} else {
					gc.setStroke(new Color(0.4, 0.4, 0.4, 1.0));
				}
				gc.strokeLine(x, 0, x, getHeight()-scrollBarSize);
				if (count % 4 == 0) {
					gc.fillText(Integer.toString(count/4+1), x+blackWidth, whiteHeight);
				}
			}
			x = interval * ++count + whiteWidth - offsetX;
		}
	}

	private void drawKeyboard() {
		gc.setLineWidth(1);
		boolean firstLine = true;
		int processingScale = 127;
		double lastC = 0.0;
		
		for (int i=0; i<WHITE_KEYS-1; i++) {
			final double height = i * whiteHeight - offsetY;
			final boolean isC = (i-4) % 7 == 0;
			
			gc.setStroke(Color.BLACK);
			gc.strokeRect(0, height, whiteWidth, whiteHeight);
			
			gc.setFill(Color.WHITE);
			gc.fillRect(0, height, whiteWidth, whiteHeight);
			
			if (isC) {
				gc.setFill(Color.BLACK);
				int index = (i-4)/7;
				String text = String.format("C%d", 9-index);
				gc.fillText(text, whiteWidth/4.0, (i+1.0)*whiteHeight-5.0-offsetY);
				
				final double currC = height + whiteHeight + offsetY;
				int nKeys;
				if (firstLine) {
					firstLine = false;
					nKeys = 8;
				} else {
					nKeys = 12;
				}
				final double hLineInterval = (currC-lastC) / nKeys;
				gc.setStroke(new Color(0.8, 0.8, 0.8, 0.8));
				for (int j=1; j<=nKeys; j++) {
					final double y = lastC + hLineInterval * j - offsetY;
					scaleHeightCache[processingScale] = y;
					if (y < getHeight() - scrollBarSize) { // avoid overdrawing on the horizontal scrollbar
						if (j == nKeys) {
							gc.setStroke(new Color(0.2, 0.2, 0.2, 0.8));
						}
						gc.strokeLine(whiteWidth, y, getWidth()-scrollBarSize, y);
					}
					processingScale--;
				}
				lastC = currC;
			}	
		}
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0.0, -offsetY, blackWidth, blackHeight/2.0);
		
		int blackIndex = 2;
		for (int i=0; i<WHITE_KEYS; i++) {
			if (blackIndex == 3) {
				blackIndex += 1;
				continue;
			} else if (blackIndex == 6) {
				blackIndex = 0;
				continue;
			} else {
				final double leftUpY = whiteHeight * (i+1.0) - blackHeight/2.0 - offsetY;
				gc.fillRect(0.0, leftUpY, blackWidth, blackHeight);
				blackIndex++;
			}
		}
	}

	class OnMouseClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			var x = event.getX();
			var y = event.getY();
			var width = getWidth();
			var height = getHeight();
			// check if the position is in scrollbars
			if (x > whiteWidth && height-scrollBarSize<y) {
				handleOnScrollBar(event, true);
				return;
			} else if (x > width-scrollBarSize) {
				handleOnScrollBar(event, false);
				return;
			}
			
		}
		
		private void handleOnScrollBar(MouseEvent event, boolean horizontal) {
			if (horizontal) {
				// TODO
			} else {
				offsetY += 20.0;
				clearAndDraw();
			}
		}
		
		private void handleOnPianoroll(MouseEvent event) {
			
		}
	}
	
	class OnScrollHandler implements EventHandler<ScrollEvent> {
		@Override
		public void handle(ScrollEvent event) {
			double y = event.getDeltaY();
			offsetY -= y;
			clearAndDraw();
		}
	}
}

enum NoteDrawnPosition {
	CORRECT,
	INVALID,
	UP, DOWN, LEFT, RIGHT
}

class NoteDrawBounds {
	double left, right, upper, lower;
	NoteDrawBounds(double left, double right, double upper, double lower) {
		this.left = left;
		this.right = right;
		this.upper = upper;
		this.lower = lower;
	}
}
