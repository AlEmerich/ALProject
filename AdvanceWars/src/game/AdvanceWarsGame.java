package game;

import gameframework.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by alan on 21/01/17.
 */
public class AdvanceWarsGame implements Game, Observer {
    protected static final int NB_ROWS = 31;
    protected static final int NB_COLUMNS = 28;
    protected static final int SPRITE_SIZE = 16;
    public static final int MAX_NUMBER_OF_PLAYER = 4;
    public static final int NUMBER_OF_LIVES = 1;

    protected CanvasDefaultImpl defaultCanvas = null;
    protected ObservableValue<String> player = null;
    protected ObservableValue<Integer> life[] = null;

    // initialized before each level
    protected ObservableValue<Boolean> endOfGame = null;

    private Frame f;

    private GameLevelDefaultImpl currentPlayedLevel = null;
    protected int levelNumber;
    protected ArrayList<GameLevel> gameLevels;

    protected Label lifeText, playerText;
    protected Label information;
    protected Label informationValue;
    protected Label lifeValue, playerValue;
    protected Label currentLevel;
    protected Label currentLevelValue;

    public AdvanceWarsGame(int nb_player) {
        if(MAX_NUMBER_OF_PLAYER < nb_player)
            return;
        life = new ObservableValue[nb_player];
        for (int i = 0; i < nb_player; ++i) {
            life[i] = new ObservableValue<>(0);
        }
        player = new ObservableValue("ONE");
        lifeText = new Label("Units Left:");
        playerText = new Label("Player:");
        information = new Label("State:");
        informationValue = new Label("Playing");
        currentLevel = new Label("Level:");
        createGUI();
    }

    public void createGUI() {
        f = new Frame("Advance Wars");
        f.dispose();

        createMenuBar();
        Container c = createStatusBar();

        defaultCanvas = new CanvasDefaultImpl();
        defaultCanvas.setSize(SPRITE_SIZE * NB_COLUMNS, SPRITE_SIZE * NB_ROWS);
        f.add(defaultCanvas);
        f.add(c, BorderLayout.NORTH);
        f.pack();
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("file");
        MenuItem start = new MenuItem("new game");
        MenuItem save = new MenuItem("save");
        MenuItem restore = new MenuItem("load");
        MenuItem quit = new MenuItem("quit");
        Menu game = new Menu("game");
        MenuItem pause = new MenuItem("pause");
        MenuItem resume = new MenuItem("resume");
        menuBar.add(file);
        menuBar.add(game);
        f.setMenuBar(menuBar);

        start.addActionListener(e -> start());
        save.addActionListener(e -> save());
        restore.addActionListener(e -> restore());
        quit.addActionListener(e -> System.exit(0));
        pause.addActionListener(e -> pause());
        resume.addActionListener(e -> resume());

        file.add(start);
        file.add(save);
        file.add(restore);
        file.add(quit);
        game.add(pause);
        game.add(resume);
    }

    private Container createStatusBar() {
        JPanel c = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        c.setLayout(layout);
        playerValue = new Label(player.getValue());
        lifeValue = new Label(Integer.toString(life[0].getValue()));
        currentLevelValue = new Label(Integer.toString(levelNumber));
        c.add(playerText);
        c.add(playerValue);
        c.add(lifeText);
        c.add(lifeValue);
        c.add(currentLevel);
        c.add(currentLevelValue);
        c.add(information);
        c.add(informationValue);
        return c;
    }

    public Canvas getCanvas() {
        return defaultCanvas;
    }

    public void start() {
        for (int i = 0; i < life.length; ++i) {
            life[i].addObserver(this);
            life[i].setValue(NUMBER_OF_LIVES);
        }
        player.addObserver(this);
        levelNumber = 0;
        for (GameLevel level : gameLevels) {
            endOfGame = new ObservableValue<Boolean>(false);
            endOfGame.addObserver(this);
            try {
                if (currentPlayedLevel != null && currentPlayedLevel.isAlive()) {
                    currentPlayedLevel.interrupt();
                    currentPlayedLevel = null;
                }
                currentPlayedLevel = (GameLevelDefaultImpl) level;
                levelNumber++;
                currentLevelValue.setText(Integer.toString(levelNumber));
                currentPlayedLevel.start();
                currentPlayedLevel.join();
            } catch (Exception e) {
            }
        }

    }

    public void restore() {
        System.out.println("restore(): Unimplemented operation");
    }

    public void save() {
        System.out.println("save(): Unimplemented operation");
    }

    public void pause() {
        System.out.println("pause(): Unimplemented operation");
        // currentPlayedLevel.suspend();
    }

    public void resume() {
        System.out.println("resume(): Unimplemented operation");
        // currentPlayedLevel.resume();
    }

    @Override
    public ObservableValue<Integer>[] score() {
        return null;
    }

    public ObservableValue<String> player(){ return player; }

    public ObservableValue<Integer>[] life() {
        return life;
    }

    public ObservableValue<Boolean> endOfGame() {
        return endOfGame;
    }

    public void setLevels(ArrayList<GameLevel> levels) {
        gameLevels = levels;
    }

    public void update(Observable o, Object arg) {
        if (o == endOfGame) {
            if (endOfGame.getValue()) {
                informationValue.setText("You win");
                currentPlayedLevel.interrupt();
                currentPlayedLevel.end();
            }
        } else {
            for (ObservableValue<Integer> lifeObservable : life) {
                if (o == lifeObservable) {
                    int lives = ((ObservableValue<Integer>) o).getValue();
                    lifeValue.setText(Integer.toString(lives));
                    if (lives == 0) {
                        informationValue.setText("Defeat");
                        currentPlayedLevel.interrupt();
                        currentPlayedLevel.end();
                    }
                }
            }
            if(o == player)
                playerValue.setText(player.getValue());
        }
    }
}
