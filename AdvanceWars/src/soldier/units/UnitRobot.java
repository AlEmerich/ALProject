/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.units;

import soldier.core.BreakingRuleException;
import soldier.core.UnitInfantry;
import soldier.core.Weapon;

public class UnitRobot extends UnitInfantry {

	private static int MAX_MOVEMENT = 6;

	public UnitRobot(String soldierName) {
		super(soldierName, new BehaviorSoldierHealthBased( 100, 50));
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
	 * A Robot can have at most four equipments
	 */
	@Override
	public void addEquipment(Weapon w) {
		if (nbWeapons()>3) throw new BreakingRuleException();
		super.addEquipment(w);
	}

	
}
