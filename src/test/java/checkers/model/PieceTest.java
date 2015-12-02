package checkers.model;

import checkers.players.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class PieceTest {

    @Test
    public void testCrowningMethods() throws Exception {
        Player player = new Player("player");
        Piece piece = new Piece(player);

        assertFalse(piece.isCrowned());
        piece.crown(); // SUT
        assertTrue(piece.isCrowned());
    }

    @Test
    public void testGetPlayer() throws Exception {
        Player player = new Player("player");
        Piece piece = new Piece(player);
        assertSame(player, piece.getPlayer());
    }

    @Test
    public void testClone() throws Exception {
        Player player = new Player("player");
        Piece piece = new Piece(player);

        // SUT
        Piece clonedPiece = piece.clone();

        assertNotSame(piece, clonedPiece);
        assertEquals(piece.isCrowned(), clonedPiece.isCrowned());
        assertEquals(piece.toString(), clonedPiece.toString());
        assertSame(piece.getPlayer(), clonedPiece.getPlayer());
    }
}