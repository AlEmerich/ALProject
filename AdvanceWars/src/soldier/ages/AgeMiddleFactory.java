/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.ages;

import soldier.core.AgeAbstractFactory;
import soldier.core.Unit;
import soldier.core.Weapon;
import soldier.units.UnitCenturion;
import soldier.units.UnitHorseMan;
import soldier.weapon.WeaponShield;
import soldier.weapon.WeaponSword;

public class AgeMiddleFactory implements AgeAbstractFactory {

	@Override
	public Unit infantryUnit(String name) {
		return new UnitCenturion(name);
	}

	@Override
	public Unit riderUnit(String name) {
		return new UnitHorseMan(name);
	}

	@Override
	public Weapon attackWeapon() {
		return new WeaponSword();
	}

	@Override
	public Weapon defenseWeapon() {
		return new WeaponShield();
	}
}
