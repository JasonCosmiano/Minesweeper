package minesweeper.model.minesweeperTests;

import org.junit.platform.commons.annotation.Testable;

import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperException;
import minesweeper.model.GameState;
import minesweeper.model.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

@Testable
public class MinesweeperTest2 {
        
    @Test
    public void testMakeSelection() throws MinesweeperException {
        Minesweeper game = new Minesweeper(5, 5, 3);
        Location location = new Location(2, 2);
        game.makeSelection(location);

        assertTrue(game.getGameState() == GameState.IN_PROGRESS);

        Location location2 = new Location(0, 0); // intentional mine picked
        game.makeSelection(location2);
        assertFalse(game.getGameState() == GameState.LOST);

    }

    @Test
    public void testGetMoveCount() throws MinesweeperException {
        Minesweeper game = new Minesweeper(4, 4, 3);
        Location location = new Location(2, 2);
        game.makeSelection(location);

        assertTrue(game.getMoveCount() == 1);
    }

    @Test
    public void testGetPossibleSelections() throws MinesweeperException {
        Minesweeper game = new Minesweeper(2, 2, 1);
        Location location = new Location(0, 0);
        game.makeSelection(location);

        List<Location> actual = game.getPossibleSelections();

        Location location2 = new Location(0, 1);
        // Location location3 = new Location(1, 0); // mine
        Location location4 = new Location(1, 1);

        List<Location> expected = Arrays.asList(location2, location4);

        assertEquals(expected, actual);
    }
}
