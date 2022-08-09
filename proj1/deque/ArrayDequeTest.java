package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /**
     * You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test.
     */

    public static Deque<Integer> ad = new ArrayDeque<Integer>();
    public static Deque<Integer> ad1 = new ArrayDeque<Integer>();
    public static Deque<String> adString = new ArrayDeque<String>();

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
//        assertTrue("A newly initialized adeque should be empty", ad.isEmpty());
//        ad.addFirst(0);
//        assertFalse("ad1 should now contain 1 item", ad.isEmpty());
//        ad = new ArrayDeque<Integer>();
//        //Assigns ad equal to a new, clean ArrayDeque!
//
//        assertTrue("A newly initialized adeque should be empty", adString.isEmpty());
//        adString.addFirst("Not Empty NOW");
//        assertFalse("selfTest should now contain 1 item", adString.isEmpty());
//        adString = new ArrayDeque<String>();
//
//    }

    /**
     * Adds an item, removes an item, and ensures that dll is empty afterwards.
     */
    @Test
    public void addRemoveTest() {
        ad = new ArrayDeque<Integer>();
        ad.addFirst(0);
        ad.addLast(1);
        ad.removeFirst();
        ad.removeLast();
//        assertTrue(ad.isEmpty());
        ad = new ArrayDeque<Integer>();

        adString.addFirst("LIST");
        adString.addLast("LISTLast");
        adString.removeFirst();
        adString.removeLast();
//        assertTrue(adString.isEmpty());
        adString = new ArrayDeque<String>();
    }

    /**
     * Make sure that removing from an empty ArrayDeque does nothing
     */
    @Test
    public void removeEmptyTest() {
        ad = new ArrayDeque<Integer>();
        ad.removeLast();
        assertEquals(0, ad.size());
        ad.removeFirst();
        assertEquals(0, ad.size());
//        assertTrue(ad.isEmpty());
    }

    /**
     * Make sure your ArrayDeque also works on non-Integer types
     */
    @Test
    public void multipleParamsTest() {
        // String test
        Deque<String> adString = new ArrayDeque<String>();
        adString.addLast("1");
        adString.addLast("2");
        adString.addLast("3");
        adString.addLast("4");
        assertEquals(4, adString.size());
        assertSame("1", adString.removeFirst());
        assertEquals(3, adString.size());
        assertSame("2", adString.get(0));
        assertSame("3", adString.get(1));
        assertSame("4", adString.get(2));

        // Remove items until ad is empty
        assertSame("2", adString.removeFirst());
        assertSame("3", adString.removeFirst());
        assertSame("4", adString.removeFirst());
        adString = new ArrayDeque<String>();

        // Int test
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        assertEquals(4, ad.size());
        assertSame(1, ad.removeFirst());
        assertEquals(3, ad.size());
        assertSame(2, ad.get(0));
        assertSame(3, ad.get(1));
        assertSame(4, ad.get(2));

        // Remove items until ad is empty
        assertSame(2, ad.removeFirst());
        assertSame(3, ad.removeFirst());
        assertSame(4, ad.removeFirst());
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Make sure that removing from an empty ArrayDeque returns null
     */
    @Test
    public void emptyNullReturn() {
        assertNull(ad.removeLast());
    }
    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */

    /**
     * Test for addFirst
     **/
    @Test
    public void addFirstTest() {
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        ad.addFirst(4);
        assertEquals(4, ad.size());
        assertEquals(4, (int) ad.get(0));
        assertEquals(3, (int) ad.get(1));
        assertEquals(2, (int) ad.get(2));
        assertEquals(1, (int) ad.get(3));
        ad = new ArrayDeque<Integer>();

        adString.addFirst("1");
        adString.addFirst("2");
        adString.addFirst("3");
        adString.addFirst("4");
        assertEquals(4, adString.size());
        assertEquals("4", adString.get(0));
        assertEquals("3", adString.get(1));
        assertEquals("2", adString.get(2));
        assertEquals("1", adString.get(3));
        ad = new ArrayDeque<Integer>();
        adString = new ArrayDeque<String>();
    }

    /**
     * Test for addLast
     **/
    @Test
    public void addLastTest() {
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        assertEquals(4, ad.size());
        assertEquals(1, (int) ad.get(0));
        assertEquals(2, (int) ad.get(1));
        assertEquals(3, (int) ad.get(2));
        assertEquals(4, (int) ad.get(3));
        ad = new ArrayDeque<Integer>();

        adString.addLast("1");
        adString.addLast("2");
        adString.addLast("3");
        adString.addLast("4");
        assertEquals(4, adString.size());
        assertEquals("1", adString.get(0));
        assertEquals("2", adString.get(1));
        assertEquals("3", adString.get(2));
        assertEquals("4", adString.get(3));
        adString = new ArrayDeque<String>();
    }

    /**
     * Test for removeFirst
     **/
    @Test
    public void removeFirstTest() {
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        assertEquals(4, ad.size());
        assertEquals(1, (int) ad.removeFirst());
        assertEquals(3, ad.size());
        assertEquals(2, (int) ad.get(0));
        assertEquals(3, (int) ad.get(1));
        assertEquals(4, (int) ad.get(2));
        // Remove items until ad is empty
        assertEquals(2, (int) ad.removeFirst());
        assertEquals(3, (int) ad.removeFirst());
        assertEquals(4, (int) ad.removeFirst());
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Test for removeLast
     **/
    @Test
    public void removeLastTest() {
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        assertEquals(4, ad.size());
        assertEquals(4, (int) ad.removeLast());
        assertEquals(3, ad.size());
        assertEquals(1, (int) ad.get(0));
        assertEquals(2, (int) ad.get(1));
        assertEquals(3, (int) ad.get(2));
        // Remove items until ad is empty
        assertEquals(3, (int) ad.removeLast());
        assertEquals(2, (int) ad.removeLast());
        assertEquals(1, (int) ad.removeLast());
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Test for resizing issue: continue to addFirst numbers from 0 to 66;
     **/
    @Test
    public void resizeBiggerFirstTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            ad.addFirst(i);
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(65 - i, (int) ad.get(i));
        }
        ad = new ArrayDeque<Integer>();

    }

    /**
     * Test for resizing issue: continue to addLast numbers from 0 to 66;
     **/
    @Test
    public void resizeBiggerLastTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(i, (int) ad.get(i));
        }
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Test for resizing issue: continue to addFirst numbers from 0 to 66, and then
     * removeFirst 55 items to make sure the array reach the usage threshold;
     **/
    @Test
    public void resizeSmallerFirstTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            assertEquals(i, ad.size());
            ad.addFirst(i);
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(65 - i, (int) ad.get(i));
        }
        for (int i = 0; i < 55; i++) {
            assertEquals(66 - i, ad.size());
            ad.removeFirst();
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(ad.size() - 1 - i, (int) ad.get(i));
        }
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Test for resizing issue: continue to addLast numbers from 0 to 66, and then
     * removeLast 55 items to make sure the array reach the usage threshold;
     **/
    @Test
    public void resizeSmallerLastTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            assertEquals(i, ad.size());
            ad.addLast(i);
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(i, (int) ad.get(i));
        }
        for (int i = 0; i < 55; i++) {
            assertEquals(66 - i, ad.size());
            ad.removeLast();
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(i, (int) ad.get(i));
        }
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Test for resizing issue: continue to addFirst numbers from 0 to 66, and then
     * removeLast 55 items to make sure the array reach the usage threshold;
     **/
    @Test
    public void resizeSmallerFirstLastTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            assertEquals(i, ad.size());
            ad.addFirst(i);
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(65 - i, (int) ad.get(i));
        }
        for (int i = 0; i < 55; i++) {
            assertEquals(66 - i, ad.size());
            ad.removeLast();
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(65 - i, (int) ad.get(i));
        }
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Test for resizing issue: continue to addLast numbers from 0 to 66, and then
     * removeFirst 55 items to make sure the array reach the usage threshold;
     **/
    @Test
    public void resizeSmallerLastFirstTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 66; i++) {
            assertEquals(i, ad.size());
            ad.addLast(i);
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(i, (int) ad.get(i));
        }
        for (int i = 0; i < 55; i++) {
            assertEquals(66 - i, ad.size());
            ad.removeFirst();
        }
        for (int i = 0; i < ad.size(); i++) {
            assertEquals(i + 55, (int) ad.get(i));
        }
        ad = new ArrayDeque<Integer>();
    }

    /**
     * Test for equality
     **/
    @Test
    public void equalsTest() {
        ad = new ArrayDeque<Integer>();
        ad1 = new ArrayDeque<Integer>();
        ad.addLast(0);
        ad.addLast(2);
        ad.addFirst(1);
        ad.addLast(3);
        ad1.addLast(0);
        ad1.addLast(2);
        ad1.addFirst(1);
        ad1.addLast(3);
        assertTrue(ad.equals(ad1));

    }
}
