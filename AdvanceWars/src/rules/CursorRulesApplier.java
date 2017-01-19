package rules;

import entity.Cursor;
import entity.MapEntitySprite;
import entity.SoldierEntity;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.util.Vector;

/**
 * Created by alan on 16/01/17.
 */
public class CursorRulesApplier extends OverlapRulesApplierDefaultImpl {
    protected GameUniverse universe;
    private CursorStrategyKeyboard strategyKeyboard;

    public CursorRulesApplier(GameUniverse u)
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
        if(cursor.isToTestOverlap())
        {
            cursor.showSoldierInformation(soldier);
            cursor.setNotTestOverlap();
        }
    }

    public void overlapRule(Cursor cursor, MapEntitySprite map)
    {
        if(map.getFilter() != 0)
            strategyKeyboard.colorize();
        else
            strategyKeyboard.uncolorize();
    }
}
