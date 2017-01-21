package soldier.util;

import soldier.core.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alan on 21/01/17.
 */
public class AttributUnitVisitor implements UnitVisitor {
    public float attack;
    public float health;
    public int movementPoint;
    public int maxMovement;

    public List<String> soldiers;

    public AttributUnitVisitor()
    {
        soldiers = new ArrayList<>();
        clear();
    }

    public void clear()
    {
        attack = health = movementPoint = maxMovement = 0;
    }

    @Override
    public void visit(UnitGroup g) {
        for (Iterator<Unit> it = g.subUnits(); it.hasNext(); it.next().accept(
                this))
            ;
        movementPoint = g.getMovementPoint();
        maxMovement = g.getMaxMovementPoint();
    }

    @Override
    public void visit(UnitRider ur) {
        attack += ur.strike();
        health += ur.getHealthPoints();
        movementPoint += ur.getMovementPoint();
        maxMovement += ur.getMaxMovementPoint();

        soldiers.add(" ---->"+ ur.getName()+" A "+ ur.strike() + " HP " + ur.getHealthPoints()
                + " MP "+ ur.getMovementPoint()+"/"+ur.getMaxMovementPoint()+"\n");
    }

    @Override
    public void visit(UnitInfantry ui) {
        attack += ui.strike();
        health += ui.getHealthPoints();
        movementPoint += ui.getMovementPoint();
        maxMovement += ui.getMaxMovementPoint();

        soldiers.add(" ---->"+ ui.getName()+" A "+ ui.strike() + " HP " + ui.getHealthPoints()
                + " MP "+ui.getMovementPoint()+"/"+ui.getMaxMovementPoint()+"\n");
    }
}
