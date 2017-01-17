/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package observer_util;

/**
 * Generic version of the Observer design pattern
 * @param <S>
 */
public interface Observable<S> {
	void addObserver(Observer<S> ob);

	void removeObserver(Observer<S> ob);

	void notifyObservers(S s);
}