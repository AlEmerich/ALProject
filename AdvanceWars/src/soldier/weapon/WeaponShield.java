/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.weapon;

import soldier.core.BehaviorSoldier;
import soldier.core.WeaponDefense;

public class WeaponShield extends WeaponDefense {

	@Override
	public WeaponShield clone() {
		return (WeaponShield) super.clone();
	}

	@Override
	public String getName() {
		return "Shield";
	}

	@Override
	public BehaviorSoldier createExtension(BehaviorSoldier s) {
		return new BehaviorExtLogLin(this, s);
	}

}
