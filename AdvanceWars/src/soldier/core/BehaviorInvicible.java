/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.core;


public class BehaviorInvicible extends BehaviorExtension {
	public BehaviorInvicible(Weapon owner, BehaviorSoldier s) {
		super(owner, s);
	}

	@Override
	public float parry(float force) {
		return super.parry(0);
	}
}
