package minesweeper.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


/**
 * Reset button event handler
 * Project 1.2
 * Group 7
 */
public class resetUpdater implements EventHandler<ActionEvent>{
    
    private Minesweeper game;
    private GridPane grid;
    private Label numMoves;
    private Label hint;
    private Label feedback;
    private Button hintButton;
    private Button solveButton;

    /**
     * Constructor
     * @param game the minesweeper game
     * @param grid grid pane
     * @param numMoves current number of moves label
     * @param hint the hint label
     * @param feedback feedback label
     * @param hintButton the hint button
     * @param solveButton the solve Button
     */
    public resetUpdater(Minesweeper game, GridPane grid, Label numMoves, Label hint, Button hintButton, Button solveButton , Label feedback){
        this.game = game;
        this.grid = grid;
        this.numMoves = numMoves;
        this.hint = hint;
        this.feedback = feedback;
        this.solveButton = solveButton;
        this.hintButton = hintButton;

    }

    @Override
    public void handle(ActionEvent arg0) {
        game = new Minesweeper(5, 5, 3); // make a new game

        // parse through grid
        for ( Node node : grid.getChildren() ) {
            Button button = (Button)node; 
            button.setDisable(false); // re-enable button
            button.setText(""); // blank
            button.setGraphic(null); // blank
            Location location = new Location(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)); // new location
            game.addButton(button, location); // add button to grid

            // new updater
            guiUpdater update = new guiUpdater(game, location, button, numMoves, feedback);
            button.setOnAction(update);

        }

        // reset labels
        numMoves.setText("0"); 
        feedback.setText("");
        hint.setText("Hint");

        hintButton.setOnAction(new hintUpdater(game, hint)); // reset hint button
        solveButton.setOnAction(new solveUpdater(game, numMoves, feedback)); // reset solve button
    }


}
