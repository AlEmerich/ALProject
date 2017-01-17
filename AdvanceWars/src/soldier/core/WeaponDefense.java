/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.core;

import soldier.weapon.WeaponVisitor;

public abstract class WeaponDefense implements Weapon {

	@Override
	public WeaponDefense clone() {
		try {
			return (WeaponDefense) super.clone();
		} catch (Exception e) {
		}
		return this;
	}

	@Override
	public void accept(WeaponVisitor v) {
		v.visit(this);
	}
}
