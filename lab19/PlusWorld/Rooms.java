package PlusWorld;


import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;



public class Rooms {

    private int sizeW;
    private int sizeH;
    private int startW;
    private int startH;
    private TETile[][] tile;
    private final TETile wall = Tileset.WALL;
    private final TETile floor = Tileset.FLOOR;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public Rooms(TETile[][] tile){
        this.tile = tile;
        int height = tile[0].length;
        int width = tile.length;
        this.sizeW = randomWidth();
        this.sizeH = randomHeight();
        this.startW =  randomStartW(width - sizeW);
        this.startH =  randomStartH(height - sizeH);
    }

    public void drawBlock(){
        for(int i = startW; i < startW + sizeW; i ++){
            for(int j = startH; j < startH + sizeH; j ++){

                if(i == startW || i == startW + sizeW - 1 || j == startH || j == startH + sizeH - 1){
                    if(tile[i][j] == Tileset.FLOOR){
                        continue;
                    }
                    tile[i][j] = wall;
                }
                else{
                    if(tile[i][j] == Tileset.WALL){
                        tile[i][j] = floor;
                    }
                    tile[i][j] = floor;
                }
            }
        }
    }


    ////////////////////////////////////////
    // ******* Random Parameters *********//
    ////////////////////////////////////////


    private static int randomWidth() {
       return RANDOM.nextInt(10) + 5;
    }

    private static int randomHeight() {
        return RANDOM.nextInt(30) + 5;
    }


    private static int randomStartW(int bound) {
        return RANDOM.nextInt(bound) + 1;
    }

    private static int randomStartH(int bound) {
        return RANDOM.nextInt(bound) + 1;
    }


}
