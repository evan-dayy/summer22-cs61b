package PlusWorld;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

public class Cross {
    private int size;
    private int startW;
    private int startH;
    private TETile pattern;

    public Cross(int size, int startW, int startH, TETile pattern){
        this.size = size;
        this.startW = startW;
        this.startH = startH;
        this.pattern = pattern;
    }

    public void draw(TETile[][] tiles){
        drawBlock(startW, startH, tiles, pattern);
        drawBlock(startW, startH + size, tiles, pattern);
        drawBlock(startW, startH + 2 * size, tiles, pattern);
        drawBlock(startW - size, startH + size, tiles, pattern);
        drawBlock(startW + size, startH + size, tiles, pattern);
    }

    public void drawBlock(int bW, int bH, TETile[][] tiles, TETile pattern){
        int height = tiles[0].length;
        int width = tiles.length;
        for(int i = bW; i < bW + size; i ++){
            for(int j = bH; j < bH + size; j ++){
                if(i < 0 || j < 0 || i > width || j > height){
                    continue;
                }
                tiles[i][j] = pattern;
            }
        }
    }


}
