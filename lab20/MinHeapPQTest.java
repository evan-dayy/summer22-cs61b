import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ<Character> heap = new MinHeapPQ();
        heap.insert('a', 6);

        heap.insert('b', 5);

        heap.insert('c', 4);

        heap.insert('d', 3);

        heap.insert('e', 2);

        heap.insert('f', 1);


        assertTrue(heap.contains('a'));
        assertTrue(heap.contains('b'));
        assertTrue(heap.contains('c'));
        assertTrue(heap.contains('d'));
        assertTrue(heap.contains('e'));
        assertTrue(heap.contains('f'));


        heap.changePriority('f',7);



        heap.poll();
        assertTrue(heap.contains('f'));
        assertFalse(heap.contains('e'));
        heap.poll();
        assertFalse(heap.contains('d'));
        heap.poll();
        assertFalse(heap.contains('c'));
        heap.poll();
        assertFalse(heap.contains('b'));
        heap.poll();
        assertFalse(heap.contains('a'));
        heap.poll();
        assertFalse(heap.contains('f'));


    }
}
