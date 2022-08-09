package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class WorldGenerator {
    private final int WIDTH;
    private final int HEIGHT;
    private final PriorityQueue<DistinctRooms> DISTINCTROOMSPQ;
    private final ArrayList<DistinctRooms> DISTINCTROOMSLIST;
    private final TETile[][] randomTiles;

    ////////////////////////////////////////
    // ******* Random Parameters *********//
    ////////////////////////////////////////
    private final long SEED;
    private final Random RANDOM;

    public WorldGenerator(TETile[][] tiles, int width, int height, long seed) {
        this.randomTiles = tiles;
        this.WIDTH = width;
        this.HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(SEED);
        DISTINCTROOMSPQ = new PriorityQueue<>();
        DISTINCTROOMSLIST = new ArrayList<>();
    }

    private int randomWidth() {
        return RANDOM.nextInt(6) + 10;
    }

    private int randomHeight() {
        return RANDOM.nextInt(5) + 10;
    }

    private int randomStartW(int bound) {
        return RANDOM.nextInt(bound) + 5;
    }

    private int randomStartH(int bound) {
        return RANDOM.nextInt(bound) + 5;
    }

    private int randomCoin() {
        return RANDOM.nextInt(5) + 10;
    }

    private int randomCoinF() {
        return RANDOM.nextInt(10) + 20;
    }

    private int randomEnergy() {
        return RANDOM.nextInt(5) + 10;
    }

    private int randomPosW() {
        return RANDOM.nextInt(WIDTH);
    }

    private int randomPosH() {
        return RANDOM.nextInt(HEIGHT);
    }

    private int randomDoorW() {
        return RANDOM.nextInt(WIDTH / 2);
    }

    private int randomDoorH() {
        return RANDOM.nextInt(HEIGHT / 2);
    }

    private int randomForestW() {
        return RANDOM.nextInt(5) + 40;
    }

    private int randomForestH() {
        return RANDOM.nextInt(5) + 20;
    }

    private int randomForeststartW() {
        return RANDOM.nextInt(5) + 35;
    }

    private int randomForeststartH() {
        return RANDOM.nextInt(5) + 20;
    }

    ////////////////////////////////////////
    // ********* Coins and Energy ********//
    ////////////////////////////////////////
    public void createDistinctCoin(TETile[][] tiles) {
        int x = randomPosW();
        int y = randomPosH();
        int num = randomCoin();
        for (int i = 0; i <= num; i++)
            if (tiles[x][y] != Tileset.FLOOR) {
                i--;
                x = randomPosW();
                y = randomPosH();
            } else {
                tiles[x][y] = Tileset.COIN;
            }
    }

    public void createDistinctCoinForest(TETile[][] tiles) {
        int x = randomPosW();
        int y = randomPosH();
        int num = randomCoinF();
        for (int i = 0; i <= num; i++)
            if (tiles[x][y] != Tileset.FLOOR) {
                i--;
                x = randomPosW();
                y = randomPosH();
            } else {
                tiles[x][y] = Tileset.COIN;
            }
    }

    public void createForest(TETile[][] tiles) {

        int w = randomForestW();
        int h = randomForestH();
        int sw = randomForeststartW();
        int sh = randomForeststartH();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
        DistinctRooms distinctRooms = new DistinctRooms(tiles, w, h, sw, sh);
        distinctRooms.drawBlock();
        createDistinctCoinForest(tiles);
    }

    public void createDistinctEnergy(TETile[][] tiles) {
        int x = randomPosW();
        int y = randomPosH();
        int num = randomEnergy();
        for (int i = 0; i <= num; i++)
            if (tiles[x][y] != Tileset.FLOOR) {
                i--;
                x = randomPosW();
                y = randomPosH();
            } else {
                tiles[x][y] = Tileset.ENERGY;
            }
    }

    public void createExitDoor(TETile[][] tiles) {
        int x = randomDoorW();
        int y = randomDoorH();
        int num = randomEnergy();
        for (int i = 0; i <= num; i++)
            if ((tiles[x][y] == Tileset.WALL
                    && tiles[x - 1][y] == Tileset.WALL && tiles[x + 1][y] == Tileset.WALL)
                    || (tiles[x][y] == Tileset.WALL && tiles[x][y - 1] == Tileset.WALL
                    && tiles[x][y + 1] == Tileset.WALL)) {
                tiles[x][y] = Tileset.LOCKED_DOOR;
                break;
            } else {
                i--;
                x = randomDoorW();
                y = randomDoorH();
            }
    }

    public void createSmallBox(TETile[][] tiles) {
        int x = randomDoorW();
        int y = randomDoorH();
        for (int i = 0; i < 1; i++)
            if ((tiles[x][y] == Tileset.FLOOR
                    && tiles[x - 1][y] == Tileset.FLOOR
                    && tiles[x + 1][y] == Tileset.FLOOR)
                    || (tiles[x][y] == Tileset.FLOOR
                    && tiles[x][y - 1] == Tileset.FLOOR
                    && tiles[x][y + 1] == Tileset.FLOOR)) {
                tiles[x][y] = Tileset.TREE;
                break;
            } else {
                i--;
                x = randomDoorW();
                y = randomDoorH();
            }
    }


    ////////////////////////////////////////
    // ********* ROOMS RENDERing *********//
    ////////////////////////////////////////

    public void createDistinctRooms(TETile[][] tiles) {
        int w = randomWidth();
        int h = randomHeight();
        int sw = randomStartW(WIDTH - w);
        int sh = randomStartH(HEIGHT - h);
        DistinctRooms distinctRooms = new DistinctRooms(tiles, w, h, sw, sh);
        if (distinctRooms.areaCheck()) {
            DISTINCTROOMSLIST.add(distinctRooms);
            DISTINCTROOMSPQ.add(distinctRooms);
            distinctRooms.drawBlock();
        }
    }

    public void createRoomsMerge(TETile[][] tiles) {
        int w = randomWidth();
        int h = randomHeight();
        int sw = randomStartW(WIDTH - w);
        int sh = randomStartH(HEIGHT - h);
        DistinctRooms distinctRooms = new DistinctRooms(tiles, w, h, sw, sh);
        if (distinctRooms.borderCheck()) {
            DISTINCTROOMSLIST.add(distinctRooms);
            DISTINCTROOMSPQ.add(distinctRooms);
            distinctRooms.drawBlockMerge();
        }

    }

    public void fillBlack(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void fillBlack(TETile[][] tiles, int w, int h, int sizew, int sizeh) {
        for (int x = w; x < w + sizew; x += 1) {
            for (int y = h; y < h + sizeh; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public boolean connectCheck(TETile[][] tiles, int w, int h, int sizew, int sizeh) {
        for (int y = h; y < h + sizeh; y += 1) {
            if (tiles[w - 1][y] != Tileset.NOTHING) {
                return true;
            }
        }

        for (int y = h; y < h + sizeh; y += 1) {
            if (tiles[w + sizew][y] != Tileset.NOTHING) {
                return true;
            }
        }

        for (int x = w - 1; x < w + sizew; x += 1) {
            if (tiles[x][h - 1] != Tileset.NOTHING) {
                return true;
            }
        }

        for (int x = w - 1; x < w + sizew; x += 1) {
            if (tiles[x][h + sizeh] != Tileset.NOTHING) {
                return true;
            }
        }

        return false;
    }

    public void startIsland(TETile[][] tiles) {
        boolean flag = false;
        for (int x = WIDTH - 1; x > 0; x--) {
            if (flag) {
                break;
            }
            for (int y = 1; y < HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.FLOOR) {
                    tiles[x][y] = Tileset.AVATAR;
                    flag = true;
                    break;
                }
            }
        }
    }


    ////////////////////////////////////////
    // ******** HALLWAYS RENDERing *******//
    ////////////////////////////////////////

    public void drawTurnBlock(int centerW, int centerH, TETile[][] tile) {
        for (int i = centerW - 1; i <= centerW + 1; i++) {
            for (int j = centerH - 1; j <= centerH + 1; j++) {
                if (i == centerW && j == centerH) {
                    tile[i][j] = Tileset.FLOOR;
                } else {
                    tile[i][j] = Tileset.WALL;
                }
            }
        }
    }

    public boolean turnCheck(int centerW, int centerH, TETile[][] tile) {
        for (int i = centerW - 1; i <= centerW + 1; i++) {
            for (int j = centerH - 1; j <= centerH + 1; j++) {
                if (tile[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public void createHallWay(TETile[][] tiles, DistinctRooms curr, DistinctRooms next) {
        if (curr.isVerticalFace(next)) {
            DistinctRooms from = curr.belowOne(next);
            DistinctRooms to = curr.aboveOne(next);
            int startPoint = to.startPointVertical(from);
            int endPoint = to.endPointVertical(from);
            int randomStartPos = (startPoint + endPoint) / 2;
            Hallway way = new Hallway(tiles, randomStartPos, from.getTop() - 1);
            way.drawStraightVertical();
        } else if (curr.isHorizontalFace(next)) {
            DistinctRooms from = curr.leftOne(next);
            DistinctRooms to = curr.rightOne(next);
            int startPoint = to.startPointHorizontal(from);
            int endPoint = to.endPointHorizontal(from);
            int randomStartPos = (startPoint + endPoint) / 2;
            Hallway way = new Hallway(tiles, from.getRight() - 1, randomStartPos);
            way.drawStraightHorizontal();
        } else {
            int currCenterW = curr.getCenterWidth();
            int currCenterH = curr.getCenterHeight();
            int nextCenterW = next.getCenterWidth();
            int nextCenterH = next.getCenterHeight();
            if (!turnCheck(currCenterW, nextCenterH, tiles)) {
                return;
            }
            drawTurnBlock(currCenterW, nextCenterH, tiles);

            if (currCenterW < nextCenterW && currCenterH < nextCenterH) {
                Hallway wayRight = new Hallway(tiles, currCenterW + 1, nextCenterH - 1);
                Hallway wayDown = new Hallway(tiles, currCenterW - 1, nextCenterH - 1);
                wayRight.drawStraightHorizontal();
                wayDown.drawStraightVerticalDown();
            } else if (currCenterW < nextCenterW && currCenterH > nextCenterH) {
                Hallway wayRight = new Hallway(tiles, currCenterW + 1, nextCenterH - 1);
                Hallway wayUp = new Hallway(tiles, currCenterW - 1, nextCenterH + 1);
                wayRight.drawStraightHorizontal();
                wayUp.drawStraightVertical();
            } else if (currCenterH > nextCenterH && currCenterW > nextCenterW) {
                Hallway wayLeft = new Hallway(tiles, currCenterW - 1, nextCenterH - 1);
                Hallway wayUp = new Hallway(tiles, currCenterW - 1, nextCenterH + 1);
                wayUp.drawStraightVertical();
                wayLeft.drawStraightHorizontalLeft();
            } else {
                Hallway wayLeft = new Hallway(tiles, currCenterW - 1, nextCenterH - 1);
                Hallway wayDown = new Hallway(tiles, currCenterW - 1, nextCenterH - 1);
                wayLeft.drawStraightHorizontalLeft();
                wayDown.drawStraightVerticalDown();
            }
        }
    }


    ////////////////////////////////////////
    // *********** Generation ************//
    ////////////////////////////////////////

    public void generator() {
        //  Drawing a set of random rooms:
        int num = 5;
        int num1 = 100;
        fillBlack(randomTiles);
        //TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);
        for (int i = 0; i < num; i++) {
            createRoomsMerge(randomTiles);
            //ter.renderFrame(randomTiles);

        }
        for (int i = 0; i < num1; i++) {
            createDistinctRooms(randomTiles);
            //ter.renderFrame(randomTiles);
        }

        // connecting with Hallways
        for (int i = 0; i < DISTINCTROOMSLIST.size(); i++) {
            ArrayList<DistinctRooms> lst = (ArrayList<DistinctRooms>) DISTINCTROOMSLIST.clone();
            DistinctRooms curr = DISTINCTROOMSPQ.poll();
            lst.remove(curr);
            DistinctRooms next;
            for (int t = 0; t <= 1; t++) {
                if (lst.size() == 0) {
                    break;
                }
                if (curr == null) {
                    break;
                }
                next = curr.findNearestRoom(lst);
                if (curr.getConnected().size() > 3) {
                    break;
                }
                lst.remove(next);
                if (!curr.getConnected().contains(next)) {
                    createHallWay(randomTiles, curr, next);
                    //ter.renderFrame(randomTiles);
                    //StdDraw.pause(500);
                    curr.getConnected().add(next);
                    next.getConnected().add(curr);
                } else {
                    t--;
                }
            }

        }
        // for the unconnected room
        for (DistinctRooms rooms : DISTINCTROOMSLIST) {
            int w = rooms.getStartW();
            int h = rooms.getStartH();
            int sizew = rooms.getSizeW();
            int sizeh = rooms.getSizeH();
            if (!connectCheck(randomTiles, w, h, sizew, sizeh)) {
                fillBlack(randomTiles, w, h, sizew, sizeh);
            }
        }

        startIsland(randomTiles);
        createDistinctCoin(randomTiles);
        createDistinctEnergy(randomTiles);
        createExitDoor(randomTiles);
        createSmallBox(randomTiles);
    }

}
