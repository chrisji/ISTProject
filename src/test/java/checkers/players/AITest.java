package checkers.players;

import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.MoveChain;
import checkers.model.Piece;
import checkers.model.State;
import checkers.model.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Chris on 30/10/2015.
 */
public class AITest {

    @Test
    public void testMinimax() throws Exception {
        AI p1 = new AI("1", 2);
        AI p2 = new AI("2", 2);

        Game game = new Game(p1, p2, p1);

        Cell[][] board = new Cell[8][8];
        for (int i=0; i < board.length; i++) {
            for (int j=0; j < board[0].length; j++) {
                board[i][j] = new Cell();
            }
        }

        board[0][5].setContents(new Piece(p1));
        board[2][3].setContents(new Piece(p1));
        board[4][5].setContents(new Piece(p1));
        board[4][3].setContents(new Piece(p1));
        board[6][3].setContents(new Piece(p1));

        board[7][2].setContents(new Piece(p2));

        State state = new State(board, p2, null);
        game.setGameState(state);
        Utils.printBoard(board);

        System.out.println(p2.nextMoveChain().getMoves());
    }
}