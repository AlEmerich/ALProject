package entity;

import gameframework.core.GameUniverse;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplier;

import java.util.Vector;

/**
 * Created by alan on 16/01/17.
 */
public class SoldierRulesApplier implements OverlapRulesApplier {
    protected GameUniverse universe;

    public SoldierRulesApplier(GameUniverse u)
    {
        this.universe = u;
    }

    @Override
    public void setUniverse(GameUniverse universe) {
        this.universe = universe;
    }

    @Override
    public void applyOverlapRules(Vector<Overlap> overlaps) {

    }
}
