/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.util;

import java.util.Iterator;

import observer_util.Observer;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.core.UnitInfantry;
import soldier.core.UnitRider;
import soldier.core.UnitVisitor;

public class AddSimpleUnitObserver implements UnitVisitor {
	Observer<Unit> obs;

	public AddSimpleUnitObserver(Observer<Unit> obs) {
		this.obs = obs;
	}

	@Override
	public void visit(UnitGroup g) {
		for (Iterator<Unit> it = g.subUnits(); it.hasNext(); it.next().accept(
				this))
			;
	}

	@Override
	public void visit(UnitRider ur) {
		if (ur.alive())
			ur.addObserver(obs);
	}

	@Override
	public void visit(UnitInfantry ui) {
		if (ui.alive())
			ui.addObserver(obs);
	}
}