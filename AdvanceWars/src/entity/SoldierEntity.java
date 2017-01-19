package entity;

import game.SpriteManagerSoldierImpl;
import gameframework.core.*;
import soldier.core.Unit;
import soldier.units.UnitCenturion;
import util.ImageUtility;

import java.awt.*;

/**
 * Created by alaguitard on 17/01/17.
 */
public class SoldierEntity extends GameMovable implements Drawable, GameEntity,
        Overlappable {

    private SpriteManager spriteManager;
    private Unit unit;
    private String filenameImage = "soldier2.png";

    protected boolean movable = true;

    public SoldierEntity(Canvas canvas, String name)
    {
        spriteManager = new SpriteManagerSoldierImpl(ImageUtility.getResource(filenameImage), canvas);
        unit = new UnitCenturion(name);
        spriteManager.setTypes(
                //
                "Idle",//
                "Right",
                "Left",
                "Down",
                "Up",
                "Wait"
        );
    }

    public void draw(Graphics g) {
        String spriteType = "";
        Point tmp = getSpeedVector().getDirection();
        movable = true;

        if (tmp.getX() == 1) {
            spriteType += "right";
        } else if (tmp.getX() == -1) {
            spriteType += "left";
        } else if (tmp.getY() == 1) {
            spriteType += "down";
        } else if (tmp.getY() == -1) {
            spriteType += "up";
        } else {
            spriteType = "Idle";
            //spriteManager.reset();
            movable = true;
        }
        spriteManager.setType(spriteType);
        spriteManager.draw(g, getPosition());

    }

    @Override
    public void oneStepMoveAddedBehavior() {
        if (movable) {
            spriteManager.increment();
        }
    }

    public Rectangle getBoundingBox() {
        return (new Rectangle(0, 0, MapEntitySprite.RENDERING_SIZE, MapEntitySprite.RENDERING_SIZE));
    }

    public Unit getUnit()
    {
        return unit;
    }

    public String getFilenameImage()
    {
        return "bigSoldier.png";
    }
    public String toString()
    {
        return "Soldier "+this.unit.getName();
    }
}
