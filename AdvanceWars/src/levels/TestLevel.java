package levels;

import entity.Cursor;
import entity.EmptyBlocker;
import entity.MapEntitySprite;
import game.GameLevelTurnImpl;
import game.GameUniverseBoardImpl;
import game.Player;
import gameframework.core.*;
import gameframework.moves_rules.*;
import rules.CursorStrategyKeyboard;
import rules.OverlapSoldierRules;
import soldier.ages.AgeFutureFactory;
import soldier.ages.AgeMiddleFactory;

import java.awt.*;

/**
 * Created by alan on 15/01/17.
 */
public class TestLevel extends GameLevelTurnImpl{

    public static final int SPRITE_SIZE = 16;
    public static final int SIZE_X_WINDOW = 31;
    public static final int SIZE_Y_WINDOW = 28;

    Canvas canvas;

    // Land : 0
    // Tree : 1
    // Mountain : 2
    // Road Vertical : 3
    // Road Horizontal : 4
    // Road Left Bottom : 5
    // Road Left Up : 6
    // Road Right Bottom : 7
    // Road Right Up : 8
    // Bridge : 9

    // Water B: 10
    // Water U: 11
    // Water LB: 12
    // Water LU: 13
    // Water RB: 14
    // Water RU: 15
    // Water Right: 16
    // Water Left : 17
    // Water : 18
    static int[][] tab = {
            { 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 2, 2, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2, 2, 0, 1, 1, 1, 1, 2, 1 },
            { 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 0, 2, 2, 2, 0, 1, 1, 1, 1, 0, 1 },
            { 2, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 0, 2, 2, 2, 2, 0, 1, 1, 1, 1, 0, 1 },
            { 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 3, 1, 1, 1, 0, 2, 2, 2, 2, 2,13,11,11,11,11,11,15, 1 },
            { 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 3, 1, 0, 2, 2, 0, 2, 2, 2, 1,17,18,18,18,18,18,16, 1 },
            { 0, 0, 0, 2, 2, 2, 0, 2, 2, 0, 3, 0, 2, 2, 1, 0, 0, 0, 1, 1,17,18,18,18,18,18,16, 1 },
            { 0, 6, 4, 4, 4, 4, 4, 4, 8, 1, 3, 1, 6, 4, 4, 4, 4, 4, 4, 8,17,18,18,18,18,18,16, 1 },
            { 20,9,20,20,20,20,20,20, 9,20, 9,20, 9,20,20,20,20,20,20, 9,19,10,10,10,10,10,14, 1 },
            { 0, 0, 2, 2, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 2, 2, 0, 1, 0, 0, 2, 2, 2, 1, 1, 0, 2, 2, 2, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 1, 2, 2, 1, 1, 0, 1, 0, 0, 1, 1, 1, 2, 2, 2, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 2, 2, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 1, 0, 0, 2, 2, 1, 1, 1, 6, 4, 4, 4, 4, 4, 4, 4, 8, 0, 1, 6, 4, 4, 4, 4, 4, 4 },
            { 1, 0, 3, 1, 1, 2, 1, 1, 1, 0, 3, 2, 1, 1, 0, 2, 0, 0, 3, 0, 1, 3, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 3, 1, 2, 2, 1, 1, 1, 0, 3, 1, 1, 1, 2, 2, 2, 1, 3, 0, 1, 3, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 3, 0, 0, 2, 2, 0, 2, 0, 3, 0, 0, 0, 0, 2, 2, 2, 3, 0, 0, 3, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 3, 1, 0, 0, 2, 1, 1, 1, 3, 1, 1, 0, 2, 2, 1, 0, 3, 1, 0, 3, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 3, 0, 0, 0, 2, 2, 1, 0, 3, 0, 1, 0, 2, 2, 0, 0, 3, 1, 0, 3, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 3, 0, 0, 0, 0, 2, 1, 1, 3, 1, 1, 2, 2, 1, 0, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 3, 0, 0, 0, 2, 2, 1, 0, 3, 1, 1, 2, 2, 0, 0, 0, 3, 0, 1, 3, 1, 1, 1, 1, 0, 1 },
            { 0, 0, 3, 0, 0, 2, 2, 0, 1, 0, 3, 0, 2, 2, 2, 1, 0, 0, 3, 0, 0, 3, 1, 1, 1, 1, 0, 1 },
            { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 7, 0, 2, 2, 1, 1, 0, 0, 5, 4, 4, 7, 1, 1, 0, 0, 2, 1 },
            { 2, 2, 0, 2, 2, 0, 0, 1, 1, 0, 2, 2, 2, 2, 1, 1, 2, 2, 0, 2, 2, 0, 1, 1, 0, 1, 1, 1 },
            { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

    public TestLevel(Game g) {
        super(g);
        canvas = g.getCanvas();
        playerOne = new Player(new AgeMiddleFactory(), Player.NUMBER.ONE);
        playerTwo = new Player(new AgeFutureFactory(), Player.NUMBER.TWO);
    }


    @Override
    protected void init() {

        MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
        OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

        OverlapRulesApplier overlapRules = new OverlapSoldierRules(universe);
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseBoardImpl(moveBlockerChecker, overlapProcessor);
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
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.Land));
                if (tab[y][x] == 1)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.Tree));
                if (tab[y][x] == 2)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.Mountain));
                if (tab[y][x] == 3)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadVertical));
                if (tab[y][x] == 4)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadHorizontal));
                if (tab[y][x] == 5)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadLeftBottom));
                if (tab[y][x] == 6)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadLeftUp));
                if (tab[y][x] == 7)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadRightBottom));
                if (tab[y][x] == 8)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RoadRightUp));
                if (tab[y][x] == 9)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.BridgeH));
                if (tab[y][x] == 10)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterBottom));
                if (tab[y][x] == 11)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterUp));
                if (tab[y][x] == 12)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterLeftBottom));
                if (tab[y][x] == 13)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterLeftUp));
                if (tab[y][x] == 14)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterRightBottom));
                if (tab[y][x] == 15)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterRightUp));
                if (tab[y][x] == 16)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterRight));
                if (tab[y][x] == 17)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterLeft));
                if (tab[y][x] == 18)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.Water));
                if (tab[y][x] == 19)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.WaterOut));
                if (tab[y][x] == 20)
                    universe.addGameEntity(new MapEntitySprite(canvas,
                            x*SPRITE_SIZE, y*SPRITE_SIZE, MapEntitySprite.MapEntityType.RiverH));
            }

            // Right side
            universe.addGameEntity(new EmptyBlocker(x*SPRITE_SIZE,y*SPRITE_SIZE));
        }

        // Bottom side
        for(int x=0; x<tab[0].length;x++)
            universe.addGameEntity(new EmptyBlocker(x*SPRITE_SIZE,y*SPRITE_SIZE));

        this.cursor = new Cursor(canvas);

        GameMovableDriverDefaultImpl cursorDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new CursorStrategyKeyboard(universe,this.cursor);
        ((OverlapSoldierRules) overlapRules).setStrategyKeyboard( (CursorStrategyKeyboard) keyStr);
		cursorDriver.setStrategy(keyStr);
		cursorDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStr);

		this.cursor.setDriver(cursorDriver);
		this.cursor.setPosition(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(this.cursor);

		playerOne.init(canvas,universe);
		playerTwo.init(canvas,universe);
    }
}
