package game2048;

import org.junit.Test;

import static org.junit.Assert.*;

/** Tests the tilt() method in the up (Side.NORTH) direction only.
 *
 * @author Omar Khan
 */
public class TestUpOnly extends TestUtils {

    /** Move tiles up (no merging). */
    @Test
    public void testUpNoMerge() {
        int[][] before = new int[][] {
                {1, 1, 1, 1},
                {1, 3, 1, 2},
                {1, 2, 3, 2}, // it works when no values in the last line...
                {1, 4, 3, 4},
        };
        int[][] after = new int[][] {
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
        };

        model = new Model(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 12, 0, prevBoard, Side.NORTH);
    }

    /** A basic merge. */
    @Test
    public void testUpBasicMerge() {
        int[][] before = new int[][] {
                {1, 2, 0, 0},
                {0, 0, 0, 0},
                {2, 2, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][] {
                {0, 8, 4, 16},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 28, 0, prevBoard, Side.NORTH);
    }

    /** A triple merge. Only the leading 2 tiles should merge. */
    @Test
    public void testUpTripleMerge() {
        int[][] before = new int[][] {
                {2, 0, 0, 2},
                {0, 2, 0, 0},
                {0, 0, 2, 4},
                {4, 3, 2, 4},
        };
        int[][] after = new int[][] {
                {4, 4, 4, 4},
                {4, 2, 2, 4},
                {0, 0, 0, 0},
                {4, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 12, 0, prevBoard, Side.NORTH);
    }

    /** A tricky merge.
     *
     * The tricky part here is that the 4 tile on the bottom row shouldn't
     * merge with the newly created 4 tile on the top row. If you're failing
     * this test, try seeing how you can ensure that the bottom 4 tile doesn't
     * merge with the newly created 4 tile on top.
     */
    @Test
    public void testUpTrickyMerge() {
        int[][] before = new int[][] {
                {2, 4, 4, 4},
                {2, 0, 2, 4},
                {4, 2, 2, 0},
                {0, 2, 0, 2},
        };
        int[][] after = new int[][] {
                {4, 4, 4, 4},
                {4, 4, 4, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 16, 0, prevBoard, Side.NORTH);
    }


}
