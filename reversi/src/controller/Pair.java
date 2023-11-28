package controller;

/**
 * Generic class for combining two data types into one Pair object.
 * @param <T> the data type of the first object
 * @param <U> the data type of the second object
 */
public class Pair<T, U> {
  private final T first;
  private final U second;

  /**
   * Constructs a Pair object by relating the two objects to each other.
   * @param first the first object
   * @param second the second object
   */
  Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Returns the first object in the pair.
   * @return the first object
   */
  public T getFirst() {
    return this.first;
  }

  /**
   * Returns the second object in the pair.
   * @return the second object
   */
  public U getSecond() {
    return this.second;
  }
}