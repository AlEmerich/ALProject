package entity;

import game.SpriteManagerSoldierImpl;
import gameframework.core.*;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;
import soldier.core.Unit;
import soldier.units.UnitCenturion;
import util.ImageUtility;
import util.PathFinding;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaguitard on 17/01/17.
 */
public class SoldierEntity extends GameMovable implements Drawable, GameEntity,
        Overlappable {

    private SpriteManager spriteManager;
    private Unit unit;
    private String filenameImage = "soldier2.png";
    private List<String> toGoThrough = new ArrayList<>();

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

    public void setWay(List<String> list)
    {
        this.toGoThrough = list;
    }

    public void move()
    {
        if(!this.toGoThrough.isEmpty())
        {
            String to = this.toGoThrough.get(this.toGoThrough.size()-1);
            String from = PathFinding.formatKey(this);
            if(!to.equals(from))
            {
                PathFinding.Direction d = PathFinding.getDirection(to,from);

                if(d != null) {

                    this.setSpeedVector(new SpeedVectorDefaultImpl(new Point(d.x,d.y)));
                }
            }
            this.toGoThrough.remove(this.toGoThrough.size()-1);
        }
        else
            this.setSpeedVector(SpeedVectorDefaultImpl.createNullVector());
    }

    public void draw(Graphics g) {
        String spriteType = "";
        Point tmp = getSpeedVector().getDirection();
        movable = true;

        if (tmp.getX() == 1) {
            spriteType += "Right";
        } else if (tmp.getX() == -1) {
            spriteType += "Left";
        } else if (tmp.getY() == 1) {
            spriteType += "Down";
        } else if (tmp.getY() == -1) {
            spriteType += "Up";
        } else {
            spriteType = "Idle";
            //spriteManager.reset();
            movable = true;
        }
        spriteManager.setType(spriteType);
        spriteManager.draw(g, getPosition());

    }

    @Override
    public void oneStepMove()
    {
        this.move();
        SpeedVector v = this.getSpeedVector();
        this.getPosition().translate((int) v.getDirection().getX()
                * v.getSpeed(), (int) v.getDirection()
                .getY() * v.getSpeed());
        this.oneStepMoveAddedBehavior();
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
