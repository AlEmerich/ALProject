package rules;

import entity.Cursor;
import entity.CursorMode;
import game.PauseMenu;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.MoveStrategyKeyboard;
import soldier.core.Unit;
import util.ImageUtility;
import util.PathFinding;
import util.PathFindingTree;

import java.awt.*;
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
    private PauseMenu pauseMenu;

    public CursorStrategyKeyboard(GameUniverse universe, Cursor cursor,Canvas canvas)
    {
        this.pathFinder = new PathFindingTree(universe);
        this.cursor = cursor;
        this.destinationKey = "";
        ImageUtility util = new ImageUtility();
        this.pauseMenu = new PauseMenu(util.getResource("menu.png"),canvas);
        universe.addGameEntity(this.pauseMenu);
    }

    @Override
    public void keyPressed(KeyEvent event) {

        int keycode = event.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_RIGHT:
                speedVector.setDirection(new Point(1, 0));
                this.pauseMenu.draw = false;
                cursor.setOverlapUnit(false);
                break;
            case KeyEvent.VK_LEFT:
                speedVector.setDirection(new Point(-1, 0));
                this.pauseMenu.draw = false;
                cursor.setOverlapUnit(false);
                break;
            case KeyEvent.VK_UP:
                speedVector.setDirection(new Point(0, -1));
                this.pauseMenu.draw = false;
                cursor.setOverlapUnit(false);
                break;
            case KeyEvent.VK_DOWN:
                speedVector.setDirection(new Point(0, 1));
                this.pauseMenu.draw = false;
                cursor.setOverlapUnit(false);
                break;
            case KeyEvent.VK_ENTER:
                if(this.pauseMenu.draw)
                {
                    this.pauseMenu.draw = false;
                    Unit army = cursor.getCurrentPlayer().getArmy();
                    army.emptyMovementPoint();
                    return;
                }
                else
                {
                    if(!cursor.isOverlapUnit() && cursor.getMode() == CursorMode.EXPLORE) {
                        this.pauseMenu.draw = true;
                        return;
                    }
                    else
                    {
                        if (cursor.getUnit().getOwner().which() == cursor.getCurrentPlayer().which()) {
                            // FIRST KEY ENTER ON UNIT, TO DISPLAY PATHS
                            if (cursor.getMode() == CursorMode.EXPLORE) {
                                this.pathFinder.getPossibleWays(cursor.getUnit());
                                this.cursor.setMode(CursorMode.MOVE_SOLDIER);
                                this.colorize();
                                return;
                            }

                            // SECOND KEY ENTER ON UNIT POSSIBLE PATHS TO VALID ONE AND UNCOLORIZE ALL
                            if (cursor.getMode() == CursorMode.MOVE_SOLDIER) {
                                if (drawnPath.size() != 1) {
                                    cursor.getUnit().setWay(this.drawnPath);
                                    cursor.getUnit().oneStepMove();
                                }

                                this.uncolorize();
                            }
                        }
                    }
                }
                break;
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
