/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.units;

import soldier.core.BehaviorSoldierStd;

public class BehaviorSoldierHealthBased extends BehaviorSoldierStd {
	public BehaviorSoldierHealthBased(float health, float force) {
		super(health, force);
	}

	@Override
	public float strike() {
		return super.strike() * getHealthPoints() / initialHealth();
	}
}
