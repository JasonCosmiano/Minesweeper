package minesweeper.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backtracker.Backtracker;
import backtracker.Configuration;

/**
 * Minesweeper backtracking configuration
 * Project 1.3
 * Group 7
 */
public class MinesweeperConfiguration implements Configuration {

    private Minesweeper minesweeper;
    private Location location;
    private static List<Location> selectedLocations = new ArrayList<>();

    /**
     * Constructor
     * @param minesweeper the minesweeper game
     * @param location the location where a selection is made
     */
    public MinesweeperConfiguration(Minesweeper minesweeper, Location location) {
        this.minesweeper = new Minesweeper(minesweeper);
        this.location = location;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new ArrayList<>();

        int newRow = location.getRow();

        if (location.getCol() + 1 >= minesweeper.getCols()) { // end of starting row
            newRow++;
        }

        // parse through the grid
        for (int row = newRow; row < minesweeper.getRows(); row++) {

            int col = location.getCol(); // set column to current

            if(col + 1 >= minesweeper.getCols()) { // last colmn of row, then reset to column 0
                col = 0;
            }

            else {
                col++;
            }

            for (; col < minesweeper.getCols(); col++) {

                Location newLocation = new Location(row, col);
                MinesweeperConfiguration newMineConfig = new MinesweeperConfiguration(minesweeper, newLocation); // new config
                successors.add(newMineConfig);
                
            }
        }
                 
       return successors;
    }

    @Override
    public boolean isValid() {

        try {
            minesweeper.makeSelection(location); // selection is valid
        } catch (MinesweeperException e) {
            e.printStackTrace();
            return false;
        }
        
        if (minesweeper.getGameState().equals(GameState.LOST)) {
            return false;
        }

        if (!selectedLocations.contains(location)) { // not selected yet
            selectedLocations.add(location); // add the current location to the selected location list
            System.out.println("Selection: " + location.toString() + "\n" + minesweeper.toString() 
                                    + "\n" + "Moves: " + minesweeper.getMoveCount() + "\n");
        }

        return true;
    }

    @Override
    public boolean isGoal() {
    
        if (minesweeper.getGameState().equals(GameState.WON)) { // winning is the goal
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String out = "";
        out += selectedLocations.toString() + "\n";
        out += minesweeper.toString();
        return out;
        
    }

    /**
     * Getter for the list of already selected location
     * @return the list of selected locations
     */
    public List<Location> getSelectedLocations(){
        return selectedLocations;
    }

    /**
     * Reset the selected locations list
     */
    public static void resetSelectedLocations() {
        selectedLocations = new ArrayList<>();
    }

    /**
     * Getter for the minesweeper game
     * @return the minesweeper game
     */
    public Minesweeper getMinesweeper() {
        return minesweeper;
    }

    public static void main(String[] args) throws MinesweeperException {

        // initialize game and location
        Minesweeper minesweeperGame = new Minesweeper(5, 5, 3);
        Location location = new Location(0, 0);

        // make new config
        MinesweeperConfiguration minesweeperConfig = new MinesweeperConfiguration(minesweeperGame, location);
        minesweeperConfig.isValid(); // make a move
        Backtracker tracker = new Backtracker(true);

        MinesweeperConfiguration solution = (MinesweeperConfiguration)tracker.solve(minesweeperConfig);

        if (solution != null) { // if solution found, then print
            System.out.println("solution found");
            System.out.println(solution.toString());
        }

        else { // solution not found
            System.out.println("no solution");
        }

        // System.out.println(solution.getSelectedLocations().size());
    }
    
}
