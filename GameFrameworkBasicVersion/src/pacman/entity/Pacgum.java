package pacman.entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.Overlappable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Pacgum implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image = null;
	protected Point position;
	public static final int RENDERING_SIZE = 16;

	public Pacgum(Canvas defaultCanvas, Point pos) {
		image = new DrawableImage("images/pacgum.gif", defaultCanvas);
		position = pos;
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
				null);

	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}
}
