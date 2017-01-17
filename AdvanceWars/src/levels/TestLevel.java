package levels;

import entity.*;
import entity.Cursor;
import gameframework.core.*;
import gameframework.moves_rules.*;
import util.ImageUtility;

import java.awt.*;
import java.io.File;

/**
 * Created by alan on 15/01/17.
 */
public class TestLevel extends GameLevelDefaultImpl{

    public static final int SPRITE_SIZE = 16;
    public static final int SIZE_X_WINDOW = 31;
    public static final int SIZE_Y_WINDOW = 28;
    Canvas canvas;

    // 0 : Pacgums; 1 : Walls; 2 : SuperPacgums; 3 : Doors; 4 : Jail; 5 : empty
    // Note: teleportation points are not indicated since they are defined by
    // directed pairs of positions.
    static int[][] tab = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 2, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 5, 1, 1, 5, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 5, 1, 1, 5, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 1, 1, 3, 3, 1, 1, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 4, 4, 4, 4, 4, 4, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 5, 5, 5, 5, 5, 5, 0, 5, 5, 5, 1, 4, 4, 4, 4, 4, 4, 1, 5, 5, 5, 0, 5, 5, 5, 5, 5, 5 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 4, 4, 4, 4, 4, 4, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 1 },
            { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
            { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

    public TestLevel(Game g) {
        super(g);
        canvas = g.getCanvas();
    }


    @Override
    protected void init() {

        MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
        OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

        OverlapRulesApplier overlapRules = new CursorRulesApplier(universe);
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
        overlapRules.setUniverse(universe);

        gameBoard = new GameUniverseViewPortDefaultImpl(g.getCanvas(),universe);

        ((CanvasDefaultImpl) g.getCanvas()).setDrawingGameBoard(gameBoard);

        // Up side
        for(int x=0; x<tab[0].length;x++)
            universe.addGameEntity(new EmptyBlocker(x*SPRITE_SIZE,-SPRITE_SIZE));

        int y;
        for(y=0; y<tab.length;y++)
        {
            // Left side
            universe.addGameEntity(new EmptyBlocker(-SPRITE_SIZE,y*SPRITE_SIZE));

            int x;
            for(x=0; x<tab[y].length;x++)
            {

                if (tab[y][x] == 0)
                    universe.addGameEntity(new MapEntitySprite(canvas, x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.Land));
                if (tab[y][x] == 1)
                    universe.addGameEntity(new MapEntitySprite(canvas, x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.Tree));
                if (tab[y][x] == 2)
                    universe.addGameEntity(new MapEntitySprite(canvas, x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadHorizontal));
                if (tab[y][x] == 3)
                    universe.addGameEntity(new MapEntitySprite(canvas, x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadVertical));
                if (tab[y][x] == 4)
                    universe.addGameEntity(new MapEntitySprite(canvas, x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadLeftBottom));
                if (tab[y][x] == 5)
                    universe.addGameEntity(new MapEntitySprite(canvas, x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadLeftUp));
            }

            // Right side
            universe.addGameEntity(new EmptyBlocker(x*SPRITE_SIZE,y*SPRITE_SIZE));
        }


        // Bottom side
        for(int x=0; x<tab[0].length;x++)
            universe.addGameEntity(new EmptyBlocker(x*SPRITE_SIZE,y*SPRITE_SIZE));

        Cursor c = new Cursor(canvas);
        GameMovableDriverDefaultImpl cursorDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard();
		cursorDriver.setStrategy(keyStr);
		cursorDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStr);
		c.setDriver(cursorDriver);
		c.setPosition(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(c);

        universe.addGameEntity(new SoldierEntity(canvas));
    }
}
