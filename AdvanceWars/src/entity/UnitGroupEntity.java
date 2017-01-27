package entity;

import game.Player;
import soldier.core.Unit;

import java.awt.*;

/**
 * Created by alan on 21/01/17.
 * TODO: Make extends SoldierEntity
 */
public class UnitGroupEntity extends UnitSimpleEntity {
    public UnitGroupEntity(Player which,Canvas canvas, Unit unit) {
        super(which, canvas,unit);
    }

    @Override
    public void oneStepMoveAddedBehavior() {
        super.oneStepMoveAddedBehavior();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    public UnitGroupEntity merge(Unit u)
    {
        this.getUnit().addUnit(u);
        this.getOwner().removeInArmy(u);
        this.getUnit().emptyMovementPoint();
        return this;
    }
}
