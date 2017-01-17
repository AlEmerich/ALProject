/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.core;

public interface UnitVisitor {
	void visit(UnitGroup g);

	void visit(UnitRider ur);

	void visit(UnitInfantry ui);
}
