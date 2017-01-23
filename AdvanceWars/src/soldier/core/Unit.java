/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.core;

import observer_util.Observable;

import java.util.Iterator;


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

	default int getMaxMovementPoint(){return 5;}
	int getMovementPoint();
	void oneStep();
	void resetMovementPoint();

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
