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
    private boolean loadInfo = true;

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

    public void information(Cursor cursor, SoldierEntity unit)
    {
        if(loadInfo && cursor.getMode() == CursorMode.EXPLORE)
        {
            cursor.showSoldierInformation(unit);
            loadInfo = false;
        }
    }

    public void overlapRule(Cursor cursor, UnitSimpleEntity soldier)
    {
        information(cursor,soldier);
    }

    public void overlapRule(Cursor cursor, UnitGroupEntity group)
    {
        information(cursor,group);
    }

    public void overlapRule(Cursor cursor, MapEntitySprite map)
    {
        if(map.getFilter() != MapFilter.NONE)
            strategyKeyboard.colorize();
        else
            strategyKeyboard.uncolorize();
        loadInfo = true;
    }

    public void overlapRule(UnitGroupEntity group,UnitSimpleEntity soldier)
    {
        // MERGE
        if(group.getOwner() == soldier.getOwner())
        {
            group.merge(soldier.getUnit());
            universe.removeGameEntity(soldier);
            loadInfo = true;
        }
        // FIGHT
        else
        {

        }
    }

    public void overlapRule(UnitSimpleEntity soldier,UnitSimpleEntity soldier2)
    {
        // MERGE
        if(soldier.getOwner() == soldier2.getOwner())
        {
            UnitGroupEntity u = soldier.merge(soldier2.getUnit());
            soldier2.getOwner().removeInArmy(soldier2.getUnit());
            this.universe.removeGameEntity(soldier);
            this.universe.removeGameEntity(soldier2);
            this.universe.addGameEntity(u);

            loadInfo = true;
        }
        // FIGHT
        else
        {

        }
    }
}
