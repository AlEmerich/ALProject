package entity;

import gameframework.core.GameEntity;
import gameframework.moves_rules.MoveBlocker;

import java.awt.*;

/**
 * Created by alaguitard on 17/01/17.
 */
public class EmptyBlocker implements MoveBlocker, GameEntity {

	int x, y;
	public static final int RENDERING_SIZE = 16;

	public EmptyBlocker(int xx, int yy) {
		x = xx;
		y = yy;
	}

	public void draw(Graphics g) {

	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}
}
