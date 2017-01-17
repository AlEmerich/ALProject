/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package observer_util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ObservableAbstract<S> implements Observable<S> {
	private List<Observer<S>> observersOrdered = new LinkedList<Observer<S>>();
	private Set<Observer<S>> observersSet = new HashSet<Observer<S>>();

	@Override
	public void addObserver(Observer<S> obs) {
		if (!observersSet.contains(obs)) {
			observersOrdered.add(obs);
			observersSet.add(obs);
		}
	}

	@Override
	public void removeObserver(Observer<S> obs) {
		observersOrdered.remove(obs);
		observersSet.remove(obs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void notifyObservers(S s) {
		Object[] copy = observersOrdered.toArray();
		for (Object u : copy)
			((Observer<S>) u).update(s);
	}
}
