package deque;

public class LinkedListDeque<T> implements Deque<T> {
    private final Node sentinel;
    private int size;

    /**
     * Creates an empty SLList.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    // @Override
    // private boolean isEmpty() {
    //    return size == 0;
    //}

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (this.size == 0) {
            this.addFirst(item);
        } else {
            sentinel.prev = new Node(item, sentinel, sentinel.prev);
            sentinel.prev.prev.next = sentinel.prev;
            size += 1;
        }
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p.next != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println(p.item);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node p = sentinel.next;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return p.item;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            Node p = sentinel.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return p.item;
        }
    }

    @Override
    public T get(int index) {
        Node p = sentinel.next;
        if (index >= size) {
            return null;
        } else {
            while (index > 0) {
                p = p.next;
                index -= 1;
            }
            return p.item;
        }
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel, index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deque)) return false;
        Deque otherList = (Deque) o;
        if (size != otherList.size()) return false;

        for (int x = 0; x < size; x++) {
            if (!(otherList.get(x).equals(this.get(x)))) return false;
        }
        return true;
    }

    /**
     * Helper function for getRecursive();
     * Recursive method to get the user-defined index value;
     * The base is when index = 0, which should return the item of the first node;
     *
     * @param p: Take a Node as input
     * @param index: input the node index
     * @return Generic data type
     * @author Aldrin Ong
     */
    private T getRecursiveHelper(Node p, int index) {
        if (index == 0) {
            return p.next.item;
        } else {
            return getRecursiveHelper(p.next, index - 1);
        }
    }

    /**
     * The access modifiers inside a private nested class are irrelevant:
     * both the inner class and the outer class can access these instance
     * variables and methods.
     */
    private class Node {
        public T item;
        public Node next;
        public Node prev;

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        // Not changing below
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node otherNode = (Node) o;
            return item == otherNode.item;
        }

        @Override
        public String toString() {
            return item + "";
        }

    }

}
