package game;

import entity.Cursor;
import gameframework.core.*;
import soldier.util.UnitCounterVisitor;

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
    protected ObservableValue<Integer>[] life;
    boolean stopGameLoop;
    volatile boolean gameInPause;

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
        gameInPause = false;
        // main game loop
        long start;
        while (!stopGameLoop && !this.isInterrupted()) {
            synchronized (this){
                while(gameInPause)
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

            if(changePlayer())
            {
                cursor.changeCurrentPlayer();
                player.setValue(cursor.getCurrentPlayer().name());
            }

            UnitCounterVisitor visitor = new UnitCounterVisitor();
            getCurrentPlayer().getArmy().accept(visitor);
            life[0].setValue(visitor.aliveUnit);
            visitor.reset();

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

    public void pause()
    {
        gameInPause = true;
    }

    public synchronized void unpause()
    {
        gameInPause = false;
        //For an unknown reason, two notify will cause the unpause action, one don't...
        notify();
        notify();
    }

    public void end() {
        stopGameLoop = true;
    }

    protected void overlap_handler() {
    }

    public Player getCurrentPlayer()
    {
        return (cursor.getCurrentPlayer().name() == Player.NUMBER.ONE.name() ? playerOne : playerTwo);
    }

    public boolean changePlayer()
    {
        Player current = getCurrentPlayer();

        if(current.getArmy().getMovementPoint() > 0)
            return false;

        current.beginTurn();
        return true;
    }
}
