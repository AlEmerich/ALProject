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

        if(cursor.getUnit() == null)
            return;

        if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
        {

        }

        if(event.getKeyCode() == KeyEvent.VK_ENTER && cursor.getUnit().getOwner().which() == cursor.getCurrentPlayer())
        {
            // FIRST KEY ENTER ON UNIT, TO DISPLAY PATHS
            if(cursor.getMode() == CursorMode.EXPLORE)
            {
                this.pathFinder.getPossibleWays(cursor.getUnit());
                this.cursor.setMode(CursorMode.MOVE_SOLDIER);
                this.colorize();
                return;
            }

            // SECOND KEY ENTER ON UNIT POSSIBLE PATHS TO VALID ONE AND UNCOLORIZE ALL
            if(cursor.getMode() == CursorMode.MOVE_SOLDIER) {
                 if (drawnPath.size() != 1) {
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
        if(this.cursor.getMode() == CursorMode.MOVE_SOLDIER) {
            this.pathFinder.removeFastestWay(destinationKey);
            destinationKey = PathFinding.formatKey(cursor);
            // coloriser en vert le plus court chemin du root jusqu'à la clé key
            this.drawnPath = this.pathFinder.setFastestWay(destinationKey);
        }
    }
}
