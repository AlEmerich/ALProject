/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.core;

import soldier.weapon.WeaponVisitor;

public abstract class WeaponAttack implements Weapon {

	@Override
	public WeaponAttack clone() {
		try {
			return (WeaponAttack) super.clone();
		} catch (Exception e) {
		}
		return this;
	}

	@Override
	public void accept(WeaponVisitor v) {
		v.visit(this);
	}

}
