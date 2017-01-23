package soldier.util;

import soldier.core.*;

import java.util.Iterator;

/**
 * Created by alan on 22/01/17.
 */
public class UnitCounterVisitor implements UnitVisitor {

    public int aliveUnit=0;

    public void reset(){ aliveUnit = 0;}
    @Override
    public void visit(UnitGroup g) {
        for(Iterator<Unit> it = g.subUnits(); it.hasNext() ; it.next().accept(this))
            ;
    }

    @Override
    public void visit(UnitRider ur) {
        if(ur.alive())
            aliveUnit++;

    }

    @Override
    public void visit(UnitInfantry ui) {
        if(ui.alive())
            aliveUnit++;
    }
}
