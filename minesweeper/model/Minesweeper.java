package minesweeper.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Minesweeper Game Logic
 * Project 1.1
 * Group S9-7
 */
public class Minesweeper implements MinesweeperObserver{
    public char MINE = 'M';
    public char COVERED = '-';
    
    private char[][] uncoveredBoard;
    private char[][] coveredBoard;
    private GameState state = GameState.NOT_STARTED;
    private int numberOfMoves;
    private int mineCount;
    private MinesweeperObserver observer;
    private int rows;
    private int cols;
    private Button[][] buttonGrid;

    /**
     * Copy constructor
     * @param minesweeper the minesweeper game
     */
    public Minesweeper(Minesweeper minesweeper){

        // deep copies
        this.uncoveredBoard = Arrays.stream(minesweeper.getUncoveredBoard())
                                .map(el -> el.clone())
                                .toArray($ -> minesweeper.getUncoveredBoard().clone());

        this.coveredBoard = Arrays.stream(minesweeper.getCoveredBoard())
                                .map(el -> el.clone())
                                .toArray($ -> minesweeper.getCoveredBoard().clone());

        this.buttonGrid = Arrays.stream(minesweeper.getButtonGrid())
                                .map(el -> el.clone())
                                .toArray($ -> minesweeper.getButtonGrid().clone());

        this.rows = minesweeper.getRows();
        this.cols = minesweeper.getCols();
        this.mineCount = minesweeper.getMineCount();
        this.numberOfMoves = minesweeper.getMoveCount();

        this.state = minesweeper.getGameState();
        
        this.observer = null;

    }

    /**
     * Constructor for Minesweeper game
     * @param rows number of rows
     * @param cols number of columns
     * @param mineCount number of mines
     */
    public Minesweeper(int rows, int cols, int mineCount){

        this.mineCount = mineCount;

        this.buttonGrid = new Button[rows][cols];

        register(this);

        // this.mineCount = 0;
        this.rows = rows;
        this.cols = cols;

        //covered board
        this.coveredBoard = new char[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                coveredBoard[row][col] = COVERED;
            }
        }

        // uncovered board
        this.uncoveredBoard = new char[rows][cols];

        Set<Location> mineLocations = new HashSet<>(); // initialize a set of mine locations
        
        Random random = new Random(1); // seed for testing purposes 
        // ********************IMPORTANT*********************
        //(FOR JUNIT, uncomment this line, and comment line 69)

        // to run random, uncomment line below, and comment line 64)
        // Random random = new Random(); // no seed

        while (mineLocations.size() < mineCount) {

            // randomize row location
            int randomRow = random.nextInt(rows);
            int randomCol = random.nextInt(cols);

            Location newLocation = new Location(randomRow, randomCol);

            if(mineLocations.contains(newLocation)) { // already in set (avoid repeat mine)
                continue;
            }

            newLocation.setMine(true); // now location is a mine

            mineLocations.add(newLocation); // add it to the mine set
        }

        System.out.println(mineLocations.toString());
        
        // make uncovered board
        for (int row = 0; row < rows; row++) {

            for(int col = 0; col < cols; col++) {

                // mines
                if (mineLocations.contains(new Location(row, col))) {
                    uncoveredBoard[row][col] = MINE;
                }

                // not mines
                else {
    
                    int mineNeighborCount = 0; // initialize mine neighbor count

                    // mine is to diagonal down right
                    if(mineLocations.contains(new Location(row + 1, col + 1)) && row + 1 <= rows && col + 1 <= cols) {
                        mineNeighborCount++;
                    }

                    // mine is below
                    if(mineLocations.contains(new Location(row + 1, col)) && row + 1 <= rows) {
                        mineNeighborCount++;
                    }

                    // mine is diagonal down left
                    if(mineLocations.contains(new Location(row + 1, col - 1)) && row + 1 <= rows && col - 1 >= 0) {
                        mineNeighborCount++;
                    }

                    // mine is to the right
                    if(mineLocations.contains(new Location(row, col + 1)) && col + 1 <= cols) {
                        mineNeighborCount++;
                    }

                    // mine is to the left
                    if(mineLocations.contains(new Location(row, col - 1)) && col - 1 >= 0) {
                        mineNeighborCount++;
                    }

                    // mine is diagonal up right
                    if(mineLocations.contains(new Location(row - 1, col + 1)) && row - 1 >= 0 && col + 1 <= cols) {
                        mineNeighborCount++;
                    }

                    // mine is above
                    if(mineLocations.contains(new Location(row - 1, col)) && row - 1 >= 0) {
                        mineNeighborCount++;
                    }

                    // mine is diagonal up left
                    if(mineLocations.contains(new Location(row - 1, col - 1)) && row - 1 >= 0 && col - 1 >= 0) {
                        mineNeighborCount++;
                    }

                    char mineCountChar = Integer.toString(mineNeighborCount).charAt(0);
                    uncoveredBoard[row][col] = mineCountChar;
                }

                
            }
        }


    }


    /**
     * getter for number of rows
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * getter for number of columns
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * getter for the covered board
     * @return the covered board
     */
    public char[][] getCoveredBoard() {
        return coveredBoard;
    }

    /**
     * getter for the uncovered board
     * @return the uncovered board
     */
    public char[][] getUncoveredBoard() {
        return uncoveredBoard;
    }

    /**
     * Getter for mine count
     * @return the mine count
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * getter for the button grid
     * @return the button grid
     */
    public Button[][] getButtonGrid() {
        return buttonGrid;
    }
    
    
    /**
     * Attemps to select a location on the board
     * @param location the wanted location to be played
     * @throws MinesweeperException error occured
     */
    public void makeSelection(Location location) throws MinesweeperException{
        
        state = GameState.IN_PROGRESS; // game is in progress

        numberOfMoves++; // increment number of moves

        if (uncoveredBoard[location.getRow()][location.getCol()] != MINE) { // if location is not a mine

            // turn the "-" into the mineNeighbor count
            coveredBoard[location.getRow()][location.getCol()] = uncoveredBoard[location.getRow()][location.getCol()];

            if (numberOfMoves == (rows * cols) - mineCount) {
                state = GameState.WON;
            }

        }

        else { // hit a mine
            state = GameState.LOST;
        }

        // System.out.println("number of moves: " + numberOfMoves);

        notifyObserver(location); // notify
    }

    /**
     * Getter for the number of moves
     * @return the number of moves so far
     */
    public int getMoveCount(){
        return numberOfMoves;
    }

    /**
     * Getter for that state of the game (IN_PROGRESS, LOST, WON, etc)
     * @return the state of the game
     */
    public GameState getGameState(){
        return state;
    }

    /**
     * Get all valid cell locations (covered and safe)
     * @return the list of all valid cell locations
     */
    public List<Location> getPossibleSelections(){
        // initialize list
        List<Location> list = new ArrayList<>();

        // parse through board
        for (int row = 0 ; row < uncoveredBoard.length; row++) {
            for (int col = 0; col < uncoveredBoard[0].length; col++) {

                // if cell hasn't been pick yet and it's not a mine, add to list
                if (coveredBoard[row][col] == COVERED && uncoveredBoard[row][col] != MINE ) { 
                    list.add(new Location(row, col)); 
                }

                else {
                    continue;
                }
            }
        }

        return list;
    }

    /**
     * add a button to the button grid
     * @param button the button to be added
     * @param location the location on the grid
     */
    public void addButton(Button button, Location location) {
        buttonGrid[location.getRow()][location.getCol()] = button;
    }
 
    @Override
    public String toString() {
        String out = "";

        if (state != GameState.LOST) {
            // should be covered board
            for (int row = 0; row < coveredBoard.length; row++) {
                for (int col = 0; col < coveredBoard[0].length; col++) {
                    
                    out += Character.toString(coveredBoard[row][col]) + " ";
                }
                out += "\n";
            }

            return out;
        }

        else { // lost
            // should be uncovered board
            for (int row = 0; row < uncoveredBoard.length; row++) {
                for (int col = 0; col < uncoveredBoard[0].length; col++) {
                    
                    out += Character.toString(uncoveredBoard[row][col]) + " ";
                }
                out += "\n";
            }

            return out;
        }
    }

    /**
     * Register observer that wishes to be notified when a cell is updated
     * @param observer the object to be notified
     */
    public void register(MinesweeperObserver observer) {
        this.observer = observer;
    }

    /**
     * Notifies an observer that a cell identified by the given 
     * location has been updated
     * @param location the location that was updated
     */
    private void notifyObserver(Location location){
        if(observer != null){
            observer.cellUpdate(location);
        }
    }

    @Override
    public void cellUpdate(Location location) { 
        if (state != GameState.IN_PROGRESS) {

            // parse through uncovered board and button grid
            for (int row = 0; row < rows; row++) {
                for(int col = 0; col < cols; col++) {

                    if (uncoveredBoard[row][col] == 'M') { // mine hit, set graphic to mine img
                        ImageView backImg = new ImageView(new Image("file:media/images/mine24.png"));
                        buttonGrid[row][col].setGraphic(backImg);

                        // disable buttons if you hit a mine
                        for (int r = 0; r < rows; r++) {
                            for(int c = 0; c < cols; c++) {
                                buttonGrid[r][c].setDisable(true);
                            }
                        }
                    }
        
                    else { // not bomb
                        if (uncoveredBoard[row][col] == '0') { // blank out
                            buttonGrid[row][col].setText("");
                        }

                        else {
                            buttonGrid[row][col].setText( String.valueOf(uncoveredBoard[row][col]));
                        }
                    
                    }
                }

            }
        }
        
    }

    /**
     * getter for character that represents state of a cell at given location
     * @param location the location of a cell
     * @return the state (covered or uncovered)
     */
    public char getSymbol(Location location){
        return coveredBoard[location.getRow()][location.getCol()];
    }

    /**
     * Determines if cell at location is covered
     * @param location desired location of cell
     * @return true if cell is covered, false otherwise
     */
    public boolean isCovered(Location location){
        if (coveredBoard[location.getRow()][location.getCol()] == COVERED)  {
            return true;
        }
        return false;
    }
}
