package minesweeper.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Button updater for the buttons in the grid
 * Project 1.2
 * Group 7
 */
public class guiUpdater implements EventHandler<ActionEvent>{
    
    private Button button;
    private Minesweeper game;
    private Location location;
    private Label numMoves;
    private Label feedback;

    /**
     * Constructor
     * @param game the minesweeper game
     * @param location location of the button
     * @param button the button in the grid
     * @param numMoves the label showing the number of moves
     * @param feedback the feedback label
     */
    public guiUpdater(Minesweeper game, Location location, Button button, Label numMoves, Label feedback) {
        this.game = game;
        this.location = location;
        this.button = button;
        this.numMoves = numMoves;
        this.feedback = feedback;
    }

    @Override
    public void handle(ActionEvent arg0) {

        numMoves.setText(String.valueOf(game.getMoveCount() + 1)); // increment label number of moves

        // if a mine, then set button img to mine
        if (game.getUncoveredBoard()[location.getRow()][location.getCol()] == 'M') {
                ImageView backImg = new ImageView(new Image("file:media/images/mine24.png"));
                button.setGraphic(backImg);
        }

        else {

            // blank if 0
            if (game.getUncoveredBoard()[location.getRow()][location.getCol()] == '0') {
                button.setText("");
            }
            
            else{  // number of mine neighbors
                button.setText( String.valueOf(game.getUncoveredBoard()[location.getRow()][location.getCol()]));
            }
        }

        // make selection
        try {
            game.makeSelection(location); 
        } catch (MinesweeperException e) {
            e.printStackTrace();
        }

        if (game.getGameState().equals(GameState.LOST)) {
            feedback.setText("You Lost");
        }

        else if (game.getGameState().equals(GameState.WON)) {
            feedback.setText("You Won");
        }
        
        button.setDisable(true); // can't press button again
    }
    
}
    

