package minesweeper.model;

import backtracker.Backtracker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

/**
 * Solve button updater
 * Project 1.3
 * Group 7
 */
public class solveUpdater implements EventHandler<ActionEvent>{

    private Minesweeper game;
    private Label numMovesLabel;
    private Label feedback;

    /**
     * Constructor
     * @param game the minesweeper game
     * @param numMovesLabel the number of moves label
     * @param feedback the feedback label
     */
    public solveUpdater(Minesweeper game, Label numMovesLabel, Label feedback) {
        this.game = game;
        this.numMovesLabel = numMovesLabel;
        this.feedback = feedback;
    }
    
    @Override
    public void handle(ActionEvent arg0) {

        System.out.println(game);
        Location location = new Location(0, 0); // start at (0, 0)
        MinesweeperConfiguration.resetSelectedLocations(); // clear selected list
        MinesweeperConfiguration config = new MinesweeperConfiguration(game, location); // new config
        config.isValid(); // does make selection inside method
        Backtracker tracker = new Backtracker(false);
        MinesweeperConfiguration solution = (MinesweeperConfiguration)tracker.solve(config); // solve

        if(solution != null) { // solved
            game = solution.getMinesweeper(); // set game to the solution
            game.cellUpdate(location);  
            numMovesLabel.setText(String.valueOf(game.getMoveCount())); // change number of moves to total number of moves
            feedback.setText("Solved!");
        }
    }
    
}
