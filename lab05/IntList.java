/**
 * A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /**
     * The integer stored by this node.
     */
    public int item;
    /**
     * The next node in this IntList.
     */
    public IntList next;

    /**
     * Constructs an IntList storing ITEM and next node NEXT.
     */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /**
     * Constructs an IntList storing ITEM and no next node.
     */
    public IntList(int item) {
        // this(item, null);
        this.item = item;
        this.next = null;
    }

    /**
     * Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3
     */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]); // create an instance of the class
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        IntList p = L;
        while (p != null) {
            p.item = p.item * p.item;
            p = p.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
        if (A == null) {
            return B;
        }

        if (B == null) {
            return A;
        }

        IntList a = A;
        do {
            a = a.next;
        } while (a.next != null);

        a.next = B;
        return A;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList catenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
        IntList a = A;
        IntList b = B;

        if (A == null) {
            return B;
        }

        if (B == null) {
            return A;
        }

        IntList newInt = new IntList(a.item);
        IntList c = newInt;



        while (a.next != null) {
            a = a.next;
            c.next = new IntList(a.item);
            c = c.next;
        }

        while (b.next != null) {
            c.next = new IntList(b.item);
            b = b.next;
            c = c.next;
        }
        c.next = b;

        return newInt;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        //TODO: YOUR CODE HERE
        /* Recursion Method: a much better way to apply to the intList cals*/
        IntList a = this;
        if (position < 0) {
            throw new IllegalArgumentException("YOUR MESSAGE HERE");
        }
        if (position == 0) {
            return a.item;
        } else {
            if (a.next == null) {
                throw new IllegalArgumentException("YOUR MESSAGE HERE");
            }
            a = a.next;
            return a.get(position - 1);
        }

        /* Iteration Method
        IntList a = this;
        if(position < 0){
            throw new IllegalArgumentException("YOUR MESSAGE HERE");
        }
        while(position>0){
            if (a.next == null){
                throw new IllegalArgumentException("YOUR MESSAGE HERE");
            }
            a = a.next;
            position --;
        }
        return a.item;

         */
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        //TODO: YOUR CODE HERE
        IntList a = this;
        String str = "";
        while (a.next != null) {
            str = str + (a.item) + " ";
            a = a.next;
        }
        return str + (a.item);
    }

    /**
     * Returns whether this and the given list or object are equal.
     * <p>
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. An example of this is on line 84.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntList)) {
            return false;
        }
        IntList otherLst = (IntList) obj;
        IntList a = this;
        IntList b = otherLst;

        while (true) {
            if (a.item != b.item) {
                return false;
            }
            a = a.next;
            b = b.next;

            if ((a.next == null && b.next != null)) {
                return false;
            }

            if ((a.next != null && b.next == null)) {
                return false;
            }

            if ((a.next == null && b.next == null)) {
                return true;
            }
        }

        //TODO: YOUR CODE HERE

    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        //TODO: YOUR CODE HERE
        IntList a = this;
        while (a.next != null) {
            a = a.next;
        }
        IntList b = new IntList(value);
        a.next = b;
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        //TODO: YOUR CODE HERE
        IntList a = this;
        int small = a.item;

        while (a.next != null) {
            a = a.next;
            if (a.item < small) {
                small = a.item;
            }
        }
        return small;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        //TODO: YOUR CODE HERE
//        IntList a = this;
//        int sq = 0;
//
//        while (a.next != null) {
//            sq = sq + (a.item) * (a.item);
//            a = a.next;
//        }
//        return sq + (a.item) * (a.item);
        IntList a = this;
        if(a.next == null){
            return a.item * a.item;
        }
        return a.item * a.item + (a.next).squaredSum();
    }
}