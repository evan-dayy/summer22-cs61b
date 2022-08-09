package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class DistinctRooms implements Comparable<DistinctRooms> {

    private static final TETile WALL = Tileset.WALL;
    private static final TETile FLOOR = Tileset.FLOOR;

    private final int sizeW;
    private final int sizeH;
    private final int startW;
    private final int startH;
    private final String roomID;
    private final TETile[][] tile;
    private final LinkedList<DistinctRooms> connectedRoom;
    private final HashSet<DistinctRooms> connected;

    private final int bottom;
    private final int top;
    private final int left;
    private final int right;

    ////////////////////////////////////////
    // ********* Rooms Creating **********//
    ////////////////////////////////////////

    public DistinctRooms(TETile[][] tile, int w, int h, int sw, int sh) {
        this.tile = tile;
        //int height = tile[0].length - 1;
        //int width = tile.length - 1;
        this.sizeW = w;
        this.sizeH = h;
        this.startW = sw;
        this.startH = sh;
        this.roomID = startW + "," + startH;
        this.connectedRoom = new LinkedList<DistinctRooms>();
        this.bottom = this.startH;
        this.top = this.startH + this.sizeH;
        this.left = this.startW;
        this.right = this.startW + this.sizeW;
        this.connected = new HashSet<DistinctRooms>();
    }

    public boolean areaCheck() {
        for (int i = startW - 2; i < startW + sizeW + 2; i++) {
            for (int j = startH - 2; j < startH + sizeH + 2; j++) {
                if (i < 0 || j < 0
                        || i > tile.length - 5
                        || j > tile[0].length - 5) {
                    return false;
                }
                if (tile[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean borderCheck() {
        for (int i = startW - 2; i < startW + sizeW + 2; i++) {
            for (int j = startH - 2; j < startH + sizeH + 2; j++) {
                if (i < 0 || j < 0
                        || i > tile.length - 5
                        || j > tile[0].length - 5) {
                    return false;
                }
            }
        }
        return true;
    }

    public void drawBlock() {
        for (int i = startW; i < startW + sizeW; i++) {
            for (int j = startH; j < startH + sizeH; j++) {
                if (i == startW
                        || i == startW + sizeW - 1
                        || j == startH
                        || j == startH + sizeH - 1) {
                    tile[i][j] = WALL;
                } else if (i == getCenterWidth() && j == getCenterHeight()) {
                    tile[i][j] = Tileset.N_LIGHT;
                } else {
                    tile[i][j] = FLOOR;
                }

            }
        }
    }

    public void drawBlockMerge() {
        for (int i = startW; i < startW + sizeW; i++) {
            for (int j = startH; j < startH + sizeH; j++) {

                if (i == startW
                        || i == startW + sizeW - 1
                        || j == startH
                        || j == startH + sizeH - 1) {
                    if (tile[i][j] == Tileset.FLOOR) {
                        continue;
                    }
                    tile[i][j] = WALL;
                } else {
                    if (tile[i][j] == WALL) {
                        tile[i][j] = FLOOR;
                    }
                    tile[i][j] = FLOOR;
                }
            }
        }
    }


    ////////////////////////////////////////
    // ******** Class Attributes *********//
    ////////////////////////////////////////

    public int getSizeW() {
        return sizeW;
    }

    public int getSizeH() {
        return sizeH;
    }

    public int getStartW() {
        return startW;
    }

    public int getStartH() {
        return startH;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getCenterWidth() {
        return roomCenterWidth();
    }

    public int getCenterHeight() {
        return roomCenterHeight();
    }

    public int roomCenterWidth() {
        return (startW + sizeW + startW) / 2;
    }

    public int roomCenterHeight() {
        return (startH + sizeH + startH) / 2;
    }

    public HashSet<DistinctRooms> getConnected() {
        return this.connected;
    }


    ////////////////////////////////////////
    // ******** Hallway Connect  *********//
    ////////////////////////////////////////

    public DistinctRooms belowOne(DistinctRooms other) {
        if (this.isVerticalFace(other)) {
            if (this.roomCenterHeight() > other.roomCenterHeight()) {
                return other;
            }
        }
        return this;
    }

    public DistinctRooms aboveOne(DistinctRooms other) {
        if (this.isVerticalFace(other)) {
            if (this.roomCenterHeight() > other.roomCenterHeight()) {
                return this;
            }
        }
        return other;
    }

    public DistinctRooms leftOne(DistinctRooms other) {
        if (this.isHorizontalFace(other)) {
            if (this.roomCenterWidth() > other.roomCenterWidth()) {
                return other;
            }
        }
        return this;
    }

    public DistinctRooms rightOne(DistinctRooms other) {
        if (this.isHorizontalFace(other)) {
            if (this.roomCenterWidth() > other.roomCenterWidth()) {
                return this;
            }
        }
        return other;
    }

    public boolean isFace(DistinctRooms other) {
        return isVerticalFace(other) || isHorizontalFace(other);
    }

    public boolean isVerticalFace(DistinctRooms other) {
        // perfectly face vertically
        if (other.left >= this.left && other.right <= this.right) {
            return other.right - other.left >= 5;
        }

        // right concave
        if (other.left >= this.left
                && other.left < this.right
                && Math.abs(this.right - other.left) >= 5) {
            return true;
        }

        // left con cave
        return other.right >= this.left
                && other.right < this.right
                && Math.abs(this.left - other.right) >= 5;
    }

    public int startPointVertical(DistinctRooms other) {
        // perfectly face vertically
        if (other.left >= this.left && other.right <= this.right) {
            return other.left;
        }
        // right concave
        if (other.left >= this.left
                && other.left < this.right
                && Math.abs(this.right - other.left) >= 5) {
            return other.left;
        }
        return this.left;
    }

    public int endPointVertical(DistinctRooms other) {
        // perfectly face vertically
        if (other.left >= this.left && other.right <= this.right) {
            return other.right;
        }
        // right concave
        if (other.left >= this.left
                && other.left < this.right
                && Math.abs(this.right - other.left) >= 5) {
            return this.right;
        }
        return other.right;
    }


    public boolean isHorizontalFace(DistinctRooms other) {
        if (other.bottom >= this.bottom && other.top <= this.top) {
            return other.top - other.bottom >= 5;
        }

        // right concave
        if (other.bottom >= this.bottom
                && other.bottom <= this.top
                && Math.abs(this.top - other.bottom) >= 5) {
            return true;
        }

        // left con cave
        return other.top >= this.bottom
                && other.top <= this.top
                && Math.abs(this.bottom - other.top) >= 5;
    }

    public int startPointHorizontal(DistinctRooms other) {
        if (other.bottom >= this.bottom && other.top <= this.top) {
            return other.bottom;
        }

        // right concave
        if (other.bottom >= this.bottom
                && other.bottom < this.top
                && Math.abs(this.top - other.bottom) >= 5) {
            return other.bottom;
        } else {
            return this.bottom;
        }
    }

    public int endPointHorizontal(DistinctRooms other) {
        if (other.bottom >= this.bottom && other.top <= this.top) {
            return other.top;
        }

        // right concave
        if (other.bottom >= this.bottom
                && other.bottom < this.top
                && Math.abs(this.top - other.bottom) >= 5) {
            return this.top;
        } else {
            return other.top;
        }

    }


    public double distance(DistinctRooms other) {
        double a = this.roomCenterHeight() - other.roomCenterHeight();
        double b = this.roomCenterWidth() - other.roomCenterWidth();
        return a * a + b * b;
    }


    public DistinctRooms findNearestRoom(ArrayList<DistinctRooms> arr) {
        DistinctRooms nearest = null;
        double dist = Integer.MAX_VALUE;
        for (DistinctRooms curr : arr) {
            if (this.distance(curr) < dist) {
                nearest = curr;
                dist = this.distance(curr);
            }
        }
        return nearest;
    }

    @Override
    public int compareTo(DistinctRooms other) {
        int diff = this.startW - other.startW;
        int diff2 = this.startH - other.startH;
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return Integer.compare(diff2, 0);
        }
    }
}
