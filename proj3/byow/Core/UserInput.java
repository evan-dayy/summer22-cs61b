package byow.Core;

// import byow.TileEngine.TERenderer;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.Random;

public class UserInput implements Serializable {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File MOVE = Utils.join(CWD, "move.txt");
    public static final File SEED = Utils.join(CWD, "seed.txt");
    public static final int WIDTHFRAME = 40;

    public static final int HEIGHTFRAME = 60;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 60;
    private final TERenderer ter = new TERenderer();
    private int AVATAR_X;
    private int AVATAR_Y;
    private int round;
    private long seed;
    private String moveRecord = "";
    private TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;

    public UserInput() {
        StdDraw.setCanvasSize(WIDTHFRAME * 16, HEIGHTFRAME * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTHFRAME);
        StdDraw.setYscale(0, HEIGHTFRAME);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public UserInput(long seed) {
        this.seed = seed;
        this.rand = new Random(seed);
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.rand = new Random(seed);
    }

    public void setTile(TETile[][] a) {
        this.finalWorldFrame = a;
    }

    public void setMoveRecord(String a) {
        this.moveRecord = a;
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


    //////////////////////////////////////////////
    ////////////////////* Menu *//////////////////
    //////////////////////////////////////////////

    public void drawMenu(String s, String s1, String s2, String s3) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 3.0 * HEIGHTFRAME / 4, s);
        Font fontSmall = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 2.2 * HEIGHTFRAME / 4, s1);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 2.1 * HEIGHTFRAME / 4, s2);
        StdDraw.text(1.0 * WIDTHFRAME / 2, 2.0 * HEIGHTFRAME / 4, s3);
        StdDraw.show();
    }


    public void menu() {
        drawMenu("CS61B: THE DUNGEON",
                "New Game (N)",
                "Load Game (L)",
                "Quit (Q)");
        menuInput();
    }

    public void menuInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                if (target == 'N' || target == 'n') {
                    Utils.writeContents(MOVE, "");
                    Utils.writeContents(SEED, "");
                    drawFrame("Please enter a SEED: ");
                    long rt = seedInput();
                    setSeed(rt);
                    drawFrame("Game Start in ...");
                    StdDraw.pause(500);
                    drawFrame("3");
                    StdDraw.pause(1000);
                    drawFrame("2");
                    StdDraw.pause(1000);
                    drawFrame("1");
                    StdDraw.pause(1000);
                    drawFrame("0");
                    StdDraw.pause(200);
                    setTile(initialTile());
                    findUser();
                    break;
                } else if (target == 'L' || target == 'l') {
                    long rt = Long.parseLong(Utils.readContentsAsString(SEED));
                    String movement = Utils.readContentsAsString(MOVE);
                    setSeed(rt);
                    drawFrame("Game Loading in ...");
                    StdDraw.pause(500);
                    drawFrame("3");
                    StdDraw.pause(1000);
                    drawFrame("2");
                    StdDraw.pause(1000);
                    drawFrame("1");
                    StdDraw.pause(1000);
                    drawFrame("0");
                    StdDraw.pause(200);
                    setTile(initialTile());
                    findUser();
                    boolean flag = false;
                    moveRecord(movement, flag);
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
        /////////////////////////////////
        ///////////* DRAW *//////////////
        /////////////////////////////////
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    //////////////////////////////////////////////
    //////////////////* Movement *////////////////
    //////////////////////////////////////////////

    public void move() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                int newX;
                int newY;
                if (target == 'w') {
                    if (AVATAR_Y + 1 >= HEIGHT - 1) {
                        continue;
                    }
                    newX = AVATAR_X;
                    newY = AVATAR_Y + 1;
                    finalWorldFrame[AVATAR_X][AVATAR_Y + 1] = Tileset.AVATAR;
                    finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                } else if (target == 's') {
                    if (AVATAR_Y - 1 < 1) {
                        continue;
                    }
                    newX = AVATAR_X;
                    newY = AVATAR_Y - 1;
                    finalWorldFrame[AVATAR_X][AVATAR_Y - 1] = Tileset.AVATAR;
                    finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                } else if (target == 'a') {
                    if (AVATAR_X - 1 < 1) {
                        continue;
                    }
                    newX = AVATAR_X - 1;
                    newY = AVATAR_Y;
                    finalWorldFrame[AVATAR_X - 1][AVATAR_Y] = Tileset.AVATAR;
                    finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                } else if (target == 'd') {
                    if (AVATAR_X + 1 >= WIDTH - 1) {
                        continue;
                    }
                    newX = AVATAR_X + 1;
                    newY = AVATAR_Y;
                    finalWorldFrame[AVATAR_X + 1][AVATAR_Y] = Tileset.AVATAR;
                    finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                } else if (target == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            // I want it to be show in the left conor
                            char spec = StdDraw.nextKeyTyped();
                            if (spec == 'q' || spec == 'Q') {
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                    Utils.writeContents(MOVE, moveRecord);
                    Utils.writeContents(SEED, String.valueOf(seed));
                    break;
                } else {
                    continue;
                }
                moveRecord = moveRecord + target;
                AVATAR_X = newX;
                AVATAR_Y = newY;
                ter.renderFrame(finalWorldFrame);
            }
        }
    }

    public void moveRecord(String s, boolean flag) {

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            int newX;
            int newY;
            if (!flag && curr == 'w' || curr == 'W') {
                if (AVATAR_Y + 1 >= HEIGHT - 1) {
                    continue;
                }
                newX = AVATAR_X;
                newY = AVATAR_Y + 1;
                finalWorldFrame[AVATAR_X][AVATAR_Y + 1] = Tileset.AVATAR;
                finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                AVATAR_X = newX;
                AVATAR_Y = newY;
                moveRecord += curr;
            } else if (!flag && curr == 's' || curr == 'S') {
                if (AVATAR_Y - 1 < 1) {
                    continue;
                }
                newX = AVATAR_X;
                newY = AVATAR_Y - 1;
                finalWorldFrame[AVATAR_X][AVATAR_Y - 1] = Tileset.AVATAR;
                finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                AVATAR_X = newX;
                AVATAR_Y = newY;
                moveRecord += curr;
            } else if (!flag && curr == 'D' || curr == 'd') {
                if (AVATAR_X + 1 >= WIDTH - 1) {
                    continue;
                }
                newX = AVATAR_X + 1;
                newY = AVATAR_Y;
                finalWorldFrame[AVATAR_X + 1][AVATAR_Y] = Tileset.AVATAR;
                finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                AVATAR_X = newX;
                AVATAR_Y = newY;
                moveRecord += curr;
            } else if (!flag && curr == 'a' || curr == 'A') {
                if (AVATAR_X - 1 < 1) {
                    continue;
                }
                newX = AVATAR_X - 1;
                newY = AVATAR_Y;
                finalWorldFrame[AVATAR_X - 1][AVATAR_Y] = Tileset.AVATAR;
                finalWorldFrame[AVATAR_X][AVATAR_Y] = Tileset.FLOOR;
                AVATAR_X = newX;
                AVATAR_Y = newY;
                moveRecord += curr;
            } else if (!flag && curr == ':') {
                flag = true;
            } else if (flag && curr == 'q' || curr == 'Q') {
                Utils.writeContents(MOVE, moveRecord);
                Utils.writeContents(SEED, String.valueOf(seed));
                return;
            } else {
                continue;
            }
            ter.renderFrame(finalWorldFrame);
        }
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
}