package gameframework.core;

import gameframework.moves_rules.ObjectWithBoundedBox;

import java.awt.Point;

public interface Overlappable extends ObjectWithBoundedBox {
	public Point getPosition();
}
