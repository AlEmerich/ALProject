package entity;

import game.Player;
import gameframework.core.*;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;
import soldier.core.Unit;
import util.PathFinding;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaguitard on 17/01/17.
 */
public abstract class SoldierEntity extends GameMovable implements Drawable, GameEntity,
        Overlappable {

    private Unit unit;
    private List<String> toGoThrough = new ArrayList<>();
    protected Player owner;
    private String lastMoveKey;
    protected boolean movable = true;

    public SoldierEntity(Player which,Unit unit)
    {
        this.unit = unit;
        this.owner = which;
    }

    public void setWay(List<String> list)
    {
        this.toGoThrough = list;
    }

    public Player getOwner()
    {
        return this.owner;
    }

    public void stepBackward()
    {
        this.toGoThrough.add(this.lastMoveKey);
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
                    this.getUnit().oneStep();
                    this.lastMoveKey = from;
                }
            }
            this.toGoThrough.remove(this.toGoThrough.size()-1);
        }
        else
            this.setSpeedVector(SpeedVectorDefaultImpl.createNullVector());
    }

    public abstract void draw(Graphics g);

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

    public Rectangle getBoundingBox() {
        return (new Rectangle(0, 0, MapEntitySprite.RENDERING_SIZE, MapEntitySprite.RENDERING_SIZE));
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setUnit(Unit u)
    {
        this.unit = u;
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
