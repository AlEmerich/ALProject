package gameframework.moves_rules;

import gameframework.core.Movable;

import java.util.Vector;

public interface MoveBlockerRulesApplier {
	public boolean moveValidationProcessing(Vector<MoveBlocker> obs, Movable m);
}
