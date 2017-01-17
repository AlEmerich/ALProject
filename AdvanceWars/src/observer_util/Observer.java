/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Université Bordeaux.
 */
package observer_util;

/**
 * Generic version of the Observer design pattern
 * @param <S>
 */
public interface Observer<S> {
	void update(S s);
}
