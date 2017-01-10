package gameframework.core;

import gameframework.moves_rules.SpeedVector;

/**
 * Applies moveBlocker checker and moving strategies
 */
public interface GameMovableDriver {
	public SpeedVector getSpeedVector(Movable m);
}
