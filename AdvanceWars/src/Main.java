import game.AdvanceWarsGame;
import gameframework.core.GameLevel;
import levels.TestLevel;

import java.util.ArrayList;

/**
 * Created by alan on 11/01/17.
 */
public class Main {
    public static void main(String[] args) {
		AdvanceWarsGame g = new AdvanceWarsGame(2);

        ArrayList<GameLevel> levels = new ArrayList<>();
        levels.add(new TestLevel(g));

        g.setLevels(levels);
		g.start();
	}
}
