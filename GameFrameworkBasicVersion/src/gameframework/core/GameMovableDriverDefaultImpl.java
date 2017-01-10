package gameframework.core;

import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.MoveStrategy;
import gameframework.moves_rules.MoveStrategyDefaultImpl;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;

public class GameMovableDriverDefaultImpl implements GameMovableDriver {
	protected MoveBlockerChecker moveBlockerChecker;
	protected MoveStrategy moveStrategy;

	public GameMovableDriverDefaultImpl() {
		moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveStrategy = new MoveStrategyDefaultImpl();
	}

	public void setStrategy(MoveStrategy strat) {
		moveStrategy = strat;
	}

	public void setmoveBlockerChecker(MoveBlockerChecker obst) {
		moveBlockerChecker = obst;
	}

	public SpeedVector getSpeedVector(Movable m) {
		SpeedVector possibleSpeedVector;

		possibleSpeedVector = moveStrategy.getSpeedVector();
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			return possibleSpeedVector;
		}

		// If the strategy did not provide a valid vector, try to keep the
		// current vector.
		possibleSpeedVector = m.getSpeedVector();
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			return possibleSpeedVector;
		}

		return SpeedVectorDefaultImpl.createNullVector();
	}
}
