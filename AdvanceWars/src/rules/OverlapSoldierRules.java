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

    private Cursor cursor;

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
        this.cursor = cursor;
        if(loadInfo && cursor.getMode() == CursorMode.EXPLORE)
        {
            cursor.showSoldierInformation(unit);
            loadInfo = false;
        }
    }

    public void fight(SoldierEntity attacker,SoldierEntity defender)
    {
        if(!attacker.getUnit().alive())
            attacker.getOwner().removeInArmy(attacker.getUnit());
        else
        if(defender.getUnit().alive())
            attacker.stepBackward();

        if(!defender.getUnit().alive())
            defender.getOwner().removeInArmy(defender.getUnit());
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
            group.getUnit().parry(soldier.getUnit().strike());
            soldier.getUnit().parry(group.getUnit().strike());

            boolean firstParamIsAttack = (group.getOwner().which() == cursor.getCurrentPlayer() ?
                    true : false);
            UnitSimpleEntity attacker = (firstParamIsAttack ? group : soldier);
            UnitSimpleEntity defender = (firstParamIsAttack ? soldier : group);

            fight(attacker,defender);
        }
    }

    public void overlapRule(UnitGroupEntity group1, UnitGroupEntity group2)
    {
        // MERGE
        if(group1.getOwner() == group2.getOwner())
        {
            group1.merge(group2.getUnit());
            universe.removeGameEntity(group2);
            loadInfo = true;
        }
        // FIGHT
        else
        {
            group1.getUnit().parry(group2.getUnit().strike());
            group2.getUnit().parry(group1.getUnit().strike());

            boolean firstParamIsAttack = (group1.getOwner().which() == cursor.getCurrentPlayer() ?
                    true : false);
            UnitSimpleEntity attacker = (firstParamIsAttack ? group1 : group2);
            UnitSimpleEntity defender = (firstParamIsAttack ? group2 : group1);

            fight(attacker,defender);
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
            soldier2.getUnit().parry(soldier.getUnit().strike());
            soldier.getUnit().parry(soldier2.getUnit().strike());

            boolean firstParamIsAttack = (soldier.getOwner().which() == cursor.getCurrentPlayer() ?
                    true : false);
            UnitSimpleEntity attacker = (firstParamIsAttack ? soldier : soldier2);
            UnitSimpleEntity defender = (firstParamIsAttack ? soldier2 : soldier);

            fight(attacker,defender);
        }
    }
}
