package game2048;

import java.util.Formatter;
import java.util.Observable;


/**
 * The state of a game of 2048.
 *
 * @author TODO: Evan Day
 */
public class Model extends Observable {
    /**
     * Largest piece value.
     */
    public static final int MAX_PIECE = 2048;
    /**
     * Current contents of the board.
     */
    private final Board _board;
    /**
     * Current score.
     */
    private int _score;
    /**
     * Maximum score so far.  Updated when game ends.
     */
    private int _maxScore;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */
    /**
     * True iff game is ended.
     */
    private boolean _gameOver;

    /**
     * A new 2048 game on a board of size SIZE with no pieces
     * and score 0.
     */
    public Model(int size) {
        // TODO: Fill in this constructor.
        this._board = new Board(size);
    }

    /**
     * A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes.
     */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        // TODO: Fill in this constructor.
        this._board = new Board(rawValues, score);
        this._score = score;
        this._gameOver = gameOver;
        this._maxScore = maxScore;
    }

    /**
     * Determine whether game is over.
     */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /**
     * Returns true if at least one space on the Board is empty.
     * Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int row = 0; row < b.size(); row++) {
            for (int col = 0; col < b.size(); col++) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int row = 0; row < b.size(); row++) {
            for (int col = 0; col < b.size(); col++) {
                if (b.tile(row, col) != null && b.tile(row, col).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        for (int row = 0; row < b.size(); row++) {
            for (int col = 0; col < b.size(); col++) {
                if (b.tile(col, row) == null) {
                    return true;
                } else if (col != b.size() - 1 && row != b.size() - 1 && b.tile(col + 1, row) != null && b.tile(col, row + 1) != null && (b.tile(col, row).value() == b.tile(col + 1, row).value() || b.tile(col, row).value() == b.tile(col, row + 1).value())) {
                    return true;
                } else if (col == b.size() - 1 && row != b.size() - 1 && b.tile(col, row + 1) != null && b.tile(col, row).value() == b.tile(col, row + 1).value()) {
                    return true;
                } else if (col != b.size() - 1 && row == b.size() - 1 && b.tile(col + 1, row) != null && b.tile(col, row).value() == b.tile(col + 1, row).value()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     * 0 <= COL < size(). Returns null if there is no tile there.
     * Used for testing. Should be deprecated and removed.
     */

    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /**
     * Return the number of squares on one side of the board.
     * Used for testing. Should be deprecated and removed.
     */
    public int size() {
        return _board.size();
    }

    /**
     * Return true iff the game is over (there are no moves, or
     * there is a tile with value 2048 on the board).
     */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /**
     * Return the current score.
     */
    public int score() {
        return _score;
    }

    /**
     * Return the current maximum game score (updated at end of game).
     */
    public int maxScore() {
        return _maxScore;
    }

    /**
     * Clear the board to empty and reset the score.
     */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /**
     * Add TILE to the board. There must be no Tile currently at the
     * same position.
     */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /**
     * Tilt the board toward SIDE. Return true iff this changes the board.
     * <p>
     * 1. If two Tile objects are adjacent in the direction of motion and have
     * the same value, they are merged into one Tile of twice the original
     * value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     * tilt. So each move, every tile will only ever be part of at most one
     * merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     * value, then the leading two tiles in the direction of motion merge,
     * and the trailing tile does not.
     */


    public boolean tilt(Side side) {
        boolean changed;
        boolean merge;
        changed = false;
        Board b = this._board;
        int changedNum = 0;
        Side n = Side.NORTH;
        Side s = Side.SOUTH;
        Side e = Side.EAST;
        Side w = Side.WEST;


        // TODO: Fill in this function.
        _board.setViewingPerspective(side); // North.View

        for (int col = 0; col < b.size(); col += 1) {
            int preciousValue = 0;
            int currentValue = 0;
            int nullTile = 0;
            boolean shouldMerged = false;
            int mergedNum = 0;
            int row = b.size() - 1;
            int equalCount = 0;

            while (true) {

                if (isNullCol(b, col)) {
                    break;
                }

                while (row >= 0) {
                    if (!hasNumber(b, col, row)) {
                        nullTile++;
                        break;
                    } else {
                        preciousValue = currentValue;
                        currentValue = b.tile(col, row).value();
                    }
                    if (row == 3) {
                        break;
                    }

                    // Here is a pattern for col start with 0
                    //if(nullTile > 0){
                    Tile t = b.tile(col, row);
                    shouldMerged = preciousValue == currentValue;

                    if ((shouldMerged && equalCount % 2 == 0) || (shouldMerged && equalCount == 0)) {
                        b.move(col, row + nullTile + mergedNum + 1, t);
                        _score += b.tile(col, row + nullTile + 1 + mergedNum).value();
                        changed = true;
                        shouldMerged = false;
                        mergedNum += 1;
                        equalCount += 1;
                        break;

                    }

                    // not merged
                    b.move(col, row + nullTile + mergedNum, t);
                    changed = true;
                    if (equalCount <= 0) {
                        equalCount = 0;
                        break;
                    }
                    equalCount--;
                    break;
                    // }
                    // what about it does not start as 0.


                }
                row--;

                if (changed) {
                    changedNum++;
                } // inner loop end once to check whether there is a change

                if (row + 1 == 0) {
                    break;
                } // end the while loop

            }


        }

        if (changedNum > 0) {
            changed = true;
        }
        _board.setViewingPerspective(n); // North.View
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /* Helpers for tile */
    /* to show how may entry there in a col. */
    private boolean isEvenEqual(Board b, int col) {
        int preciousValue = 0;
        int currentValue = 0;
        if (isNullCol(b, col)) {
            return false;
        }
        for (int row = 0; row < b.size(); row++) {
            if (b.tile(col, row) == null) {
                return false;
            }
        }
        for (int row = 0; row < b.size(); row++) {
            if (row == 0) {
                currentValue = b.tile(col, row).value();
                continue;
            }
            if (row > 0) {
                preciousValue = currentValue;
                currentValue = b.tile(col, row).value();
            }
            if (currentValue != preciousValue) {
                return false;
            }

        }
        return true;
    }

    private boolean hasNumber(Board b, int col, int row) {
        return b.tile(col, row) != null;
    }

    private boolean isNullCol(Board b, int col) {
        int i = 0;
        for (int row = 0; row < b.size(); row++) {
            if (b.tile(col, row) == null) {
                i++;
            }

            if (i == b.size()) {
                return true;
            }
        }
        return false;
    }

    private int entryCheck(Board b, int col) {
        int i = 0;
        for (int row = 0; row < b.size(); row++) {
            if (b.tile(col, row) != null) {
                i = i + 1;
            }
        }
        return i;
    }

    /*To show number previous-null entry */
    private int previousNullNumber(Board b, int col, int row) {
        int i = 0;
        while (row < 3) {
            if (b.tile(col, row + 1) == null) {
                i = i + 1;
            }
            row = row + 1;
        }
        return i;
    }

    /*To show whether the preview entry non-null entry is same as the current */
    private boolean isPreviousNonNullSame(Board b, int col, int row) { // apply to exist tile
        int previousTileValue = 0;
        int currentTileValue = _board.tile(col, row).value();
        while (row < 3) {
            if (b.tile(col, row + 1) != null) {
                previousTileValue = _board.tile(col, row + 1).value();
                break;
            }
            row++;
        }
        return previousTileValue == currentTileValue;
    }

    private boolean isPreviousNonNullSame1(Board b, int col, int row) { // apply to exist tile
        int previousTileValue = 0;
        int currentTileValue = _board.tile(col, row + 1).value();
        while (row < 2) {
            if (b.tile(col, row + 1) != null) {
                previousTileValue = _board.tile(col, row + 2).value();
                break;
            }
            row++;
        }
        return previousTileValue == currentTileValue;
    }

    /**
     * Checks if the game is over and sets the gameOver variable
     * appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /**
     * Returns the model as a string, used for debugging.
     */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /**
     * Returns whether two models are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /**
     * Returns hash code of Model’s string.
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
