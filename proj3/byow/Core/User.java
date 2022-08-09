package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class User implements Serializable {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File MOVE = Utils.join(CWD, "move.txt");
    public static final File MOVEF = Utils.join(CWD, "moveForest.txt");
    public static final File CLICK = Utils.join(CWD, "light.txt");
    public static final File MAKE = Utils.join(CWD, "make.txt");
    public static final File FORESTX = Utils.join(CWD, "x.txt");
    public static final File FORESTY = Utils.join(CWD, "y.txt");
    public static final File SEED = Utils.join(CWD, "seed.txt");
    public static final int WIDTHFRAME = 40;
    public static final int HEIGHTFRAME = 60;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 60;
    private final TERenderer ter = new TERenderer();
    private ArrayList<Point> lightList = new ArrayList<>();
    private ArrayList<Point> lightList1 = new ArrayList<>();
    private ArrayList<Point> make1 = new ArrayList<>();
    private ArrayList<Point> make2 = new ArrayList<>();
    private int HD = 200;
    private int aX = 50;
    private int aY = 40;
    private boolean given = false;
    private boolean record = false;
    private boolean repeat = false;
    private boolean success = false;
    private int coinCollect = 0;
    private String click = "";
    private int AVATAR_X;
    private int AVATAR_Y;
    private int DOOR_X;
    private int DOOR_Y;
    private long seed;
    private double mouseX;
    private double mouseY;
    private String moveRecord = "";
    private String moveRecordForest = "";
    private TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
    private Random rand;

    //////////////////////////////////////////////
    //////* Attributes and Constructor *//////////
    //////////////////////////////////////////////

    public User() {
        StdDraw.setCanvasSize(WIDTHFRAME * 16, HEIGHTFRAME * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTHFRAME);
        StdDraw.setYscale(0, HEIGHTFRAME);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.rand = new Random(seed);
    }

    public void setTile(TETile[][] a) {
        this.finalWorldFrame = a;
    }


    //////////////////////////////////////////////
    ////////////////////* DRAW *//////////////////
    //////////////////////////////////////////////


    public void drawHeader(String click, String coin, String energy) {
        // StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontBig);
        StdDraw.text(9, HEIGHT - 1.8, click);
        StdDraw.text(WIDTH / 2.0, HEIGHT - 1.8, "Coin: " + coin + "/10");
        StdDraw.text(WIDTH - 8, HEIGHT - 1.8, "Energy: " + energy);
        StdDraw.line(0, HEIGHT - 3, WIDTH, HEIGHT - 3);
        StdDraw.show();
    }

    public void drawSuccess() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTHFRAME / 2, HEIGHTFRAME / 2, "Congrats! You win the Game!");
        StdDraw.text(WIDTHFRAME / 2, HEIGHTFRAME / 2 - 2, "Press R to Start a new One!");
        StdDraw.text(WIDTHFRAME / 2, HEIGHTFRAME / 2 - 4, "Press Q to exit");
        StdDraw.show();
    }

    public void drawFail() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTHFRAME / 2, HEIGHTFRAME / 2, "Ohhhhh! You lose the Game :(");
        StdDraw.text(WIDTHFRAME / 2, HEIGHTFRAME / 2 - 2, "Press R to Start a new One!");
        StdDraw.text(WIDTHFRAME / 2, HEIGHTFRAME / 2 - 4, "Press Q to exit");
        StdDraw.show();
    }

    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTHFRAME / 2, HEIGHTFRAME / 2, s);
        StdDraw.show();
    }

    public void drawMenu(String s, String s1, String s2, String s3, String s4, String name1, String name2) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 3.0 * HEIGHTFRAME / 4, s);
        Font fontSmall = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 2.5 * HEIGHTFRAME / 4, name1 + ", " + name2);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 2.2 * HEIGHTFRAME / 4, s1);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 2.1 * HEIGHTFRAME / 4, s2);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 2.0 * HEIGHTFRAME / 4, s3);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 1.9 * HEIGHTFRAME / 4, s4);
        StdDraw.show();
    }

    public void drawHeading(String s) {
        drawFrame(s);
        StdDraw.pause(500);
        drawFrame("3");
        StdDraw.pause(500);
        drawFrame("2");
        StdDraw.pause(500);
        drawFrame("1");
        StdDraw.pause(500);
        drawFrame("GO!");
        StdDraw.pause(200);
    }

    public void drawColonQuit(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontBig);
        StdDraw.textLeft(8, 2, s);
        StdDraw.show();
    }

    public void display(TETile[][] t, String s) {
        ter.renderFrame(t);
        drawHeader(s, String.valueOf(coinCollect), String.valueOf(HD));
        StdDraw.pause(100);
        click = "";
    }


    //////////////////////////////////////////////
    ////////////////////* Menu *//////////////////
    //////////////////////////////////////////////


    public void menu() {
        drawMenu("CS61B: THE DUNGEON",
                "New Game (N)",
                "Load Game (L)",
                "Repeat Game (R)",
                "Quit (Q)",
                "Evan Day",
                "Aldrin Ong");
        menuInput();
    }

    public void menuInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                if (target == 'N' || target == 'n') {
                    HD = 200;
                    coinCollect = 0;
                    moveRecord = "";
                    moveRecordForest = "";
                    success = false;
                    lightList.clear();
                    lightList1.clear();
                    repeat = false;
                    record = false;
                    given = false;
                    Utils.writeContents(MOVE, "");
                    Utils.writeContents(SEED, "");
                    Utils.writeContents(MOVEF, "");
                    Utils.writeContents(FORESTX, "");
                    Utils.writeContents(FORESTY, "");
                    Utils.writeObject(CLICK, new ArrayList<Point>());
                    Utils.writeObject(MAKE, new ArrayList<Point>());
                    drawFrame("Please enter a SEED: ");
                    long rt = seedInput();
                    setSeed(rt);
                    drawHeading("Game Start in ...");
                    initialTile();
                    move(finalWorldFrame, false);
                    break;
                } else if (target == 'L' || target == 'l') {
                    long rt = Long.parseLong(Utils.readContentsAsString(SEED));
                    String movement = Utils.readContentsAsString(MOVE);
                    setSeed(rt);
                    drawHeading("Game Loading in ...");
                    initialTile();
                    lightList = Utils.readObject(CLICK, ArrayList.class);
                    lightList1 = (ArrayList<Point>) lightList.clone();
                    make1 = Utils.readObject(MAKE, ArrayList.class);
                    make2 =  (ArrayList<Point>) make1.clone();
                    aX = Integer.valueOf(Utils.readContentsAsString(FORESTX));
                    aY = Integer.valueOf(Utils.readContentsAsString(FORESTY));

                    boolean flag = false;
                    moveRecord(finalWorldFrame, movement, flag, false, false);
                    move(finalWorldFrame, false);
                    break;
                } else if (target == 'Q' || target == 'q') {
                    drawFrame("Bye! See u next time! ");
                    StdDraw.pause(1000);
                    StdDraw.pause(1000);
                    StdDraw.clear();
                    break;
                } else if (target == 'R' || target == 'r') {
                    long rt = Long.parseLong(Utils.readContentsAsString(SEED));
                    String movement = Utils.readContentsAsString(MOVE);
                    setSeed(rt);
                    drawHeading("Game Repeating in ...");
                    initialTile();
                    findUser();
                    findDoor();
                    lightList = Utils.readObject(CLICK, ArrayList.class);
                    lightList1 = (ArrayList<Point>) lightList.clone();
                    make1 = Utils.readObject(MAKE, ArrayList.class);
                    make2 =  (ArrayList<Point>) make1.clone();
                    aX = Integer.valueOf(Utils.readContentsAsString(FORESTX));
                    aY = Integer.valueOf(Utils.readContentsAsString(FORESTY));
                    boolean flag = false;
                    moveRecord(finalWorldFrame, movement, flag, true, false);
                    menu();
                    break;
                }
            }
        }
    }

    public long seedInput() {
        String title = "Please enter a SEED: ";
        String rT = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                if (target == 's' || target == 'S') {
                    break;
                }
                rT = rT + target;
                drawFrame(title + rT);
                StdDraw.clear();
            }
        }
        return Long.parseLong(rT);
    }


    //////////////////////////////////////////////
    //////////////////* Initial */////////////////
    //////////////////////////////////////////////
    public TETile[][] initialTile() {
        WorldGenerator generator = new WorldGenerator(finalWorldFrame, WIDTH, HEIGHT, this.seed);
        generator.generator();
        ///////////* DRAW *//////////////
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        drawHeader(click, String.valueOf(coinCollect), String.valueOf(HD));
        setTile(finalWorldFrame);
        findUser();
        findDoor();
        return finalWorldFrame;
    }

    public void findUser() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (finalWorldFrame[x][y] == Tileset.AVATAR) {
                    AVATAR_X = x;
                    AVATAR_Y = y;
                }
            }
        }
    }

    public void findDoor() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (finalWorldFrame[x][y] == Tileset.LOCKED_DOOR) {
                    DOOR_X = x;
                    DOOR_Y = y;
                }
            }
        }
    }

    //////////////////////////////////////////////
    ////////////////* Move Limits *///////////////
    //////////////////////////////////////////////
    public boolean moveLimit(TETile[][] t, int startX, int startY, int moveInX, int moveInY) {
        return startY + moveInY >= HEIGHT - 1 || startY + moveInY < 0
                || startX + moveInX >= WIDTH - 1 || startX + moveInX < 0
                || t[startX + moveInX][startY + moveInY] == Tileset.WALL
                || t[startX + moveInX][startY + moveInY] == Tileset.LIGHT
                || t[startX + moveInX][startY + moveInY] == Tileset.N_LIGHT
                || t[startX + moveInX][startY + moveInY] == Tileset.LOCKED_DOOR;
    }

    public void nonFloorAreaMove(TETile[][] t, int startX, int startY, int moveInX, int moveInY) {
        TETile go = t[startX + moveInX][startY + moveInY];
        if (go == Tileset.FLOOR_L2) {
            t[startX + moveInX][startY + moveInY] = Tileset.AVATAR_L2;
            if (t[startX][startY] == Tileset.AVATAR) {
                t[startX][startY] = Tileset.FLOOR;
            } else if (t[startX][startY] == Tileset.AVATAR_L2) {
                t[startX][startY] = Tileset.FLOOR_L2;
            } else if (t[startX][startY] == Tileset.AVATAR_L1) {
                t[startX][startY] = Tileset.FLOOR_L1;
            }
        } else if (go == Tileset.FLOOR && t[startX][startY] == Tileset.AVATAR_L2) {
            t[startX + moveInX][startY + moveInY] = Tileset.AVATAR;
            t[startX][startY] = Tileset.FLOOR_L2;
        } else if (go == Tileset.FLOOR_L1) {
            t[startX + moveInX][startY + moveInY] = Tileset.AVATAR_L1;
            if (t[startX][startY] == Tileset.AVATAR_L2) {
                t[startX][startY] = Tileset.FLOOR_L2;
            } else {
                t[startX][startY] = Tileset.FLOOR_L1;
            }
        } else if (go == Tileset.ENERGY) {
            HD += 10;
            if (t[startX][startY] == Tileset.AVATAR_L2) {
                t[startX + moveInX][startY + moveInY] = Tileset.AVATAR;
                t[startX][startY] = Tileset.FLOOR_L2;
            } else {
                t[startX + moveInX][startY + moveInY] = Tileset.AVATAR;
                t[startX][startY] = Tileset.FLOOR;
            }
        } else if (go == Tileset.COIN) {
            coinCollect += 1;
            if (t[startX][startY] == Tileset.AVATAR_L2) {
                t[startX + moveInX][startY + moveInY] = Tileset.AVATAR;
                t[startX][startY] = Tileset.FLOOR_L2;
            } else {
                t[startX + moveInX][startY + moveInY] = Tileset.AVATAR;
                t[startX][startY] = Tileset.FLOOR;
            }

        } else if (go == Tileset.TREE) {
            int initX = AVATAR_X;
            int initY = AVATAR_Y;
            //moveRecord = moveRecord + 'f';
            if (record) {
                forestRecord();
            } else if (repeat) {
                forestRepeat();
            } else {
                forest();
            }
            recoverTreeMove(finalWorldFrame, moveInX, moveInY, initX, initY);
        } else {
            t[startX + moveInX][startY + moveInY] = Tileset.AVATAR;
            t[startX][startY] = Tileset.FLOOR;
        }
    }

    public void recoverTreeMove(TETile[][] t, int moveInX, int moveInY, int initX, int initY) {
        AVATAR_X = initX + moveInX;
        AVATAR_Y = initY + moveInY;
        t[initX + moveInX][initY + moveInY] = Tileset.AVATAR;
        t[initX][initY] = Tileset.FLOOR;
    }

    public boolean colonQuit(TETile[][] t, String s) {
        ter.renderFrame(t);
        drawColonQuit(":");
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                // I want it to be show in the left conor
                char spec = StdDraw.nextKeyTyped();
                if (spec == 'q' || spec == 'Q') {
                    Utils.writeContents(MOVE, moveRecord);
                    Utils.writeContents(SEED, String.valueOf(seed));
                    Utils.writeObject(CLICK, lightList);
                    Utils.writeObject(MAKE, make1);
                    Utils.writeContents(FORESTX, String.valueOf(aX));
                    Utils.writeContents(FORESTY, String.valueOf(aY));
                    ter.renderFrame(t);
                    drawColonQuit(":" + spec);
                    menu();
                    return true;
                } else {
                    ter.renderFrame(t);
                    drawColonQuit(s);
                    StdDraw.pause(1000);
                    return false;
                }
            }
        }
    }

    //////////////////////////////////////////////
    /////////////////* MOUSE *////////////////////
    //////////////////////////////////////////////
    public void trackPressing(TETile[][] t, int mX, int mY) {
        TETile here = t[mX][mY];
        if (t[mX][mY] == Tileset.N_LIGHT) {
            click = "Open Light Source!";
            Point src1 = new Point(mX, mY);
            moveRecord += 'o';
            lightList.add(src1);
            openLight(mX, mY);
            here = t[mX][mY];
        } else if (t[mX][mY] == Tileset.LIGHT) {
            click = "Close Light Source!";
            Point src = new Point(mX, mY);
            moveRecord += 'f';
            lightList.add(src);
            closeLight(mX, mY);
            here = t[mX][mY];
        } else if ((t[mX][mY] == Tileset.WALL && t[mX+1][mY] == Tileset.WALL)
                || (t[mX][mY] == Tileset.WALL && t[mX - 1][mY] == Tileset.WALL)
                || (t[mX][mY] == Tileset.NOTHING && t[mX][mY+1] == Tileset.FLOOR)
                || (t[mX][mY] == Tileset.NOTHING && t[mX][mY-1] == Tileset.FLOOR)) {
            t[mX][mY] = Tileset.FLOOR;
            t[mX+1][mY] = Tileset.WALL;
            t[mX-1][mY] = Tileset.WALL;
            moveRecord += 't';
            Point src3 = new Point(mX, mY);
            make1.add(src3);
        }
        else if ((t[mX][mY] == Tileset.WALL && t[mX][mY - 1] == Tileset.WALL)
                || (t[mX][mY] == Tileset.WALL && t[mX][mY + 1] == Tileset.WALL)
                || (t[mX][mY] == Tileset.NOTHING && t[mX - 1][mY] == Tileset.FLOOR)
                || (t[mX][mY] == Tileset.NOTHING && t[mX + 1][mY] == Tileset.FLOOR)) {
            t[mX][mY] = Tileset.FLOOR;
            t[mX][mY+1] = Tileset.WALL;
            t[mX][mY-1] = Tileset.WALL;
            moveRecord += 'l';
            Point src2 = new Point(mX, mY);
            make1.add(src2);
        }

        ter.renderFrame(t);
        drawHeader(click, String.valueOf(coinCollect), String.valueOf(HD));
    }

    private boolean checkIfMouseChanged(double x, double y) {
        if (x != mouseX || y != mouseY) {
            mouseX = x;
            mouseY = y;
            return true;
        } else {
            return false;
        }
    }

    //////////////////////////////////////////////
    //////////////////* GameOver *////////////////
    //////////////////////////////////////////////

    public void gameOver() {
        if (success) {
            Utils.writeContents(MOVE, moveRecord);
            Utils.writeContents(MOVEF, moveRecordForest);
            Utils.writeContents(SEED, String.valueOf(seed));
            Utils.writeObject(CLICK, lightList);
            Utils.writeObject(MAKE, make1);
            Utils.writeContents(FORESTX, String.valueOf(aX));
            Utils.writeContents(FORESTY, String.valueOf(aY));
            drawSuccess();
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char target = StdDraw.nextKeyTyped();
                    if (target == 'R' || target == 'r') {
                        menu();
                    } else if (target == 'Q' || target == 'q') {
                        drawFrame("Congrats again! See you next time!");
                        StdDraw.pause(1000);
                        StdDraw.pause(1000);
                        StdDraw.clear();
                    }
                }
            }
        } else {
            Utils.writeContents(MOVE, moveRecord);
            Utils.writeContents(MOVEF, moveRecordForest);
            Utils.writeContents(SEED, String.valueOf(seed));
            Utils.writeObject(CLICK, lightList);
            Utils.writeObject(MAKE, make1);
            Utils.writeContents(FORESTX, String.valueOf(aX));
            Utils.writeContents(FORESTY, String.valueOf(aY));
            drawFail();
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char target = StdDraw.nextKeyTyped();
                    if (target == 'R' || target == 'r') {
                        menu();
                    } else if (target == 'Q' || target == 'q') {
                        drawFrame("Pity! See you next time!");
                        StdDraw.pause(1000);
                        StdDraw.pause(1000);
                        StdDraw.clear();
                    }
                }
            }
        }
    }


    //////////////////////////////////////////////
    //////////////////* Movement *////////////////
    //////////////////////////////////////////////

    public void move(TETile[][] t, boolean forestMove) {
        if (forestMove) {
            AVATAR_X = aX;
            AVATAR_Y = aY;
        }
        Date start = new Date();
        while (true) {
            mouseX = (int) Math.floor(StdDraw.mouseX());
            mouseY = (int) Math.floor(StdDraw.mouseY());
            if (checkIfMouseChanged(Math.floor(StdDraw.mouseX()), Math.floor(StdDraw.mouseY()))) {
                if (mouseX < 0 || mouseX >= WIDTH || mouseY < 0 || mouseY >= HEIGHT) {
                    continue;
                }
                mouseX = (int) Math.floor(StdDraw.mouseX());
                mouseY = (int) Math.floor(StdDraw.mouseY());
                TETile m = finalWorldFrame[(int) mouseX][(int) mouseY];
                click = m.description();
                ter.renderFrame(t);
                drawHeader(click, String.valueOf(coinCollect), String.valueOf(HD));
            }
            if (forestMove) {
                long seconds = timeCounter(t, start);
                if (seconds >= 10 || coinCollect >= 10) {
                    Utils.writeContents(MOVEF, moveRecordForest);
                    drawFrame("10 Sec! Times Up! \nLet's get back to the dungeon!");
                    StdDraw.pause(1500);
                    break;
                }
            }

            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                int newX = AVATAR_X;
                int newY = AVATAR_Y;
                if (target == 'w' || target == 'W') {
                    if (moveLimit(t, AVATAR_X, AVATAR_Y, 0, 1)) {
                        continue;
                    }
                    // successful case
                    if (t[AVATAR_X][AVATAR_Y + 1] == Tileset.UNLOCKED_DOOR) {
                        given = true;
                        success = true;
                        break;
                    }
                    newX = AVATAR_X;
                    newY = AVATAR_Y + 1;
                    nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, 0, 1);
                } else if (target == 's' || target == 'S') {
                    if (moveLimit(t, AVATAR_X, AVATAR_Y, 0, -1)) {
                        continue;
                    }
                    // successful case
                    if (t[AVATAR_X][AVATAR_Y - 1] == Tileset.UNLOCKED_DOOR) {
                        given = true;
                        success = true;
                        break;
                    }
                    newX = AVATAR_X;
                    newY = AVATAR_Y - 1;
                    nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, 0, -1);
                } else if (target == 'a' || target == 'A') {
                    if (moveLimit(t, AVATAR_X, AVATAR_Y, -1, 0)) {
                        continue;
                    }
                    // successful case
                    if (t[AVATAR_X - 1][AVATAR_Y] == Tileset.UNLOCKED_DOOR) {
                        given = true;
                        success = true;
                        break;
                    }
                    newX = AVATAR_X - 1;
                    newY = AVATAR_Y;
                    nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, -1, 0);
                } else if (target == 'd' || target == 'D') {
                    if (moveLimit(t, AVATAR_X, AVATAR_Y, 1, 0)) {
                        continue;
                    }
                    // successful case
                    if (t[AVATAR_X + 1][AVATAR_Y] == Tileset.UNLOCKED_DOOR) {
                        given = true;
                        success = true;
                        break;
                    }
                    newX = AVATAR_X + 1;
                    newY = AVATAR_Y;
                    nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, 1, 0);

                } else if (target == ':') {
                    if (colonQuit(t, "No such operation - Press <WASD> to continue game - Press<:q> to quit")) {
                        given = false;
                        break;
                    }
                    continue;
                } else {
                    continue;
                }
                if (forestMove) {
                    moveRecordForest = moveRecordForest + target;
                } else {
                    moveRecord = moveRecord + target;
                }
                HD--;
                if (HD < 0) {
                    given = true;
                    success = false;
                    break;
                }
                AVATAR_X = newX;
                AVATAR_Y = newY;
                if (coinCollect >= 10) {
                    t[DOOR_X][DOOR_Y] = Tileset.UNLOCKED_DOOR;
                }
                ter.renderFrame(t);
                drawHeader(click, String.valueOf(coinCollect), String.valueOf(HD));
                click = "";
            }

            if (StdDraw.isMousePressed()) {
                if (mouseX < 0 || mouseX >= WIDTH || mouseY < 0 || mouseY >= HEIGHT) {
                    continue;
                }
                trackPressing(t, (int) Math.floor(mouseX), (int) Math.floor(mouseY));
            }

        }
        if (given) {
            gameOver();
        }
    }

    public void moveRecord(TETile[][] t, String s, boolean flag, boolean step, boolean forestMove) {
        if (!forestMove) {
            HD = 200;
            coinCollect = 0;
            moveRecord = "";
        }
        if (!forestMove) {
            if (!step) {
                record = true;
            }
            if (step) {
                repeat = true;
            }
        } else {
            AVATAR_X = aX;
            AVATAR_Y = aY;
        }
        for (int i = 0; i < s.length(); i++) {
            char target = s.charAt(i);
            int newX = AVATAR_X;
            int newY = AVATAR_Y;
            if (target == 'w') {
                if (moveLimit(t, AVATAR_X, AVATAR_Y, 0, 1)) {
                    continue;
                }
                newX = AVATAR_X;
                newY = AVATAR_Y + 1;
                nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, 0, 1);
            } else if (target == 's' || target == 'S') {
                if (moveLimit(t, AVATAR_X, AVATAR_Y, 0, -1)) {
                    continue;
                }
                newX = AVATAR_X;
                newY = AVATAR_Y - 1;
                nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, 0, -1);
            } else if (target == 'a' || target == 'A') {
                if (moveLimit(t, AVATAR_X, AVATAR_Y, -1, 0)) {
                    continue;
                }
                newX = AVATAR_X - 1;
                newY = AVATAR_Y;
                nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, -1, 0);
            } else if (target == 'd' || target == 'D') {
                if (moveLimit(t, AVATAR_X, AVATAR_Y, 1, 0)) {
                    continue;
                }
                newX = AVATAR_X + 1;
                newY = AVATAR_Y;
                nonFloorAreaMove(t, AVATAR_X, AVATAR_Y, 1, 0);

            }

            ///////////////////Fore StringInput////////////////////////

            else if (!flag && target == ':') {
                flag = true;
            } else if (flag && target == 'q' || target == 'Q') {
                Utils.writeContents(MOVE, moveRecord);
                Utils.writeContents(SEED, String.valueOf(seed));
                return;
            }
            //////////////////////////////////////////////////////////

            else if (target == 'f' || target == 'F') {
                Point op = lightList1.remove(0);
                closeLight((int) op.getX(), (int) op.getY());
                moveRecord = moveRecord + target;
                continue;
            } else if (target == 'o' || target == 'O') {
                Point op = lightList1.remove(0);
                openLight((int) op.getX(), (int) op.getY());
                moveRecord = moveRecord + target;
                continue;
            } else if (target == 't' || target == 'T') {
                Point op = make2.remove(0);
                t[(int)op.getX()][(int)op.getY()] = Tileset.FLOOR;
                t[(int)op.getX()+1][(int)op.getY()] = Tileset.WALL;
                t[(int)op.getX()-1][(int)op.getY()] = Tileset.WALL;
                moveRecord = moveRecord + target;
                continue;
            } else if (target == 'l' || target == 'L') {
                Point op =  make2.remove(0);
                t[(int)op.getX()][(int) op.getY()] = Tileset.FLOOR;
                t[(int)op.getX()][(int) op.getY()+1] = Tileset.WALL;
                t[(int)op.getX()][(int) op.getY()-1] = Tileset.WALL;
                moveRecord = moveRecord + target;
                continue;
            }
            else {
                continue;
            }
            AVATAR_X = newX;
            AVATAR_Y = newY;
            if (forestMove) {
                moveRecordForest = moveRecordForest + target;
            } else {
                moveRecord = moveRecord + target;
            }
            HD--;
            if (HD < 0) {
                drawFail();
            }
            if (coinCollect >= 10) {
                t[DOOR_X][DOOR_Y] = Tileset.UNLOCKED_DOOR;
            }
            if (step) {
                display(t, "Repeating");
            }
        }
        if (!forestMove) {
            if (!step) {
                display(t, "Load Completed");
                record = false;
            }
            if (step) {
                display(t, "Repeated Completed");
                while (true) {
                    if (StdDraw.hasNextKeyTyped()) {
                        char target = StdDraw.nextKeyTyped();
                        if (target == ':') {
                            if (colonQuit(t, "No such operation - Press<:q> to menu")) {
                                break;
                            }
                        }
                    }
                }
                repeat = false;
            }
        }
    }

    /////////////////////////////////
    ///////////* Light */////////////
    /////////////////////////////////
    public void openLight(int x, int y) {
        boolean l1 = false;
        boolean l2 = false;


        if (finalWorldFrame[x][y] == Tileset.AVATAR
                || finalWorldFrame[x + 1][y] == Tileset.AVATAR
                || finalWorldFrame[x - 1][y] == Tileset.AVATAR
                || finalWorldFrame[x][y + 1] == Tileset.AVATAR
                || finalWorldFrame[x][y - 1] == Tileset.AVATAR
                || finalWorldFrame[x + 1][y + 1] == Tileset.AVATAR
                || finalWorldFrame[x - 1][y - 1] == Tileset.AVATAR
                || finalWorldFrame[x + 1][y - 1] == Tileset.AVATAR
                || finalWorldFrame[x - 1][y + 1] == Tileset.AVATAR) {
            l1 = true;
        }

        finalWorldFrame[x][y] = Tileset.LIGHT;
        finalWorldFrame[x + 1][y] = Tileset.FLOOR_L1;
        finalWorldFrame[x - 1][y] = Tileset.FLOOR_L1;
        finalWorldFrame[x][y + 1] = Tileset.FLOOR_L1;
        finalWorldFrame[x][y - 1] = Tileset.FLOOR_L1;
        finalWorldFrame[x + 1][y + 1] = Tileset.FLOOR_L1;
        finalWorldFrame[x - 1][y - 1] = Tileset.FLOOR_L1;
        finalWorldFrame[x + 1][y - 1] = Tileset.FLOOR_L1;
        finalWorldFrame[x - 1][y + 1] = Tileset.FLOOR_L1;

        if (finalWorldFrame[x + 2][y] == Tileset.AVATAR
                || finalWorldFrame[x + 2][y + 1] == Tileset.AVATAR
                || finalWorldFrame[x + 2][y + 2] == Tileset.AVATAR
                || finalWorldFrame[x - 2][y] == Tileset.AVATAR
                || finalWorldFrame[x - 2][y - 1] == Tileset.AVATAR
                || finalWorldFrame[x + 2][y - 1] == Tileset.AVATAR
                || finalWorldFrame[x - 2][y + 1] == Tileset.AVATAR
                || finalWorldFrame[x - 2][y + 2] == Tileset.AVATAR
                || finalWorldFrame[x][y + 2] == Tileset.AVATAR
                || finalWorldFrame[x - 1][y + 2] == Tileset.AVATAR
                || finalWorldFrame[x + 1][y + 2] == Tileset.AVATAR
                || finalWorldFrame[x][y - 2] == Tileset.AVATAR
                || finalWorldFrame[x - 1][y - 2] == Tileset.AVATAR
                || finalWorldFrame[x + 1][y - 2] == Tileset.AVATAR
                || finalWorldFrame[x - 2][y - 2] == Tileset.AVATAR
                || finalWorldFrame[x + 2][y - 2] == Tileset.AVATAR) {
            l2 = true;
        }

        finalWorldFrame[x + 2][y] = Tileset.FLOOR_L2;
        finalWorldFrame[x + 2][y + 1] = Tileset.FLOOR_L2;
        finalWorldFrame[x + 2][y + 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x - 2][y] = Tileset.FLOOR_L2;
        finalWorldFrame[x - 2][y - 1] = Tileset.FLOOR_L2;
        finalWorldFrame[x + 2][y - 1] = Tileset.FLOOR_L2;
        finalWorldFrame[x - 2][y + 1] = Tileset.FLOOR_L2;
        finalWorldFrame[x - 2][y + 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x][y + 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x - 1][y + 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x + 1][y + 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x][y - 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x - 1][y - 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x + 1][y - 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x - 2][y - 2] = Tileset.FLOOR_L2;
        finalWorldFrame[x + 2][y - 2] = Tileset.FLOOR_L2;

        if (l1) {
            finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.AVATAR_L1;
        } else if (l2) {
            finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.AVATAR_L2;
        }
    }

    public void closeLight(int x, int y) {
        boolean l1 = avatarDetect(x, y);

        finalWorldFrame[x][y] = Tileset.N_LIGHT;
        finalWorldFrame[x + 1][y] = Tileset.FLOOR;
        finalWorldFrame[x - 1][y] = Tileset.FLOOR;
        finalWorldFrame[x][y + 1] = Tileset.FLOOR;
        finalWorldFrame[x][y - 1] = Tileset.FLOOR;
        finalWorldFrame[x + 1][y + 1] = Tileset.FLOOR;
        finalWorldFrame[x - 1][y - 1] = Tileset.FLOOR;
        finalWorldFrame[x + 1][y - 1] = Tileset.FLOOR;
        finalWorldFrame[x - 1][y + 1] = Tileset.FLOOR;

        finalWorldFrame[x + 2][y] = Tileset.FLOOR;
        finalWorldFrame[x + 2][y + 1] = Tileset.FLOOR;
        finalWorldFrame[x + 2][y + 2] = Tileset.FLOOR;
        finalWorldFrame[x - 2][y] = Tileset.FLOOR;
        finalWorldFrame[x - 2][y - 1] = Tileset.FLOOR;
        finalWorldFrame[x + 2][y - 1] = Tileset.FLOOR;
        finalWorldFrame[x - 2][y + 1] = Tileset.FLOOR;
        finalWorldFrame[x - 2][y + 2] = Tileset.FLOOR;
        finalWorldFrame[x][y + 2] = Tileset.FLOOR;
        finalWorldFrame[x - 1][y + 2] = Tileset.FLOOR;
        finalWorldFrame[x + 1][y + 2] = Tileset.FLOOR;
        finalWorldFrame[x][y - 2] = Tileset.FLOOR;
        finalWorldFrame[x - 1][y - 2] = Tileset.FLOOR;
        finalWorldFrame[x + 1][y - 2] = Tileset.FLOOR;
        finalWorldFrame[x - 2][y - 2] = Tileset.FLOOR;
        finalWorldFrame[x + 2][y - 2] = Tileset.FLOOR;

        if (l1) {
            finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.AVATAR;
        }
    }

    public boolean avatarDetect(int x, int y) {
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if (finalWorldFrame[i][j] == Tileset.AVATAR_L1 || finalWorldFrame[i][j] == Tileset.AVATAR_L2) {
                    return true;
                }
            }
        }
        return false;
    }

    /////////////////////////////////
    ///////////* Forest *////////////
    /////////////////////////////////

    public void forest() {

        TETile[][] newForest = new TETile[WIDTH][HEIGHT];
        WorldGenerator f = new WorldGenerator(newForest, WIDTH, HEIGHT, this.seed);
        f.createForest(newForest);
        while (true) {
            aX = rand.nextInt(100);
            aY = rand.nextInt(60);
            if (newForest[aX][aY] == Tileset.FLOOR) {
                newForest[aX][aY] = Tileset.AVATAR;
                Utils.writeContents(FORESTX, String.valueOf(aX));
                Utils.writeContents(FORESTY, String.valueOf(aY));
                break;
            }
        }
        ter.initialize(WIDTH, HEIGHT);
        drawHeading("Now it is time to Collect the coin! 10 sec!");
        ter.renderFrame(newForest);
        drawHeader(click, String.valueOf(coinCollect), String.valueOf(HD));
        move(newForest, true);
    }

    public void forestRecord() {
        TETile[][] newForest = new TETile[WIDTH][HEIGHT];
        WorldGenerator f = new WorldGenerator(newForest, WIDTH, HEIGHT, this.seed);
        f.createForest(newForest);
        newForest[aX][aY] = Tileset.AVATAR;
        String forestString = Utils.readContentsAsString(MOVEF);
        boolean flag = false;
        moveRecord(newForest, forestString, flag, false, true);
    }

    public void forestRepeat() {
        TETile[][] newForest = new TETile[WIDTH][HEIGHT];
        WorldGenerator f = new WorldGenerator(newForest, WIDTH, HEIGHT, this.seed);
        f.createForest(newForest);
        newForest[aX][aY] = Tileset.AVATAR;
        ter.renderFrame(newForest);
        drawHeader(click, String.valueOf(coinCollect), String.valueOf(HD));
        String forestString = Utils.readContentsAsString(MOVEF);
        boolean flag = false;
        moveRecord(newForest, forestString, flag, true, true);
    }

    public long timeCounter(TETile[][] tiles, Date start) {
        Date end = new Date();
        long diff = end.getTime() - start.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        ter.renderFrame(tiles);
        drawHeader(String.valueOf(seconds), String.valueOf(coinCollect), String.valueOf(HD));
        return seconds;
    }
}
