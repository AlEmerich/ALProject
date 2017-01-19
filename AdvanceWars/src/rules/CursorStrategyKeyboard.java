package rules;

import entity.Cursor;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.MoveStrategyKeyboard;
import util.PathFinding;
import util.PathFindingTree;

import java.awt.event.KeyEvent;

/**
 * Created by alaguitard on 18/01/17.
 */
public class CursorStrategyKeyboard extends MoveStrategyKeyboard {

    private PathFinding pathFinder;
    private Cursor cursor;
    private boolean toDrawWay = false;

    private String destinationKey;

    public CursorStrategyKeyboard(GameUniverse universe, Cursor cursor)
    {
        this.pathFinder = new PathFindingTree(universe);
        this.cursor = cursor;
        this.destinationKey = "";
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        super.keyPressed(event);

        if(event.getKeyCode() == KeyEvent.VK_ENTER && !cursor.isToTestOverlap())
        {
            toDrawWay = true;
            System.err.println(this.pathFinder.getPossibleWays(cursor.getUnit()));

        }

        if(toDrawWay) {



        }
    }

    public void uncolorize()
    {
        this.pathFinder.reset();
        toDrawWay = false;
    }

    public void colorize()
    {
        if(toDrawWay) {
            this.pathFinder.removeFastestWay(destinationKey);
            destinationKey = PathFinding.formatKey(cursor);
            // coloriser en vert le plus court chemin du root jusqu'à la clé key
            this.pathFinder.setFastestWay(PathFinding.formatKey(cursor.getUnit()),destinationKey);
        }
    }
}
