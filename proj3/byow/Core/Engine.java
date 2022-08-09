package byow.Core;

import byow.TileEngine.TETile;

import java.io.File;

public class Engine {
    /* Feel free to change the width and height. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File MOVE = Utils.join(CWD, "move.txt");
    public static final File SEED = Utils.join(CWD, "seed.txt");
    public static final int WIDTH = 100;
    public static final int HEIGHT = 60;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        User game = new User();
        game.menu();
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        String num = "";
        int s = 0;
        String inputMovement = "";
        if (input.charAt(0) == 'n' || input.charAt(0) == 'N') {
            for (int i = 1; i < input.length(); i++) {
                if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                    s = i;
                    break;
                }
                num = num + input.charAt(i);
            }
            for (int i = s + 1; i < input.length(); i++) {
                inputMovement += input.charAt(i);
            }
            long seed = Long.parseLong(num);
            User game = new User();
            game.setSeed(seed);
            TETile[][] t = game.initialTile();
            boolean flag = false;
            game.moveRecord(t, inputMovement, flag, false, false);
            return t;


        } else if (input.charAt(0) == 'l' || input.charAt(0) == 'L') {
            long rt = Long.parseLong(Utils.readContentsAsString(SEED));
            String movement = Utils.readContentsAsString(MOVE);
            for (int i = 1; i < input.length(); i++) {
                inputMovement += input.charAt(i);
            }
            User game = new User();
            game.setSeed(rt);
            TETile[][] t = game.initialTile();
            boolean flag = false;
            game.moveRecord(t, movement, flag, false, false);
            game.moveRecord(t, inputMovement, flag, false, false);
            return t;

        } else if (input.charAt(0) == 'Q' || input.charAt(0) == 'q') {
            return null;
        } else if (input.charAt(0) == 'r' || input.charAt(0) == 'R') {
            long rt = Long.parseLong(Utils.readContentsAsString(SEED));
            String movement = Utils.readContentsAsString(MOVE);
            User game = new User();
            game.setSeed(rt);
            TETile[][] t = game.initialTile();
            boolean flag = false;
            game.moveRecord(t, movement, flag, true, false);
            return t;
        }
        return null;
    }
}


