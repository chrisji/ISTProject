package checkers;

import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.MoveChain;
import checkers.model.Piece;
import checkers.model.State;
import checkers.model.Utils;
import checkers.players.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Chris on 03/10/2015.
 */
public class GameTest {

    @Test
    public void testIncrementTurn() throws Exception {
        Player p1 = new Player("1");
        Player p2 = new Player("2");

        Game game = new Game(p1, p2, p1);


        Cell[][] board = new Cell[8][8];
        for (int i=0; i < board.length; i++) {
            for (int j=0; j < board[0].length; j++) {
                board[i][j] = new Cell();
            }
        }

        board[2][3].setContents(new Piece(p1));
        board[2][1].setContents(new Piece(p1));
        board[4][5].setContents(new Piece(p1));
        board[4][3].setContents(new Piece(p1));
        board[6][3].setContents(new Piece(p1));

        board[7][2].setContents(new Piece(p2));

        State state = new State(board, p2, null);
        game.setGameState(state);
        Utils.printBoard(board);

        for (MoveChain mc : game.getMoveChains(state, p2, new ArrayList<MoveChain>(), new MoveChain())) {
//            System.out.println(mc.getMoves());
        }
    }
}