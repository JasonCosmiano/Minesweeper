package minesweeperTests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

import minesweeper.model.Location;
import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperException;

@Testable
public class MinesweeperTest {
    
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
