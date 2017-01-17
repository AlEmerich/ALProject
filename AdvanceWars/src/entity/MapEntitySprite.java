package entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.Overlappable;
import util.ImageUtility;

import java.awt.*;

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
		Land(1,0),
		Tree(6,4),
		Mountain(6,10),

		RoadVertical(0,2),
		RoadHorizontal(1,3),
		RoadLeftBottom(0,3),
		RoadLeftUp(1,2),
		RoadRightBottom(2,3),
		RoadRightUp(2,2),
		BridgeH(22,0),

		WaterBottom(10,0),
		WaterUp(10,2),
		WaterLeftBottom(11,1),
		WaterLeftUp(11,2),
		WaterRightBottom(9,1),
		WaterRightUp(9,2),
		WaterRight(8,1),
		WaterLeft(8,5),

		Water(9,0),
		WaterOut(9,8),
		RiverH(12,3)
		;

		private final int x,y;

		MapEntityType(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public MapEntitySprite(Canvas defaultCanvas, int xx, int yy, MapEntityType t) {
		if(image == null)
			image = new DrawableImage(ImageUtility.getResource("map1.png"), defaultCanvas);
		x = xx;
		y = yy;
		this.type = t;
	}

	public void draw(Graphics g) {

		if(this.type == MapEntityType.Mountain || this.type == MapEntityType.Tree)
		{
			g.drawImage(image.getImage(), x, y-3, x+RENDERING_SIZE , y+RENDERING_SIZE
					, type.x*RENDERING_SIZE, type.y*RENDERING_SIZE-3,
					(type.x+1)*RENDERING_SIZE,(type.y+1)*RENDERING_SIZE, null);
		}
		else
			g.drawImage(image.getImage(), x, y, x+RENDERING_SIZE, y+RENDERING_SIZE
				, type.x*RENDERING_SIZE, type.y*RENDERING_SIZE,
				(type.x+1)*RENDERING_SIZE,(type.y+1)*RENDERING_SIZE, null);
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}
}
