package checkers.model;

import checkers.players.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 31/10/2015.
 */
public class CellTest {

    @Test
    public void testRemoveContents() throws Exception {
        Player player = new Player("player1");
        Piece piece = new Piece(player);
        Cell cell = new Cell(piece);

        assertFalse(cell.isEmpty());

        // SUT
        Piece removedPiece = cell.removeContents();

        assertSame(piece, removedPiece);
        assertTrue(cell.isEmpty());
    }

    @Test
    public void testRemoveContentsWhenEmpty() throws Exception {
        Cell cell = new Cell();

        assertTrue(cell.isEmpty());

        // SUT
        Piece removedPiece = cell.removeContents();

        assertNull(removedPiece);
        assertTrue(cell.isEmpty());
    }


    @Test
    public void testClone() throws Exception {
        Player player = new Player("player1");
        Piece piece = new Piece(player);
        Cell cell = new Cell(piece);

        // SUT
        Cell clonedCell = cell.clone();

        assertNotSame(cell, clonedCell);
        assertNotSame(cell.getContents(), clonedCell.getContents());
        assertSame(cell.getContents().getPlayer(), clonedCell.getContents().getPlayer());
    }
}