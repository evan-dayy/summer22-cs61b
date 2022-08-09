import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MeasurementTest {
    @Test
    public void Measurement_0() {
        // TODO: stub for first test
        Measurement m = new Measurement();
        assertEquals(0, m.getFeet());
        assertEquals(0, m.getInches());
    }

    @Test
    public void Measurement_1() {
        Measurement m = new Measurement(4);
        assertEquals(4, m.getFeet());
        assertEquals(0, m.getInches());
    }

    @Test
    public void Measurement_2() {
        Measurement m = new Measurement(4, 3);
        assertEquals(4, m.getFeet());
        assertEquals(3, m.getInches());
    }


    @Test
    public void getFeet() {
        Measurement m = new Measurement(4, 3);
        Measurement m1 = new Measurement(2);
        Measurement m2 = new Measurement();
        assertEquals(4, m.getFeet());
        assertEquals(2, m1.getFeet());
        assertEquals(0, m2.getFeet());
    }

    @Test
    public void getInches() {
        Measurement m = new Measurement(4, 3);
        Measurement m1 = new Measurement(2);
        Measurement m2 = new Measurement();
        assertEquals(3, m.getInches());
        assertEquals(0, m1.getInches());
        assertEquals(0, m2.getFeet());
    }

    @Test
    public void plus() {
        Measurement m = new Measurement(4, 3);
        Measurement m2 = new Measurement(1,4);
        Measurement m3 = new Measurement(3, 9);
        Measurement m4 = new Measurement(3,11);

        assertEquals(new Measurement(5, 7), m.plus(m2));
        assertEquals(new Measurement(8, 0), m.plus(m3));
        assertEquals(new Measurement(8, 2), m.plus(m4));

    }

    @Test
    public void minus() {
        Measurement m = new Measurement(4, 8);
        Measurement m2 = new Measurement(1,4);
        Measurement m3 = new Measurement(1,8);
        Measurement m4 = new Measurement(3,9);

        assertEquals(new Measurement(3, 4), m.minus(m2));
        assertEquals(new Measurement(3, 0), m.minus(m3));
        assertEquals(new Measurement(0, 11), m.minus(m4));

    }


    @Test
    public void multiply() {
        Measurement m = new Measurement(0, 7);
        Measurement m2 = new Measurement(1, 7);
        Measurement m3 = new Measurement(2, 7);
        Measurement m4 = new Measurement(2);

        assertEquals(new Measurement(1, 9), m.multiple(3));
        assertEquals(new Measurement(4, 9), m2.multiple(3));
        assertEquals(new Measurement(7, 9), m3.multiple(3));
        assertEquals(new Measurement(6, 0), m4.multiple(3));
    }

    // TODO: Add additional JUnit tests for Measurement.java here.

    @Test
    public void tostring() {
        Measurement m = new Measurement(1, 7);
        assertEquals("1'7\"", m.toString());
    }

}
