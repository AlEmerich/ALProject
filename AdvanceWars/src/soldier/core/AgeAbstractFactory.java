/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.core;

public interface AgeAbstractFactory {
	Unit infantryUnit(String name);

	Unit riderUnit(String name);

	Weapon attackWeapon();

	Weapon defenseWeapon();
}
