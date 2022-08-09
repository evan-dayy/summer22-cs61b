import jh61b.junit.In;

/**
 * An SLList is a list of integers, which encapsulates the
 * naked linked list structure.
 */
public class SLList {
    /* The first item (if it exists) is at sentinel.next. */
    private final IntListNode sentinel;
    private int size;
    /**
     * Creates an empty SLList.
     */
    public SLList() {
        sentinel = new IntListNode(42, null);
        sentinel.next = sentinel;
        size = 0;
    } // Constructor 1 (with empty SLList)

    public SLList(int x) {
        sentinel = new IntListNode(42, null);
        sentinel.next = new IntListNode(x, null);
        sentinel.next.next = sentinel;
        size = 1;
    } // Constructor 2 (with 1 IntListNode)

    /**
     * Returns an SLList consisting of the given values.
     */
    public static SLList of(int... values) {
        SLList list = new SLList();
        for (int i = values.length - 1; i >= 0; i -= 1) {
            list.addFirst(values[i]);
        }
        return list;
    } // is int an array?

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass() /*!(o instance of SLList)*/) return false;
        SLList slList = (SLList) o;
        if (size != slList.size) return false;

        IntListNode l1 = this.sentinel.next;
        IntListNode l2 = slList.sentinel.next;

        while (l1 != sentinel && l2 != slList.sentinel) {
            if (!l1.equals(l2)) return false;
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == sentinel && l2 == slList.sentinel;
    }

    @Override
    public String toString() {
        IntListNode l = sentinel.next;
        String result = "";

        while (l != sentinel) {
            result += l.item + " ";
            l = l.next;
        }
        return result.trim();
    }

    /**
     * Returns the size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Adds x to the front of the list.
     */
    public void addFirst(int x) {
        sentinel.next = new IntListNode(x, sentinel.next);
        size += 1;
    }

    /**
     * Return the value at the given index.
     */
    public int get(int index) {
        IntListNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /**
     * Adds x to the list at the specified index.
     */
    public void add(int index, int x) {
        // TODO
        IntListNode p = this.sentinel;
         if (this.size == 0 || index > this.size) {
            while (p.next != sentinel) {
                p = p.next;
            }
            p.next = new IntListNode(x, sentinel);

        } else {
            while (index > 0) {
                index--;
                p = p.next;
            }
            p.next = new IntListNode(x, p.next);
        }
        size++;
    }

    /**
     * Destructively reverses this list.
     */
    public void reverse() {
        sentinel.next = reverseHelper(sentinel.next);
    }

    public IntListNode reverseHelper(IntListNode L){
        if (L.next == sentinel){
            return L;
        }
        IntListNode temp = reverseHelper(L.next);
        L.next.next = L;
        L.next = sentinel;
        return temp;
    }



    /** public void reverse() {
        // TODO
        IntListNode Header = sentinel;
        while (Header.next != sentinel) {
            Header = Header.next;
        }
        IntListNode L = sentinel.next;
        reverseHelper(L);
        L.next.next = Header;

    }

    private void reverseHelper(IntListNode L) {
        if (L == sentinel) {
            return;
        }
        if (L.next == sentinel) {
            return;
        }
        IntListNode temp = L;
        while (temp.next.next != this.sentinel) {
            temp = temp.next;
        }
        IntListNode secLast = temp;
        IntListNode last = temp.next;
        last.next = secLast;
        secLast.next = sentinel;
        reverseHelper(L);
    } */

    /**
     * IntListNode is a nested class that represents a single node in the
     * SLList, storing an item and a reference to the next IntListNode.
     */
    private static class IntListNode {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        public int item;
        public IntListNode next;

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass() /*!(o instance of IntListNode)*/) return false;
            IntListNode that = (IntListNode) o;
            return item == that.item;
        }

        @Override
        public String toString() {
            return item + "";
        }

    }

}



