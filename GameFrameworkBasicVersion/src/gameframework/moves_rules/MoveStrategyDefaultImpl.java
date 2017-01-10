package gameframework.moves_rules;

public class MoveStrategyDefaultImpl implements MoveStrategy {
	public SpeedVector getSpeedVector() {
		return SpeedVectorDefaultImpl.createNullVector();
	}
}
