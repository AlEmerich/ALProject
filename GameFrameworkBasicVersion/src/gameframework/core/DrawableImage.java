package gameframework.core;

import java.awt.*;

public class DrawableImage implements Drawable {
	protected Image image;
	protected Canvas canvas;

	public DrawableImage(String filename, Canvas canvas) {
		this.canvas = canvas;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		// Implement to work on intelij
		image = toolkit.createImage(filename);
		MediaTracker tracker = new MediaTracker(canvas);
		tracker.addImage(image, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
		}
	}

	public Image getImage() {
		return image;
	}

	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, canvas);
	}
}
