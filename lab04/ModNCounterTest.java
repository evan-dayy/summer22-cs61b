import org.junit.Test;

import static org.junit.Assert.*;

public class ModNCounterTest {

    @Test
    public void testConstructor() {
        ModNCounter c = new ModNCounter(2);
        assertEquals(0, c.value());
    }

    @Test
    public void increment() {
        ModNCounter c = new ModNCounter(2);
        // c.increment();
        // assertEquals(1, c.value());
        // c.increment();
        // assertEquals(0, c.value());
        // c.increment();
        // assertEquals(1, c.value());
        // c.increment();
        // assertEquals(0, c.value());
        // c.increment();
        // assertEquals(1, c.value());
        for (int i = 0; i <= 10; i ++){
            c.increment();
            if(i % 2 == 0){
                assertEquals(1, c.value());
            } else{
                assertEquals(0, c.value());
            }
        }

    }

    @Test
    public void reset() {
        ModNCounter c = new ModNCounter(2);
        c.increment();
        c.reset();
        assertEquals(0, c.value());
    }
}