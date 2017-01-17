package entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.Overlappable;
import gameframework.moves_rules.MoveBlocker;

import java.awt.*;
import java.util.Map;

/**
 * Created by alaguitard on 17/01/17.
 */
public class MapEntitySprite implements Drawable,GameEntity, Overlappable{
    protected static DrawableImage image = null;
	int x, y;
	public static final int RENDERING_SIZE = 16;
	private MapEntityType type;

	@Override
	public Point getPosition() {
		return null;
	}

	public enum MapEntityType{
		Tree(6,4),
		Land(0,1),
		RoadVertical(0,2),
		RoadHorizontal(1,3),
		RoadLeftBottom(0,3),
		RoadLeftUp(1,2),
		RoadRightUp(2,2),
		RoadRightBottom(2,3)
		;

		private final int x,y;

		MapEntityType(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public MapEntitySprite(Canvas defaultCanvas, int xx, int yy, MapEntityType t) {
		if(image == null)
			image = new DrawableImage("map1.png", defaultCanvas);
		x = xx;
		y = yy;
		this.type = t;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, x+RENDERING_SIZE, y+RENDERING_SIZE
		, type.x*RENDERING_SIZE, type.y*RENDERING_SIZE, (type.x+1)*RENDERING_SIZE,(type.y+1)*RENDERING_SIZE, null);
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}
}
