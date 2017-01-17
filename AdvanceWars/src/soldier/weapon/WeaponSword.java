/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.weapon;

import soldier.core.BehaviorExtConst;
import soldier.core.BehaviorSoldier;
import soldier.core.WeaponAttack;

public class WeaponSword extends WeaponAttack {

	@Override
	public WeaponSword clone() {
		return (WeaponSword) super.clone();
	}

	@Override
	public String getName() {
		return "Sword";
	}

	@Override
	public BehaviorSoldier createExtension(BehaviorSoldier s) {
		return new BehaviorExtConst(this, s, 10, 10);
	}
}
