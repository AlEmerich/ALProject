/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.util;

import java.util.Iterator;

import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.core.UnitInfantry;
import soldier.core.UnitRider;
import soldier.core.UnitVisitor;
import soldier.core.Weapon;
import soldier.core.WeaponAttack;
import soldier.core.WeaponDefense;
import soldier.weapon.WeaponVisitor;

public class WeaponCounterVisitor implements UnitVisitor {
	public int attWeapon = 0;
	public int defWeapon = 0;

	WeaponVisitor weaponVisitor = new WeaponVisitor() {
		@Override
		public void visit(WeaponAttack s) {
			++attWeapon;
		}

		@Override
		public void visit(WeaponDefense s) {
			++defWeapon;
		}
	};

	public void clear() {
		attWeapon = 0;
		defWeapon = 0;
	}

	@Override
	public void visit(UnitGroup g) {
		for (Iterator<Unit> it = g.subUnits(); it.hasNext(); it.next().accept(
				this))
			;
	}

	@Override
	public void visit(UnitRider ur) {
		for (Iterator<Weapon> it = ur.getWeapons(); it.hasNext(); it.next()
				.accept(weaponVisitor))
			;
	}

	@Override
	public void visit(UnitInfantry ui) {
		for (Iterator<Weapon> it = ui.getWeapons(); it.hasNext(); it.next()
				.accept(weaponVisitor))
			;
	}

}