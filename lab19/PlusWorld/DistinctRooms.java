package PlusWorld;
import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;
import java.util.LinkedList;

import java.util.Random;

public class DistinctRooms {

    private int sizeW;
    private int sizeH;
    private int startW;
    private int startH;
    private String roomID;
    private TETile[][] tile;
    private LinkedList<DistinctRooms> connectedRoom;
    private final TETile wall = Tileset.WALL;
    private final TETile floor = Tileset.FLOOR;
    private static final long SEED = 2353;
    private static final Random RANDOM = new Random(SEED);


    public DistinctRooms(TETile[][] tile){
        this.tile = tile;
        int height = tile[0].length - 1;
        int width = tile.length -1 ;
        this.sizeW = randomWidth();
        this.sizeH = randomHeight();
        this.startW = randomStartW(width - sizeW);
        this.startH = randomStartH(height - sizeH);
        this.roomID = String.valueOf(startW) + "," + String.valueOf(startH);
        this.connectedRoom = new LinkedList<DistinctRooms>();
    }

    public boolean areaCheck(){
        for(int i = startW-2; i < startW + sizeW + 2; i ++){
            for(int j = startH-2; j < startH + sizeH + 2; j ++){
                if(i < 0 || j < 0 || i > tile.length -1 || j > tile[0].length - 1){
                    continue;
                }
                if(tile[i][j] != Tileset.NOTHING){
                    return false;
                }
            }
        }
        return true;
    }

    public void drawBlock(){
        if(!areaCheck()){
            return;
        }
        for(int i = startW; i < startW + sizeW; i ++){
            for(int j = startH; j < startH + sizeH; j ++){
                    tile[i][j] = floor;

            }
        }
    }


    ////////////////////////////////////////
    // ******* Random Parameters *********//
    ////////////////////////////////////////


    private static int randomWidth() {
        return RANDOM.nextInt(6) + 10;
    }

    private static int randomHeight() {
        return RANDOM.nextInt(5) + 10;
    }


    private static int randomStartW(int bound) {
        return RANDOM.nextInt(bound) + 1;
    }

    private static int randomStartH(int bound) {
        return RANDOM.nextInt(bound) + 1;
    }


    ////////////////////////////////////////
    // ******** Class Attributes *********//
    ////////////////////////////////////////


    public int getSizeW(){
        return sizeW;
    }

    public int getSizeH(){
        return sizeH;
    }

    public int roomCenterWidth(){
        return startW + sizeW / 2;
    }

    public int roomCenterHeight(){
        return startH + sizeH / 2;
    }



    ////////////////////////////////////////
    // ******** Hallway Connect  *********//
    ////////////////////////////////////////






}
