/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.core;

import java.util.Iterator;

import observer_util.Observable;
 
public interface Unit extends Observable<Unit> {
	/**
	 * Unit methods
	 */
	public String getName();
	public float getHealthPoints();
	public boolean alive();
	public void heal();
	public float parry(float force); 
	public float strike();

	/**
	 * Behavior extensions
	 */
	public void addEquipment(Weapon w);
	public void removeEquipment(Weapon w);
	public Iterator<Weapon> getWeapons();

	/**
	 * Composite methods
	 */
	public Iterator<Unit> subUnits();
	public void addUnit(Unit au);
	public void removeUnit(Unit au);

	/**
	 * Visitor method
	 */
	public void accept(UnitVisitor v);
}
