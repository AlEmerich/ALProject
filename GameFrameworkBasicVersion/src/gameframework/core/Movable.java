package gameframework.core;

import gameframework.moves_rules.ObjectWithBoundedBox;
import gameframework.moves_rules.SpeedVector;

import java.awt.Point;

/**
 * Has a current position, a {@link SpeedVector} and a bounding box.
 */
public interface Movable extends ObjectWithBoundedBox {
	public Point getPosition();

	public SpeedVector getSpeedVector();

	public void setSpeedVector(SpeedVector m);

	public void oneStepMove();
}
