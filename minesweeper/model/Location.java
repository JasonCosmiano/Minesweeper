package minesweeper.model;

/**
 * Location Class
 * Project 1.1
 * Group S9-7
 */
public class Location {

    // fields
    private final int row;
    private final int col;
    private boolean mine;
    private int mineNeighborCount;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
        this.mine = false;
        this.mineNeighborCount = 0;

    }

    /**
     * Getter for column
     * @return the column number
     */
    public int getCol() {
        return col;
    }

    /**
     * Getter for row
     * @return the row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for the number of neighbor mines
     * @return the number of neighbor mines
     */
    public int getMineNeighborCount() {
        return mineNeighborCount;
    }

    /**
     * Setter for the number of neighbor mines
     * @param mineNeighborCount the number of neighbors that are bombs
     */
    public void setMineNeighborCount(int mineNeighborCount) {
        this.mineNeighborCount = mineNeighborCount;
    }

    /**
     * Determines if a location is a cell or not
     * @return true or false
     */
    public boolean isMine() {
        return mine;
    }

    /**
     * Makes a location a mine
     * @param mine
     */
    public void setMine(boolean mine) {
        this.mine = mine;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Location)) {
            return false;
        }
        Location other = (Location)(o);
        return (row == other.row) && (col == other.col);
    }

    @Override
    public int hashCode() {
        return (row + 100) * col;
    }

    @Override
    public String toString() {
        String out = "";
        out += String.format("(%d, %d)", row, col);
        return out;
    }
  
}

