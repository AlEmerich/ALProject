package entity;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.Overlappable;
import soldier.core.Weapon;
import util.ImageUtility;

import java.awt.*;

/**
 * Created by alaguitard on 27/01/17.
 */
public class WeaponEntity implements GameEntity,Drawable,Overlappable{

    protected DrawableImage image = null;
    int x,y;
    public static final int RENDERING_SIZE = 16;
    private WeaponType type;

    public WeaponEntity(Canvas canvas,int xx, int yy,WeaponType type)
    {
        ImageUtility util = new ImageUtility();
        this.type = type;
        this.image = new DrawableImage(util.getResource(this.type.filename),canvas);
        x = xx;
        y = yy;
    }

    public boolean isShield()
    {
        return type == WeaponType.SHIELD;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(),x,y,x+RENDERING_SIZE,y+RENDERING_SIZE,
                0,0,RENDERING_SIZE,RENDERING_SIZE,null);
    }

    @Override
    public Point getPosition() {
        return new Point(x,y);
    }

    @Override
    public Rectangle getBoundingBox() {
        return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
    }
}
