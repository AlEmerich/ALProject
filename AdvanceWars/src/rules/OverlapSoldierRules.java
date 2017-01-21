package rules;

import entity.*;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

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
        this.strategyKeyboard = strategyKeyboard;
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
            if(cursor.getMode() == CursorMode.MOVE_SOLDIER)
                strategyKeyboard.uncolorize();
    }
}
