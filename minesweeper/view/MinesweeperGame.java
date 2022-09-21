package minesweeper.view;

import java.util.Scanner;

import backtracker.Backtracker;
import minesweeper.model.GameState;
import minesweeper.model.Location;
import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperConfiguration;
import minesweeper.model.MinesweeperException;


/**
 * This is the part where you view the game (UI)
 * Project 1.1
 * Group S9-7
 */
public class MinesweeperGame {
    private static int Row;
    private static int Col;
    private static int mineCount;
    private static Minesweeper game;
   

    public static int command(String command) throws MinesweeperException {
        int status = 0;

        String[] word = command.split(" ");

        if(word[0].toLowerCase().equals("help")) {
            System.out.println("Help: displays all of the commands \n" +
                    "pick row col: attempts to uncover a cell at the given location. \n" +
                    "hint: displays an available move. \n" +
                    "solve: solves the entire game"+
                    "reset: reset to a new game with the same board size and number of mines. \n" +
                    "quit: ends the game. ");
        }
        if (word[0].toLowerCase().equals("pick")){

            int row = Integer.parseInt(word[1]);
            int col = Integer.parseInt(word[2]);

            Location loc = new Location(row, col);
            game.makeSelection(loc);
        }
        if (word[0].toLowerCase().equals("hint")){
           for (Location move: game.getPossibleSelections()){
               System.out.println(move);
               break;
           }
        }
        if (word[0].toLowerCase().equals("reset")){

            game = new Minesweeper(Row,Col,mineCount);

        }
        if (word[0].toLowerCase().equals("quit")){

            System.out.println("Game Over! Goodbye!");
            return ++status;
        }
        if(word[0].toLowerCase().equals("solve")){

            Location location = new Location(0, 0);

            // make new config
            MinesweeperConfiguration minesweeperConfig = new MinesweeperConfiguration(game, location);
            minesweeperConfig.isValid(); // make a move
            Backtracker tracker = new Backtracker(false);
    
            MinesweeperConfiguration solution = (MinesweeperConfiguration)tracker.solve(minesweeperConfig);

            if (solution != null) { // if solution found, then print
                System.out.println("solution found");
                for (Location aLocation : solution.getSelectedLocations()) {
                    game.makeSelection(aLocation);
                }
            }
    
            else { // solution not found
                System.out.println("no solution");
            }
        }
        return status;
    }

/**
 * This method implements the minesweeper game 
 * Works by getting in the conditions for the gameStart method
 */
    public static void gameStart() {
        
        Scanner s = new Scanner(System.in); // Directing user input
        System.out.print("Enter the number or rows and column and also the number of mines: ");
        String initialInput = s.nextLine();
        String[] initialSplit = initialInput.split(" ");
        Row = Integer.parseInt(initialSplit[0]);
        Col = Integer.parseInt(initialSplit[1]);
        mineCount = Integer.parseInt(initialSplit[2]);
        game = new Minesweeper(Row,Col, mineCount);
        game.register(null);
        System.out.println(game);
        GameState state = game.getGameState();
        while(state != GameState.LOST && state != GameState.WON) { // a simple condition (while the game has not been won or lost Loop)
            System.out.print("Enter a command: ");
            try{
                if(command(s.nextLine()) != 1) {; //calls the command on the inputted string
                    state = game.getGameState();
                    System.out.println(game);
                    System.out.println("\n");
                    System.out.println("Number of moves: "+game.getMoveCount());
                } else {
                    break;
                }
            } catch(MinesweeperException mse) {
                System.out.println("Invalid input.");
            }
        }
        /**
         * Two conditions at the end to print the following messages
         */
        if(state == GameState.LOST){
            System.out.println("BOOM! Better luck next time!");
        }
        else if(state == GameState.WON){
            System.out.println("Good Job, You Won");
        }

        s.close();
    }
    public static void main(String[] args) {
        gameStart();
    }
}
