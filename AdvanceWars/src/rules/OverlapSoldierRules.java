package rules;

import entity.*;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;
import soldier.core.Unit;
import soldier.core.UnitGroup;

import java.util.Vector;

/**
 * Created by alan on 16/01/17.
 */
public class OverlapSoldierRules extends OverlapRulesApplierDefaultImpl {
    protected GameUniverse universe;
    private CursorStrategyKeyboard strategyKeyboard;

    public OverlapSoldierRules(GameUniverse u)
    {
        this.universe = u;
    }

    public void setStrategyKeyboard(CursorStrategyKeyboard strategyKeyboard)
    {
        this.strategyKeyboard = strategyKeyboard;
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

    public void overlapRule(Cursor cursor, SoldierEntity soldier)
    {
        if(cursor.isToTestOverlap() && cursor.getMode() == CursorMode.EXPLORE)
        {
            cursor.showSoldierInformation(soldier);
            cursor.setNotTestOverlap();
        }
    }

    public void overlapRule(Cursor cursor, MapEntitySprite map)
    {
        if(map.getFilter() != MapFilter.NONE)
            strategyKeyboard.colorize();
        else
            strategyKeyboard.uncolorize();
    }

    public void overlapRule(SoldierEntity soldier,SoldierEntity soldier2)
    {
        if(soldier2.getUnit() instanceof UnitGroup)
        {
            soldier2.getUnit().addUnit(soldier.getUnit());
            this.universe.removeGameEntity(soldier);
        }
        else if(soldier.getUnit() instanceof UnitGroup)
        {
            soldier.getUnit().addUnit(soldier2.getUnit());
            this.universe.removeGameEntity(soldier2);
        }
        else
        {
            Unit groupUnit = new UnitGroup(soldier.getUnit().getName());
            groupUnit.addUnit(soldier.getUnit());
            groupUnit.addUnit(soldier2.getUnit());
            soldier.setUnit(groupUnit);
            this.universe.removeGameEntity(soldier2);
        }

    }
}
