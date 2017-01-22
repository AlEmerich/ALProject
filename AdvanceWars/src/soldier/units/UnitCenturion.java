/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.units;

import soldier.core.BehaviorSoldierStd;
import soldier.core.BreakingRuleException;
import soldier.core.UnitInfantry;
import soldier.core.Weapon;

public class UnitCenturion extends UnitInfantry {

	private static int MAX_MOVEMENT = 1;

	public UnitCenturion(String soldierName) {
		super(soldierName, new BehaviorSoldierStd(15, 100));
		this.currentMovementPoint = MAX_MOVEMENT;
	}

	@Override
	public int getMaxMovementPoint(){ return MAX_MOVEMENT; }

	@Override
	public void resetMovementPoint()
	{
		this.currentMovementPoint = MAX_MOVEMENT;
	}

	/**
	 * A Centurion can have at most two equipments
	 */
	@Override
	public void addEquipment(Weapon w) {
		if (nbWeapons() > 1)
			throw new BreakingRuleException();
		super.addEquipment(w);
	}

}
