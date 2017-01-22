/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.units;

import soldier.core.BehaviorSoldierStd;
import soldier.core.BreakingRuleException;
import soldier.core.UnitRider;
import soldier.core.Weapon;

public class UnitBikerMan extends UnitRider {

	private static int MAX_MOVEMENT = 10;

	public UnitBikerMan(String soldierName) {
		super(soldierName, new BehaviorSoldierStd(20, 120));
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
	 * A BikerMan can have at most one equipment
	 */
	@Override
	public void addEquipment(Weapon w) {
		if (nbWeapons() > 0)
			throw new BreakingRuleException();
		super.addEquipment(w);
	}

}
