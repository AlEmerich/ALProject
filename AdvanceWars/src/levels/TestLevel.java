package levels;

import entity.SoldierRulesApplier;
import gameframework.core.*;
import gameframework.moves_rules.*;

/**
 * Created by alan on 15/01/17.
 */
public class TestLevel extends GameLevelDefaultImpl{
    public TestLevel(Game g) {
        super(g);
    }

    @Override
    protected void init() {

        MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
        OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

        OverlapRulesApplier overlapRules = new SoldierRulesApplier(universe);
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
        overlapRules.setUniverse(universe);

        gameBoard = new GameUniverseViewPortDefaultImpl(g.getCanvas(),universe);

        ((GameUniverseViewPortDefaultImpl) gameBoard).setBackground("images/grid.gif");
        ((CanvasDefaultImpl) g.getCanvas()).setDrawingGameBoard(gameBoard);

    }
}
