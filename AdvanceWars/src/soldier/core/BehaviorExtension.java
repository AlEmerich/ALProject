/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package soldier.core;

public abstract class BehaviorExtension implements BehaviorSoldier {
	private BehaviorSoldier soldier;
	private Weapon owner;

	public Weapon getOwner() {
		return owner;
	}

	void reparent(BehaviorSoldier newParent) {
		soldier = newParent;
	}

	public BehaviorExtension(Weapon owner, BehaviorSoldier s) {
		this.soldier = s;
		this.owner = owner;
	}

	@Override
	public float getHealthPoints() {
		return soldier.getHealthPoints();
	}

	@Override
	public boolean alive() {
		return soldier.alive();
	}

	@Override
	public void heal() {
		soldier.heal();
	}

	@Override
	public float parry(float force) {
		return soldier.parry(force);
	}

	@Override
	public float strike() {
		return soldier.strike();
	}

	/**
	 * Function to manage the decoration chain
	 * 
	 * @visibility package
	 */
	BehaviorSoldier parent() {
		return soldier;
	}
}
