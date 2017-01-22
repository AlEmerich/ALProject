package game;

import entity.Cursor;
import gameframework.core.*;

import java.util.Date;

/**
 * Created by alaguitard on 17/01/17.
 */
public abstract class GameLevelTurnImpl extends GameLevelDefaultImpl {
    private static final int MINIMUM_DELAY_BETWEEN_GAME_CYCLES = 100;

    protected final Game g;
    protected Cursor cursor;
    protected GameUniverse universe;
    protected GameUniverseViewPort gameBoard;

    protected ObservableValue<String> player;
    protected ObservableValue<Integer> life[];
    protected ObservableValue<Boolean> endOfGame;

    boolean stopGameLoop;

    protected Player playerOne;
    protected Player playerTwo;

    protected abstract void init();

    public GameLevelTurnImpl(Game g) {
        super(g);
        this.g = g;
        this.player = ((AdvanceWarsGame) g).player();
        this.life = g.life();
    }

    // start of class Thread which calls the run method (see below)
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void run() {
        stopGameLoop = false;
        // main game loop
        long start;
        while (!stopGameLoop && !this.isInterrupted()) {
            if(changePlayer())
            {
                cursor.changeCurrentPlayer();
                player.setValue(cursor.getCurrentPlayer().name());
            }

            start = new Date().getTime();
            gameBoard.paint();

            universe.allOneStepMoves();
            universe.processAllOverlaps();
            try {
                long sleepTime = MINIMUM_DELAY_BETWEEN_GAME_CYCLES
                        - (new Date().getTime() - start);
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (Exception e) {
            }
        }
    }

    public void end() {
        stopGameLoop = true;
    }

    protected void overlap_handler() {
    }

    private boolean changePlayer()
    {
        Player current = (cursor.getCurrentPlayer().name() == Player.NUMBER.ONE.name() ? playerOne : playerTwo);

        if(current.getArmy().getMovementPoint() > 0)
            return false;

        current.beginTurn();
        return true;
    }
}
