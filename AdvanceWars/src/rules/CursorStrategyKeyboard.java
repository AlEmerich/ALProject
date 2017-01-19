package rules;

import entity.Cursor;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.MoveStrategyKeyboard;
import util.PathFinding;
import util.PathFindingTree;

import java.awt.event.KeyEvent;
import java.util.Map;

/**
 * Created by alaguitard on 18/01/17.
 */
public class CursorStrategyKeyboard extends MoveStrategyKeyboard {

    private PathFinding pathFinder;
    private Cursor cursor;
    private boolean toDrawWay = false;
    private GameUniverse universe;

    private Map destinationNode;

    public CursorStrategyKeyboard(GameUniverse universe, Cursor cursor)
    {
        this.pathFinder = new PathFindingTree(universe);
        this.cursor = cursor;
        this.universe = universe;
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        super.keyPressed(event);
        colorize();

        if(event.getKeyCode() == KeyEvent.VK_ENTER && !cursor.isToTestOverlap())
        {
            toDrawWay = true;
            Map paths = this.pathFinder.getPossibleWays(cursor.getUnit());

        }
    }

    private void colorize()
    {
        if(toDrawWay) {
            //this.pathFinder.removeFastestWay(destinationNode);

            // coloriser en vert le plus court chemin du root jusqu'à la clé key
            String destKey = PathFinding.formatKey(cursor);

            if((destinationNode = this.pathFinder.setFastestWay(destKey)) == null) {
                toDrawWay = false;
                this.pathFinder.reset();
            }
        }
        else
            this.pathFinder.reset();
    }
}
