/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.weapon;

import soldier.core.BehaviorExtConst;
import soldier.core.BehaviorSoldier;
import soldier.core.WeaponAttack;

public class WeaponGun extends WeaponAttack {

	@Override
	public WeaponGun clone() {
		return (WeaponGun) super.clone();
	}

	@Override
	public String getName() {
		return "Nerf";
	}

	@Override
	public BehaviorSoldier createExtension(BehaviorSoldier s) {
		return new BehaviorExtConst(this, s, 20, 5);
	}
}
