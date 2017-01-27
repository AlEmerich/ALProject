/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.core;

import observer_util.ObservableAbstract;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class UnitGroup extends ObservableAbstract<Unit> 
                       implements Unit {

	private Set<Unit> units;
	private String name;

	public UnitGroup(String name) {
		this.name = name;
		units = new TreeSet<>((o1, o2) -> {
			if (o1.getName().compareTo(o2.getName()) == 0)
				return o1.hashCode() - o2.hashCode();
			else
				return o1.getName().compareTo(o2.getName());
		});
	}

	@Override
	public void addUnit(Unit au) {
		units.add(au);
	}

	@Override
	public void removeUnit(Unit au) {
		units.remove(au);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public float getHealthPoints() {
		float sum = 0.f;
		for (Unit u : units)
			sum += u.getHealthPoints();
		return sum;
	}

	@Override
	public boolean alive() {
		return getHealthPoints() > 0.f;
	}

	@Override
	public void heal() {
		for (Unit u : units)
			u.heal();
	}

	@Override
	public float parry(float force) {
		float f = 0.f;
		Iterator<Unit> it = subUnits();
		while (force > 0.f && it.hasNext()) {
			Unit u = it.next();
			if (!u.alive())
				continue;
			force = u.parry(force);
		}
		notifyObservers(this);
		return f;
	}

	@Override
	public float strike() {
		float sum = 0;
		for (Unit u : units) {
			if (u.alive())
				sum += u.strike();
		}
		return sum;
	}

	@Override
	public int getMaxMovementPoint()
	{
        int max=0;
        int tmp = 0;
        for(Unit u : units)
        {
            tmp = u.getMaxMovementPoint();
            if(tmp > max) {
                max = tmp;
            }
        }
        return max;
	}

	/**
	 *
	 * @return the highest movement point on the group.
	 */
	@Override
	public int getMovementPoint() {
		int max=0;
		int tmp = 0;
		for(Unit u : units)
        {
            tmp = u.getMovementPoint();
            if(tmp > max) {
                max = tmp;

            }
        }
		return max;
	}

	@Override
	public void emptyMovementPoint()
	{
		for(Unit u : units)
			u.emptyMovementPoint();
	}

	@Override
	public void oneStep() {
		for (Iterator<Unit> it = subUnits(); it.hasNext(); it.next().oneStep());
	}

	@Override
	public void resetMovementPoint()
	{
		for (Iterator<Unit> it = subUnits(); it.hasNext(); it.next().resetMovementPoint());
	}

	@Override
	public Iterator<Unit> subUnits() {
		return units.iterator();
	}

	@Override
	public void accept(UnitVisitor v) {
		v.visit(this);
	}

	@Override
	public Iterator<Weapon> getWeapons() {
		if (units.isEmpty())
			return Collections.emptyIterator();
		return new Iterator<Weapon>() {
			Iterator<Unit> itUnit = subUnits();
			Iterator<Weapon> curIt = itUnit.next().getWeapons();

			@Override
			public boolean hasNext() {
				while (!curIt.hasNext() && itUnit.hasNext())
					curIt = itUnit.next().getWeapons();
				return curIt.hasNext();
			}

			@Override
			public Weapon next() {
				return curIt.next();
			}
		};
	}

	@Override
	public void addEquipment(Weapon w) {
		Iterator<Unit> it = subUnits();
		while (it.hasNext()) {
			Unit u = it.next();
			try {
				u.addEquipment(w);
				w = w.clone();
			} catch (BreakingRuleException b) {
				System.out.println("Impossible to add " + w.getName() + " to " + u.getName());
			}
		}
	}

	@Override
	public void removeEquipment(Weapon w) {
		for (Iterator<Unit> it = subUnits(); it.hasNext(); it.next()
				.removeEquipment(w)) {
		}
	}

	@Override
    public String toString()
    {
        return this.units.size()+"";
    }
}
