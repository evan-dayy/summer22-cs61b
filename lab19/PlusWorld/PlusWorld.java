package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void fillWithTilesCross(TETile[][] tiles, int size, int posW, int posH) {
        TETile pattern = randomTileCross();
        Cross cross = new Cross(size, posW, posH, pattern);
        cross.draw(tiles);
    }


    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                randomTiles[x][y] = Tileset.NOTHING;
            }
        }
        int size = 1;
        for(int i = 0; i < WIDTH; i += size * 3){
            for(int j = 0; j < HEIGHT; j += size * 3){
                fillWithTilesCross(randomTiles,size, i, j);
                ter.renderFrame(randomTiles);
            }
        }

    }


    private static TETile randomTileCross() {
        int tileNum = RANDOM.nextInt(4);
        return switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.SAND;
            case 2 -> Tileset.WATER;
            case 3 -> Tileset.AVATAR;
            case 4 -> Tileset.GRASS;
            case 5 -> Tileset.UNLOCKED_DOOR;
            default -> Tileset.NOTHING;
        };
    }

}
