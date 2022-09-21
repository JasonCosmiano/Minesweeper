package minesweeper.model.minesweeperTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

import backtracker.Backtracker;
import backtracker.Configuration;
import javafx.print.Collation;
import minesweeper.model.Location;
import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperConfiguration;
import minesweeper.model.MinesweeperException;

@Testable
public class MinesweeperTest {

    @Test
    public void testgetSymbol() throws MinesweeperException{
        Minesweeper game = new Minesweeper(2, 2, 1);
        Location location = new Location(0, 0);
        
        // game.getSymbol(location);

        char actual =  game.getSymbol(location);
        char expected = '-';

        assertEquals(expected, actual);    }
    
    @Test
    public void TestisValid(){
        Minesweeper minesweeperGame = new Minesweeper(5, 5, 3);
        Location location = new Location(0, 0);
        boolean expected = true;

        MinesweeperConfiguration minesweeperConfig = new MinesweeperConfiguration(minesweeperGame, location);
        boolean actual = minesweeperConfig.isValid(); 
        
        assertEquals(expected, actual);
    }

    @Test
    public void TestisValid2(){
        Minesweeper minesweeperGame = new Minesweeper(5, 5, 3);
        Location location = new Location(0, 0);

        MinesweeperConfiguration minesweeperConfig = new MinesweeperConfiguration(minesweeperGame, location);
        Backtracker tracker = new Backtracker(false);
        tracker.solve(minesweeperConfig);

        boolean expected = true;
        boolean actual = minesweeperConfig.isValid(); 
        
        assertEquals(expected, actual);
    }

    @Test
    public void TestisGoal(){
        Minesweeper minesweeperGame = new Minesweeper(5, 5, 3);
        Location location = new Location(0, 0);
        boolean expected = false;
        MinesweeperConfiguration minesweeperConfig = new MinesweeperConfiguration(minesweeperGame, location);
        boolean actual = minesweeperConfig.isGoal();
        
        assertEquals(expected, actual);
    }

    @Test
    public void TestisGoal2(){
        Minesweeper minesweeperGame = new Minesweeper(5, 5, 3);
        Location location = new Location(0, 0);
        MinesweeperConfiguration minesweeperConfig = new MinesweeperConfiguration(minesweeperGame, location);

        Backtracker tracker = new Backtracker(false);
        tracker.solve(minesweeperConfig);

        boolean actual = minesweeperConfig.isGoal();
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetSuccessors() {
        Minesweeper minesweeperGame = new Minesweeper(2, 2, 1);
        Location location = new Location(0, 0);
        MinesweeperConfiguration minesweeperConfig = new MinesweeperConfiguration(minesweeperGame, location);

        List<Configuration> list = (List<Configuration>) minesweeperConfig.getSuccessors();
        int actual = list.size();
        int expected = 2;

        assertEquals(expected, actual);

    }


}


