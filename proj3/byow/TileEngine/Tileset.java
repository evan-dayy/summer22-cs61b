package byow.TileEngine;

import java.awt.*;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 * <p>
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 * <p>
 * Ex:
 * world[x][y] = Tileset.FLOOR;
 * <p>
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you!");
    public static final TETile WALL = new TETile(' ', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(216, 128, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "Nothing here!");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.red, Color.red,
            "Locked door! You need to get 10 coins to open :)");
    public static final TETile UNLOCKED_DOOR = new TETile('█', Color.green, Color.green,
            "Open Door! Now can pass :)");

    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile LIGHT = new TETile('▲', new Color(249, 255, 250), new Color(57, 76, 238), "Light Source!");
    public static final TETile FLOOR_L1 = new TETile('·', new Color(128, 192, 128), new Color(85, 100, 222), "Shining Floor!");
    public static final TETile AVATAR_L1 = new TETile('@', Color.white, new Color(85, 100, 222), "Shining you");
    public static final TETile FLOOR_L2 = new TETile('·', new Color(128, 192, 128), new Color(87, 95, 145), "Shining Floor!");
    public static final TETile AVATAR_L2 = new TETile('@', Color.white, new Color(87, 95, 145), "Shining you");
    public static final TETile N_LIGHT = new TETile('▲', new Color(119, 59, 5), new Color(3, 3, 3), "Click to open light");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "Magical Forest!");
    public static final TETile COIN = new TETile('©', Color.yellow, Color.black, "Gain 1 coin!");
    public static final TETile ENERGY = new TETile('œ', Color.blue, Color.black, "Gain 10 Energy!");

}


