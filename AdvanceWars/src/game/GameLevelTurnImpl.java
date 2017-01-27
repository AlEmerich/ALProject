package game;

import entity.Cursor;
import gameframework.core.*;
import soldier.util.UnitCounterVisitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    protected List<Player> players;
    protected abstract void init();



    public GameLevelTurnImpl(Game g) {
        super(g);
        this.players = new ArrayList<>();
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
                int indexNext = (players.indexOf(cursor.getCurrentPlayer()) + 1) % players.size();
                cursor.setCurrentPlayer(players.get(indexNext));
                player.setValue(cursor.getCurrentPlayer().which().name());
            }

            UnitCounterVisitor visitor = new UnitCounterVisitor();
            cursor.getCurrentPlayer().getArmy().accept(visitor);
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

    public boolean changePlayer()
    {
        if(cursor.getCurrentPlayer().getArmy().getMovementPoint() > 0)
            return false;

        cursor.getCurrentPlayer().beginTurn();
        return true;
    }
}
