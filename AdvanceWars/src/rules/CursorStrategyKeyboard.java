package rules;

import entity.Cursor;
import entity.SoldierEntity;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.MoveStrategyKeyboard;
import util.PathFinding;

import java.awt.event.KeyEvent;

/**
 * Created by alaguitard on 18/01/17.
 */
public class CursorStrategyKeyboard extends MoveStrategyKeyboard {

    private PathFinding pathFinder;
    private Cursor cursor;

    public CursorStrategyKeyboard(GameUniverse universe, Cursor cursor)
    {
        this.pathFinder = new PathFinding(universe);
        this.cursor = cursor;
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        super.keyPressed(event);
        if(event.getKeyCode() == KeyEvent.VK_ENTER && !cursor.isToTestOverlap())
        {
            this.pathFinder.getPossibleWays(cursor.getUnit());
            //calcule des chemins possibles
            //on affiche

        }
    }
}
