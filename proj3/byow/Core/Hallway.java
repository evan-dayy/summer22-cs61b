package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hallway {
    private final int width = 3;
    private final int startW;
    private final int startH;
    private final TETile[][] tile;

    public Hallway(TETile[][] tile, int startW, int startH) {
        this.tile = tile;
        this.startH = startH;
        this.startW = startW;
    }

    public void drawStraightVertical() {
        for (int i = startW; i <= startW + width - 1; i++) {
            for (int j = startH; j != tile[0].length - 1; j++) {
                if (j >= tile[0].length - 5) {
                    tile[i][j] = Tileset.WALL;
                    tile[i+1][j] = Tileset.WALL;
                    break;
                }

                if ((i == startW || i == startW + width - 1) && j == startH) {
                    if (tile[i][j] == Tileset.WALL) {
                        continue;
                    }
                }

                if (i == startW || i == startW + width - 1) {
                    if (tile[i][j] != Tileset.NOTHING) {
                        break;
                    }
                    tile[i][j] = Tileset.WALL;
                } else {
                    if (tile[i][j] == Tileset.WALL) {
                        tile[i][j] = Tileset.FLOOR;
                        if (tile[i - 1][j] == Tileset.NOTHING) {
                            tile[i - 1][j] = Tileset.WALL;
                        }

                        if (tile[i + 1][j] == Tileset.NOTHING) {
                            tile[i + 1][j] = Tileset.WALL;
                        }
                        continue;
                    } else if (tile[i][j] == Tileset.FLOOR) {
                        break;
                    }

                    tile[i][j] = Tileset.FLOOR;

                    if (tile[i - 1][j] == Tileset.NOTHING) {
                        tile[i - 1][j] = Tileset.WALL;
                    }

                    if (tile[i + 1][j] == Tileset.NOTHING) {
                        tile[i + 1][j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void drawStraightVerticalDown() {
        for (int i = startW; i <= startW + width - 1; i++) {
            for (int j = startH; j >= 0; j--) { // startH should be the above one
                if ((i == startW || i == startW + width - 1) && j == startH) {
                    if (tile[i][j] == Tileset.WALL) {
                        continue;
                    }
                }

                if (i == startW || i == startW + width - 1) {
                    if (tile[i][j] != Tileset.NOTHING) {
                        break;
                    }
                    tile[i][j] = Tileset.WALL;
                } else {
                    if (tile[i][j] == Tileset.WALL) {
                        tile[i][j] = Tileset.FLOOR;
                        if (tile[i - 1][j] == Tileset.NOTHING) {
                            tile[i - 1][j] = Tileset.WALL;
                        }
                        if (tile[i + 1][j] == Tileset.NOTHING) {
                            tile[i + 1][j] = Tileset.WALL;
                        }
                        continue;
                    } else if (tile[i][j] == Tileset.FLOOR) {
                        break;
                    }
                    tile[i][j] = Tileset.FLOOR;
                    if (tile[i - 1][j] == Tileset.NOTHING) {
                        tile[i - 1][j] = Tileset.WALL;
                        tile[i + 1][j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void drawStraightHorizontal() {
        for (int j = startH; j <= startH + width - 1; j++) {
            for (int i = startW; i <= tile.length - 1; i++) {
                if ((j == startH || j == startH + width - 1) && i == startW) {
                    if (tile[i][j] == Tileset.WALL) {
                        continue;
                    }
                }

                if (j == startH || j == startH + width - 1) {
                    if (tile[i][j] != Tileset.NOTHING) {
                        break;
                    }
                    tile[i][j] = Tileset.WALL;

                } else {
                    if (tile[i][j] == Tileset.WALL) {
                        tile[i][j] = Tileset.FLOOR;
                        if (tile[i][j + 1] == Tileset.NOTHING) {
                            tile[i][j + 1] = Tileset.WALL;
                        }
                        if (tile[i][j - 1] == Tileset.NOTHING) {
                            tile[i][j - 1] = Tileset.WALL;
                        }
                        continue;
                    } else if (tile[i][j] == Tileset.FLOOR && tile[i + 1][j] == Tileset.FLOOR) {
                        break;
                    }
                    tile[i][j] = Tileset.FLOOR;
                    if (tile[i][j + 1] == Tileset.NOTHING) {
                        tile[i][j + 1] = Tileset.WALL;
                    }
                    if (tile[i][j - 1] == Tileset.NOTHING) {
                        tile[i][j - 1] = Tileset.WALL;
                    }

                }
            }
        }
    }

    public void drawStraightHorizontalLeft() {
        for (int j = startH; j <= startH + width - 1; j++) {
            for (int i = startW; i >= 0; i--) { // startW should be rooms.left
                if ((j == startH || j == startH + width - 1) && i == startW) {
                    if (tile[i][j] == Tileset.WALL) {
                        continue;
                    }
                }

                if (j == startH || j == startH + width - 1) {
                    if (tile[i][j] != Tileset.NOTHING) {
                        break;
                    }
                    tile[i][j] = Tileset.WALL;

                } else {
                    if (tile[i][j] == Tileset.WALL) {
                        tile[i][j] = Tileset.FLOOR;
                        if (tile[i][j + 1] == Tileset.NOTHING) {
                            tile[i][j + 1] = Tileset.WALL;
                        }
                        if (tile[i][j - 1] == Tileset.NOTHING) {
                            tile[i][j - 1] = Tileset.WALL;
                        }
                        continue;
                    } else if (tile[i][j] == Tileset.FLOOR && tile[i - 1][j] == Tileset.FLOOR) {
                        break;
                    }
                    tile[i][j] = Tileset.FLOOR;
                    if (tile[i][j + 1] == Tileset.NOTHING) {
                        tile[i][j + 1] = Tileset.WALL;
                    }
                    if (tile[i][j - 1] == Tileset.NOTHING) {
                        tile[i][j - 1] = Tileset.WALL;
                    }

                }
            }
        }
    }
}
