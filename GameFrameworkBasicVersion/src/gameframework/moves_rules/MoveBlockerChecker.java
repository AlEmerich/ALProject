package gameframework.moves_rules;

import gameframework.core.Movable;


public interface MoveBlockerChecker {
	public void addMoveBlocker(MoveBlocker p);

	public void removeMoveBlocker(MoveBlocker p);

	public void setMoveBlockerRules(MoveBlockerRulesApplier moveBlockerRules);

	public boolean moveValidation(Movable m, SpeedVector mov);
}
