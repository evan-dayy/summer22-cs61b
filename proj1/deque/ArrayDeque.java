package deque;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    /**
     * Constructor
     * It generates a new ArrayDeque, with size = 0;
     * In this Project, we set nextFirst and nextLast as two different continuous number;
     * The advantage of continuous number is that we can make sure it is continuous between these two;
     *
     * @author Evan Day, Aldrin Ong
     */
    public ArrayDeque() {
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        items = (T[]) new Object[8];
    }

    @Override
    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            resizeBigger();
        }
        items[nextFirst] = item;
        size++;
        if (nextFirst == 0) {
            nextFirst = items.length;
        }
        nextFirst = (nextFirst - 1);
    }

    @Override
    public void addLast(T item) {
        if (nextFirst == nextLast) {
            resizeBigger();
        }
        items[nextLast] = item;
        size++;
        nextLast = (nextLast + 1) % items.length;
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
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(nextFirst + 1 + i) % items.length] + " ");
        }
        System.out.println(" ");
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T val = items[(nextFirst + 1) % items.length];
        items[(nextFirst + 1) % items.length] = null;
        nextFirst = (nextFirst + 1) % items.length;
        size--;
        double threshold = (double) size / items.length;
        if (items.length > 16 && threshold < 0.25) {
            resizeSmaller();
        }
        return val;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T val = items[(nextLast + items.length - 1) % items.length];
        items[(nextLast + items.length - 1) % items.length] = null;
        nextLast = (nextLast + items.length - 1) % items.length;
        size--;
        double threshold = (double) size / items.length;
        if (items.length > 16 && threshold < 0.25) {
            resizeSmaller();
        }
        return val;
    }

    @Override
    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        return items[(nextFirst + index + 1) % items.length];
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
     * Try to resize the array upward when the current array is almost filled (take action when size = length - 1);
     * The resize factor is equal to 2, suggesting that items point to an array with 2 * current array size;
     * The basic logic is try to move every element after NextFirst to the end of new array;
     * Then move the NextFast to the relative position in new array;
     *
     * @author Evan Day
     */
    private void resizeBigger() {
        T[] nb = (T[]) new Object[items.length * 2];
        int boxAdded = nb.length - items.length;
        for (int i = nextFirst + 1; i < items.length; i++) {
            nb[i + boxAdded] = items[i];
        }
        for (int i = 0, j = 0; i < nextLast; i++, j++) {
            nb[j] = items[i];
        }

        this.nextFirst = this.nextFirst + boxAdded;
        items = nb;
    }

    /**
     * Try to resize the array downward when the current array's usage below 25%;
     * The resize factor is equal to 2, suggesting that items point to an array with half of current array size;
     * The basic logic is that it is true that it is continuous between NextFirst and NextLast (consider it as circle);
     * Then move the NextFast to the 0 position, and put others in relative position in new array;
     *
     * @author Evan Day
     */
    private void resizeSmaller() {
        T[] sb = (T[]) new Object[items.length / 2];
        for (int i = nextFirst + 1, j = 0; j < size; i++, j++) {
            sb[j + 1] = items[i % items.length];
        }
        this.nextFirst = 0;
        this.nextLast = size + 1;
        items = sb;
    }
}
