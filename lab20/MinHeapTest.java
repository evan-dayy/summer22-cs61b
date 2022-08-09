import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void test1() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(6);
        assertTrue(heap.contains(6));
        heap.insert(5);
        assertTrue(heap.contains(5));
        heap.insert(4);
        assertTrue(heap.contains(4));
        heap.insert(3);
        assertTrue(heap.contains(3));
        heap.insert(2);
        assertTrue(heap.contains(2));
        heap.insert(1);
        assertTrue(heap.contains(1));

        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(null);
        arr.add(1);
        arr.add(3);
        arr.add(2);
        arr.add(6);
        arr.add(4);
        arr.add(5);
        MinHeap<Integer> heap1 = new MinHeap<>(arr);
        assertTrue(heap1.equals(heap));

        heap.removeMin();
        assertFalse(heap.contains(1));
        heap.removeMin();
        assertFalse(heap.contains(2));
        heap.removeMin();
        assertFalse(heap.contains(3));
        heap.removeMin();
        assertFalse(heap.contains(4));
        heap.removeMin();
        assertFalse(heap.contains(5));
        heap.removeMin();
        assertFalse(heap.contains(6));

    }
}
