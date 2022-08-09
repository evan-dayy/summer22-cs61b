package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list deque tests.
 */
public class LinkedListDequeTest {

    /**
     * You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograd will not grade that test. If you would like to test
     * LinkedListDeque with types other than Integer (and you should),
     * you can define a new local variable. However, the autograd will
     * not grade that test. Please do not import java.util.Deque here!
     */

    public static Deque<Integer> lld = new LinkedListDeque<Integer>(); // Autograd Test
    public static Deque<String> lldString = new LinkedListDeque<String>(); // Self different Type Test

    /**
     * Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     * <p>
     * && is the "and" operation.
     */
//    @Test
//    public void addIsEmptySizeTest() {
//
//        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
//
//        lld.addFirst(0);
//        assertFalse("lld1 should now contain 1 item", lld.isEmpty());
//        lld = new LinkedListDeque<Integer>(); //Assigns lld equal to a new, clean LinkedListDeque!
//
//        assertTrue("A newly initialized LLDeque should be empty", lldString.isEmpty());
//        lldString.addFirst("Not Empty NOW");
//        assertFalse("selfTest should now contain 1 item", lldString.isEmpty());
//        lldString = new LinkedListDeque<String>();
//
//    }

    /**
     * Adds an item, removes an item, and ensures that dll is empty afterwards.
     */
    @Test
    public void addRemoveTest() {
        lld.addFirst(0);
        lld.addLast(1);
        lld.removeFirst();
        lld.removeLast();
//        assertTrue(lld.isEmpty());
        lld = new LinkedListDeque<Integer>();

        lldString.addFirst("LIST");
        lldString.addLast("LISTLast");
        lldString.removeFirst();
        lldString.removeLast();
//        assertTrue(lldString.isEmpty());
        lldString = new LinkedListDeque<String>();
    }

    /**
     * Make sure that removing from an empty LinkedListDeque does nothing
     */
    @Test
    public void removeEmptyTest() {
        lld.removeLast();
        assertEquals(0, lld.size());
        lld.removeFirst();
        assertEquals(0, lld.size());
//        assertTrue(lld.isEmpty());
    }

    /**
     * Make sure your LinkedListDeque also works on non-Integer types
     */
    @Test
    public void multipleParamsTest() {
        // String test
        Deque<String> lldString = new LinkedListDeque<String>();
        lldString.addLast("1");
        lldString.addLast("2");
        lldString.addLast("3");
        lldString.addLast("4");
        assertEquals(4, lldString.size());
        assertSame("1", lldString.removeFirst());
        assertEquals(3, lldString.size());
        assertSame("2", lldString.get(0));
        assertSame("3", lldString.get(1));
        assertSame("4", lldString.get(2));

        // Remove items until lld is empty
        assertSame("2", lldString.removeFirst());
        assertSame("3", lldString.removeFirst());
        assertSame("4", lldString.removeFirst());
        lldString = new LinkedListDeque<String>();

        // Int test
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld.addLast(4);
        assertEquals(4, lld.size());
        assertSame(1, lld.removeFirst());
        assertEquals(3, lld.size());
        assertSame(2, lld.get(0));
        assertSame(3, lld.get(1));
        assertSame(4, lld.get(2));

        // Remove items until lld is empty
        assertSame(2, lld.removeFirst());
        assertSame(3, lld.removeFirst());
        assertSame(4, lld.removeFirst());
        lld = new LinkedListDeque<Integer>();

    }

    /**
     * Make sure that removing from an empty LinkedListDeque returns null
     */
    @Test
    public void emptyNullReturn() {
        assertNull(lld.removeLast());
    }
    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */

    /**
     * Test for addFirst
     **/
    @Test
    public void addFirstTest() {
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        lld.addFirst(4);
        assertEquals(4, lld.size());
        assertEquals(4, (int) lld.get(0));
        assertEquals(3, (int) lld.get(1));
        assertEquals(2, (int) lld.get(2));
        assertEquals(1, (int) lld.get(3));
        lld = new LinkedListDeque<Integer>();

        lldString.addFirst("1");
        lldString.addFirst("2");
        lldString.addFirst("3");
        lldString.addFirst("4");
        assertEquals(4, lldString.size());
        assertEquals("4", lldString.get(0));
        assertEquals("3", lldString.get(1));
        assertEquals("2", lldString.get(2));
        assertEquals("1", lldString.get(3));
        lld = new LinkedListDeque<Integer>();
        lldString = new LinkedListDeque<String>();
    }

    /**
     * Test for addLast
     **/
    @Test
    public void addLastTest() {
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld.addLast(4);
        assertEquals(4, lld.size());
        assertEquals(1, (int) lld.get(0));
        assertEquals(2, (int) lld.get(1));
        assertEquals(3, (int) lld.get(2));
        assertEquals(4, (int) lld.get(3));
        lld = new LinkedListDeque<Integer>();

        lldString.addLast("1");
        lldString.addLast("2");
        lldString.addLast("3");
        lldString.addLast("4");
        assertEquals(4, lldString.size());
        assertEquals("1", lldString.get(0));
        assertEquals("2", lldString.get(1));
        assertEquals("3", lldString.get(2));
        assertEquals("4", lldString.get(3));
        lldString = new LinkedListDeque<String>();
    }

    /**
     * Test for removeFirst
     **/
    @Test
    public void removeFirstTest() {
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld.addLast(4);
        assertEquals(4, lld.size());
        assertEquals(1, (int) lld.removeFirst());
        assertEquals(3, lld.size());
        assertEquals(2, (int) lld.get(0));
        assertEquals(3, (int) lld.get(1));
        assertEquals(4, (int) lld.get(2));

        // Remove items until lld is empty
        assertEquals(2, (int) lld.removeFirst());
        assertEquals(3, (int) lld.removeFirst());
        assertEquals(4, (int) lld.removeFirst());
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * Test for removeLast
     **/
    @Test
    public void removeLastTest() {
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld.addLast(4);
        assertEquals(4, lld.size());
        assertEquals(4, (int) lld.removeLast());
        assertEquals(3, lld.size());
        assertEquals(1, (int) lld.get(0));
        assertEquals(2, (int) lld.get(1));
        assertEquals(3, (int) lld.get(2));

        // Remove items until lld is empty
        assertEquals(3, (int) lld.removeLast());
        assertEquals(2, (int) lld.removeLast());
        assertEquals(1, (int) lld.removeLast());
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * Test for getRecursive
     **/
    @Test
    public void getRecursiveTest() {
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld.addLast(4);
        assertEquals(1, (int) ((LinkedListDeque<Integer>) lld).getRecursive(0));
        assertEquals(2, (int) ((LinkedListDeque<Integer>) lld).getRecursive(1));
        assertEquals(3, (int) ((LinkedListDeque<Integer>) lld).getRecursive(2));
        assertEquals(4, (int) ((LinkedListDeque<Integer>) lld).getRecursive(3));
        lld = new LinkedListDeque<Integer>();
    }


    /**
     * General Test for large input number
     **/
    @Test
    public void resizeBiggerFirstTest() {
        lld = new LinkedListDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            lld.addFirst(i);
        }
        for (int i = 0; i < lld.size(); i++) {
            assertEquals(65 - i, (int) lld.get(i));
        }
        lld = new LinkedListDeque<Integer>();

    }

    /**
     * General Test for large input number
     **/
    @Test
    public void resizeSmallerFirstTest() {
        lld = new LinkedListDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            assertEquals(i, lld.size());
            lld.addFirst(i);
        }
        for (int i = 0; i < lld.size(); i++) {
            assertEquals(65 - i, (int) lld.get(i));
        }
        for (int i = 0; i < 55; i++) {
            assertEquals(66 - i, lld.size());
            lld.removeFirst();
        }
        for (int i = 0; i < lld.size(); i++) {
            assertEquals(lld.size() - 1 - i, (int) lld.get(i));
        }
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * Test for equality
     **/
    @Test
    public void equalsTest() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        lld = new LinkedListDeque<Integer>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld.addLast(4);
        assertTrue(lld.equals(ad));

        lld.removeLast();
        assertTrue(!lld.equals(ad));

        lld = new LinkedListDeque<Integer>();
        ad = new ArrayDeque<Integer>();
        assertTrue(lld.equals(ad));

        Deque<String[]> ad1 = new ArrayDeque<String[]>();
        Deque<String[]> ad2 = new LinkedListDeque<String[]>();
        String[] onjDeque = new String[]{"1", "2"};
        String[] onjDeque1 = new String[]{"1", "2"};
        ad1.addLast(onjDeque);
        ad2.addLast(onjDeque1);
    }
}
