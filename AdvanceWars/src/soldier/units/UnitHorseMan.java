/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.units;

import soldier.core.BehaviorSoldierStd;
import soldier.core.BreakingRuleException;
import soldier.core.UnitRider;
import soldier.core.UnitVisitor;
import soldier.core.Weapon;

public class UnitHorseMan extends UnitRider {

	private static int MAX_MOVEMENT = 8;

	public UnitHorseMan(String soldierName) {
		super(soldierName, new BehaviorSoldierStd(120, 20));
		this.currentMovementPoint = MAX_MOVEMENT;
	}

	@Override
	public int getMaxMovementPoint(){ return MAX_MOVEMENT; }

	@Override
	public void resetMovementPoint()
	{
		this.currentMovementPoint = MAX_MOVEMENT;
	}

	@Override
	public void accept(UnitVisitor v) {
		v.visit(this);
	}

	/**
	 * A HorseMan can only have two equipments, and one of each kind
	 */
	@Override
	public void addEquipment(Weapon w) {
		int nbW = nbWeapons();
		if (nbW > 1)
			throw new BreakingRuleException();
		if (nbW == 1 && getWeapons().next().getClass() == w.getClass())
			throw new BreakingRuleException();
		super.addEquipment(w);
	}

}
