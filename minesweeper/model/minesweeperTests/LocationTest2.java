package minesweeper.model.minesweeperTests;

import minesweeper.model.Location;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class LocationTest2 {
    
    @Test
    public void testGetRowandCol() {
        Location location = new Location(3, 2);

        assertTrue(location.getRow() == 3);
        assertTrue(location.getCol() == 2);
    }

    @Test
    public void testToString() {
        Location location = new Location(3, 2);

        String expected = "(3, 2)";
        String actual = location.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() {
        Location location = new Location(3, 2);
        Location location2 = new Location(3, 2);

        assertTrue(location.equals(location2));
    }

    @Test
    public void testEquals2() {
        Location location = new Location(3, 2);
        Location location2 = new Location(1, 2);

        assertFalse(location.equals(location2));
    }

    @Test
    public void testSetMineandIsMine() {
        Location location = new Location(3, 2);
        location.setMine(true);

        assertTrue(location.isMine() == true);
    }

    @Test
    public void testIsMine() {
        Location location = new Location(3, 2);

        assertTrue(location.isMine() == false);
    }

    @Test
    public void testGetandSetMineNeighborCount() {
        Location location = new Location(3, 2);
        location.setMineNeighborCount(3);

        int actual = 3;
        int expected = location.getMineNeighborCount();

        assertEquals(expected, actual);

    }
}
