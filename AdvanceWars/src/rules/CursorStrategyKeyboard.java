package rules;

import entity.Cursor;
import entity.CursorMode;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.MoveStrategyKeyboard;
import util.PathFinding;
import util.PathFindingTree;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by alaguitard on 18/01/17.
 */
public class CursorStrategyKeyboard extends MoveStrategyKeyboard {

    private PathFinding pathFinder;
    private Cursor cursor;
    private List<String> drawnPath = null;
    private String destinationKey;

    private boolean cursorMoved =false;

    public CursorStrategyKeyboard(GameUniverse universe, Cursor cursor)
    {
        this.pathFinder = new PathFindingTree(universe);
        this.cursor = cursor;
        this.destinationKey = "";
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        this.cursorMoved = false;
        super.keyPressed(event);

        if(event.getKeyCode() == KeyEvent.VK_ENTER)
        {
            // FIRST KEY ENTER ON UNIT, TO DISPLAY PATHS
            if(!cursor.isToTestOverlap() && cursor.getMode() == CursorMode.EXPLORE)
            {
                this.pathFinder.getPossibleWays(cursor.getUnit());
                this.cursor.setMode(CursorMode.MOVE_SOLDIER);
            }

            // SECOND KEY ENTER ON UNIT POSSIBLE PATHS TO VALID ONE AND UNCOLORIZE ALL
            if(drawnPath != null) {
                if(drawnPath.size() != 1)
                {
                    cursor.getUnit().setWay(this.drawnPath);
                    cursor.getUnit().oneStepMove();
                }

                this.uncolorize();
            }
        }
    }

    public void uncolorize()
    {
        if(drawnPath != null)
        {
            this.pathFinder.reset();
            this.cursor.setMode(CursorMode.EXPLORE);
            this.drawnPath = null;
        }
    }

    public void colorize()
    {
        if(!cursorMoved && this.cursor.getMode() == CursorMode.MOVE_SOLDIER) {
            this.pathFinder.removeFastestWay(destinationKey);
            destinationKey = PathFinding.formatKey(cursor);
            // coloriser en vert le plus court chemin du root jusqu'à la clé key
            this.drawnPath = this.pathFinder.setFastestWay(destinationKey);
            cursorMoved = true;
        }
    }
}
