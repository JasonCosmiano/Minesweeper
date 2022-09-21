package minesweeper.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import minesweeper.model.Location;
import minesweeper.model.Minesweeper;
import minesweeper.model.guiUpdater;
import minesweeper.model.hintUpdater;
import minesweeper.model.resetUpdater;
import minesweeper.model.solveUpdater;


/**
 * Minesweeper GUI
 * Project 1.2
 * Group 7
 */
public class MinesweeperGUI extends Application {

    // game
    private Minesweeper game;

    @Override
    public void start(Stage stage) throws Exception {
        
        game = new Minesweeper(5, 5, 3); // initialize game
        
        Label numMines = makeLabel("Mines: 3", Color.RED, Color.BLACK, 15); // number of mines label
        Label numMoves = makeLabel("0", Color.RED, Color.BLACK, 20); // number of moves label

        // move count label increment
        int i = game.getMoveCount();
        String s = Integer.toString(i);
        numMoves.setText(s);

        // hint label
        Label hintLabel = makeLabel("Hint", Color.RED, Color.BLACK, 15);
        
        // hint button
        Button hint = makeButton("Hint");
        hint.setPrefSize(50, 50);
        hintUpdater giveHint = new hintUpdater(game, hintLabel);
        hint.setOnAction(giveHint);

        // status/feedback label
        Label feedback = makeLabel("", Color.RED, Color.BLACK, 20);

        // solve button
        Button solve = makeButton("Solve");
        solve.setPrefSize(50, 50);
        solveUpdater solver = new solveUpdater(game, numMoves, feedback);
        solve.setOnAction(solver);

        GridPane grid = new GridPane();

        // make grid pane
        for (int row = 0; row < game.getRows(); row ++) {

            for (int col = 0; col < game.getCols(); col ++) {

                    Location location = new Location(row, col);

                    Button button = new Button("");
                    button.setPrefSize(40, 30);
                    // button.alignmentProperty(TextAlignment.CENTER);
                    guiUpdater update = new guiUpdater(game, location, button, numMoves, feedback);

                    game.addButton(button, location);

                    button.setOnAction(update);
                    grid.add(button, col, row);
            }
        }

        // reset button
        Button reset = makeButton("Reset");
        resetUpdater resetter = new resetUpdater(game, grid, numMoves, hintLabel, hint, solve, feedback);
        reset.setPrefSize(50, 50);
        reset.setOnAction(resetter);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(numMines, reset ,hintLabel, hint, numMoves, solve);

        VBox all = new VBox();
        all.getChildren().addAll(hbox, grid, feedback);
        
        Scene scene = new Scene(all);
        stage.setScene(scene);
        stage.setTitle("Minsweeper");
        stage.show();           
    }

    /**
     * Makes a button
     * @param text String - the text of the label
     * @return a button
     */

    private static Button makeButton(String text){
        Button button = new Button(text);
        button.setAlignment(Pos.CENTER);
        button.setPrefSize(40, 30);

        return button;
    }

    /**
     * Makes a label
     * @param text String - the text of the label
     * @param foreground Color - the color of the text
     * @param background Color the color of the background
     * @return
     */
    public static Label makeLabel (String text, Color foreground, Color background, int fontSize) {
        Label label = new Label(text);

        // label characteristics
        label.setFont(new Font("Comic Sans MS", fontSize));
        label.setTextFill(foreground);
        label.setPadding(new Insets(fontSize));
        label.setBackground(new Background( new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.POSITIVE_INFINITY);
        label.setPrefSize(100, 50);
        label.setPadding(new Insets(10));

        return label;
    }


    public static void main(String[] args) {
        launch(args);
    }


}
