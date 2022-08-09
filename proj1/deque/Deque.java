package deque;

/* The Deque interface defines the expected behavior for any
 * Deque, whether it is an ArrayDeque or LinkedListDeque. A
 * Deque is a doubly-ended queue, that allows you to quickly add
 * and remove items from the front and back. */
public interface Deque<T> {
    /* Adds an item of type T to the front of the deque. You can assume that item is never null. */
    void addFirst(T item);

    /* Adds an item of type T to the back of the deque. You can assume that item is never null. */
    void addLast(T item);

    /* Returns true if deque is empty, false otherwise. */
    // boolean isEmpty();

    /* Returns the number of items in the deque. */
    int size();

    /* Prints the items in the deque from first to last, separated by a space.
    Once all the items have been printed, print out a new line.
     */
    void printDeque();

    /* Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    T removeFirst();

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    T removeLast();

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque!
     */
    T get(int index);

    /* Returns whether the parameter o is equal to the Deque. o is
    considered equal if it is a Deque and if it contains the same contents
    (as governed by the generic T’s equals method) in the same order.
     */
    boolean equals(Object o);

}
