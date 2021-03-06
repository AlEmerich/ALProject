package gameframework.core;

import java.awt.*;

public class DrawableImage implements Drawable {
	protected Image image;
	protected Canvas canvas;

	public DrawableImage(Image image,Canvas canvas)
	{
		this.canvas = canvas;
		this.image = image;

		MediaTracker tracker = new MediaTracker(canvas);
		tracker.addImage(image, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
		}
	}

	public DrawableImage(String filename, Canvas canvas) {
		this.canvas = canvas;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		image = toolkit.createImage(filename);

		MediaTracker tracker = new MediaTracker(canvas);
		tracker.addImage(image, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
		}
	}

	public void setImage(Image image){ this.image = image;}

	public Image getImage() {
		return image;
	}

	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, canvas);
	}
}
