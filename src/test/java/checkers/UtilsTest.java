package checkers;

import checkers.model.Cell;
import checkers.model.Piece;
import checkers.model.Utils;
import checkers.players.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 29/10/2015.
 */
public class UtilsTest {

    @Test
    public void testCloneBoard() throws Exception {
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");

        Cell[][] boardA = Utils.generateInitialBoard(8, 8, p1, p2);
        Cell cellA = boardA[1][0];
        Piece pieceA = cellA.getContents();
        Player playerA = pieceA.getPlayer();

        Cell[][] boardB = Utils.cloneBoard(boardA);
        Cell cellB = boardB[1][0];
        Piece pieceB = cellB.getContents();
        Player playerB = pieceB.getPlayer();

        assertNotSame(cellA, cellB);
        assertNotSame(pieceA, pieceB);
        assertSame(playerA, playerB);
    }

    @Test
    public void testCloneBoardPiecePropertiesClones() throws Exception {
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");

        Cell[][] boardA = Utils.generateInitialBoard(8, 8, p1, p2);
        Piece pieceA = boardA[1][0].getContents();

        Cell[][] boardB = Utils.cloneBoard(boardA);
        Piece pieceB = boardB[1][0].getContents();

        assertEquals(pieceA.isCrowned(), pieceB.isCrowned());

        pieceA.crown();
        assertTrue(pieceA.isCrowned());
        assertFalse(pieceB.isCrowned());

        Cell[][] boardC = Utils.cloneBoard(boardA);
        Piece pieceC = boardC[1][0].getContents();
        assertTrue(pieceC.isCrowned());
    }
}