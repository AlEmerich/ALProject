package soldier.util;

import soldier.core.*;

import java.util.Iterator;
import java.util.Locale;

/**
 * Created by alan on 21/01/17.
 */
public class AttributUnitVisitor implements UnitVisitor {
    public float attack;
    public float health;
    public int movementPoint;
    public int maxMovement;
    public String weapons;

    public String soldiers;

    public AttributUnitVisitor()
    {
        soldiers = "<html><ul>";

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
        this.weapons = " Weapon ";
        for(Iterator<Weapon> it = ur.getWeapons(); it.hasNext(); )
        {
            weapons += it.next().getName()+" ";

        }
        soldiers += "<li> "+ ur.getName()+" ATK "+ ur.strike()
                + weapons
                + " <br> HP " + String.format(Locale.US,"%.2f",ur.getHealthPoints())
                + " MP "+ ur.getMovementPoint()+"/"+ur.getMaxMovementPoint()+"</li>";
    }

    @Override
    public void visit(UnitInfantry ui) {
        attack += ui.strike();
        health += ui.getHealthPoints();
        movementPoint += ui.getMovementPoint();
        maxMovement += ui.getMaxMovementPoint();
        this.weapons = " Weapon ";
        for(Iterator<Weapon> it = ui.getWeapons(); it.hasNext(); )
        {
            weapons += it.next().getName()+" ";

        }
        soldiers += "<li>"+ ui.getName()+" ATK "
                + weapons
                + ui.strike() + " <br> HP " + ui.getHealthPoints()
                + " MP "+ui.getMovementPoint()+"/"+ui.getMaxMovementPoint()+"</li>";
    }
}
