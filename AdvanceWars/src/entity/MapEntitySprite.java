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

	//0: None, 1:possible, 2:oneWay
	private int filter=0;

	@Override
	public Point getPosition() {
		return (new Point(x, y));
	}

	public enum MapEntityType{
		Land(1,0,true),
		Tree(6,4,true),
		Mountain(6,10,false),

		RoadVertical(0,2,true),
		RoadHorizontal(1,3,true),
		RoadLeftBottom(0,3,true),
		RoadLeftUp(1,2,true),
		RoadRightBottom(2,3,true),
		RoadRightUp(2,2,true),
		BridgeH(22,0,true),

		WaterBottom(10,0,false),
		WaterUp(10,2,false),
		WaterLeftBottom(11,1,false),
		WaterLeftUp(11,2,false),
		WaterRightBottom(9,1,false),
		WaterRightUp(9,2,false),
		WaterRight(8,1,false),
		WaterLeft(8,5,false),

		Water(9,0,false),
		WaterOut(9,8,false),
		RiverH(12,3,false)
		;

		private final int x,y;
		public boolean overlappable;

		MapEntityType(int x, int y,boolean b) {
			this.x = x;
			this.y = y;
			this.overlappable = b;
		}
	}

	public MapEntitySprite(Canvas defaultCanvas, int xx, int yy, MapEntityType t) {
		if(image == null)
			image = new DrawableImage(ImageUtility.getResource("map1.png"), defaultCanvas);
		x = xx;
		y = yy;
		this.type = t;
	}

	public MapEntityType getType()
	{
		return this.type;
	}

	public void setFilter(int filter)
	{
		this.filter = filter;
	}

	public int getFilter()
	{
		return filter;
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

		if(filter!=0)
		{
			if(filter==1)
				g.setColor(new Color(1,0,0,0.3f));
			if(filter==2)
				g.setColor(new Color(0,1,0,0.3f));
			g.fillRect(x,y,RENDERING_SIZE,RENDERING_SIZE);
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}

	@Override
	public String toString()
	{
		return "Map "+this.type.name();
	}

}
