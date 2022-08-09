import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BooleanSetTest {

    @Test
    public void testBasics() {
        BooleanSet aSet = new BooleanSet(100);
        assertEquals(0, aSet.size());
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());

        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());

        BooleanSet bSet = new BooleanSet(10);

        for (int i = 0; i < 10; i += 2) {
            bSet.add(i);
            assertTrue(bSet.contains(i));
        }
        int[] g = bSet.toIntArray();
        int[] h = new int[]{0,2,4,6,8};
        assertArrayEquals(g, h);

    }
}
