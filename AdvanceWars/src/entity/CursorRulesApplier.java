package entity;

import gameframework.core.GameUniverse;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplier;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.util.Vector;

/**
 * Created by alan on 16/01/17.
 */
public class CursorRulesApplier extends OverlapRulesApplierDefaultImpl {
    protected GameUniverse universe;

    public CursorRulesApplier(GameUniverse u)
    {
        this.universe = u;
    }

    @Override
    public void setUniverse(GameUniverse universe) {
        this.universe = universe;
    }

    @Override
    public void applyOverlapRules(Vector<Overlap> overlaps)
    {
        super.applyOverlapRules(overlaps);
    }

    public void overlapRule(Cursor cursor,SoldierEntity soldier)
    {
        if(cursor.isToTestOverlap())
        {
            System.err.println("Afficher menu état du soldat "+soldier.getUnit().getName());
            cursor.setNotTestOverlap();
        }
    }
}
