package pacman.entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.moves_rules.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Wall implements Drawable, MoveBlocker, GameEntity {
	protected static DrawableImage image = null;
	int x, y;
	public static final int RENDERING_SIZE = 16;

	public Wall(Canvas defaultCanvas, int xx, int yy) {
		image = new DrawableImage("images/wall.gif", defaultCanvas);
		x = xx;
		y = yy;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, RENDERING_SIZE, RENDERING_SIZE,
				null);
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}
}
