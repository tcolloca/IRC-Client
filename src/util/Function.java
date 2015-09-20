package util;

/**
 * Represents a function that can be evaluated over an object of type T,
 * returning another object of type S.
 * 
 * @author Tomas
 *
 * @param <T>
 *            Function's Domain set.
 * @param <S>
 *            Function's Image set.
 */
public interface Function<T, S> {

	/**
	 * Evaluates a function for an object of type T, and returns something of
	 * type S.
	 * 
	 * @param obj
	 *            Object to be evaluated.
	 * @return The corresponding S object for the one received.
	 */
	public S evaluate(T obj);
}
