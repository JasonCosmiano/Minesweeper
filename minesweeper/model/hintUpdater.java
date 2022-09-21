package minesweeper.model;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

/**
 * Hint button event handler
 * Project 1.2
 * Group 7
 */
public class hintUpdater implements EventHandler<ActionEvent>{
    
    private Minesweeper game;
    private Label hint;

    /**
     * Constructor
     * @param game the minesweeper game
     * @param hint the label that gives a hint
     */
    public hintUpdater(Minesweeper game, Label hint){
        this.game = game;
        this.hint = hint;
    }

    @Override
    public void handle(ActionEvent arg0) {
        
        Random random = new Random();
        // get a random location that is safe
        int randomIndex = random.nextInt(game.getPossibleSelections().size());
        // game.getPossibleSelections().get(randomIndex);

        // set to string, and set the text to the safe coordinate
        String move = game.getPossibleSelections().get(randomIndex).toString();
        hint.setText(move);
        
    }
    
}
