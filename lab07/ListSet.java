import java.util.ArrayList;
import java.util.List;

/**
 * Represent a set of ints.
 */
public class ListSet implements SimpleSet {

    List<Integer> elems;

    public ListSet() {
        elems = new ArrayList<Integer>();
    }

    /** Adds k to the set. */
    @Override
    public void add(int k) {
        // TODO
        if(!elems.contains(k)){
            elems.add(k);
        }
    }

    /** Removes k from the set. */
    public void remove(int k) {
        Integer toRemove = k;
        // TODO - use the above variable with an appropriate List method.
        // The reason is beyond the scope of this lab, but involves
        // method resolution.
        if(elems.contains(k)){
            elems.remove(toRemove);
        }
    }

    /** Return true if k is in this set, false otherwise. */
    public boolean contains(int k) {
        // TODO
        return elems.contains(k);
    }

    /** Return true if this set is empty, false otherwise. */
    public boolean isEmpty() {
      return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    public int size() {
        // TODO
        return elems.size();
    }

    /** Returns an array containing all of the elements in this collection. */
    public int[] toIntArray() {
        // TODO - use a for loop!
        int[] a = new int[elems.size()];
        for(int i = 0; i < elems.size(); i++){
            a[i] = elems.get(i);
        }
        return a;

    }
}
