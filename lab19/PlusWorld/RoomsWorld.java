package PlusWorld;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

public class RoomsWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;


    public static void createRooms(TETile[][] tiles) {
        Rooms rooms = new Rooms(tiles);
        rooms.drawBlock();
    }
    public static void createDistinctRooms(TETile[][] tiles) {
        DistinctRooms distinctRooms = new DistinctRooms(tiles);
        distinctRooms.drawBlock();
    }

    public static void fillBlack(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if(x == 0 || y == 0 || x == WIDTH -1 || y == HEIGHT - 1){
                    tiles[x][y] = Tileset.TREE;
                    continue;
                }
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }



    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        fillBlack(randomTiles);
        int num = 50;
//        for(int i = 0; i < num; i ++){
//            createRooms(randomTiles);
//            ter.renderFrame(randomTiles);
//            // StdDraw.pause(600);
//        }
        for(int i = 0; i < num; i ++){
            createDistinctRooms(randomTiles);
            ter.renderFrame(randomTiles);
        }
    }

}
