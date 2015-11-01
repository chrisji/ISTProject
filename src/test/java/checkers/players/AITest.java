package checkers.players;

import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.Move;
import checkers.model.MoveChain;
import checkers.model.Piece;
import checkers.model.State;
import checkers.model.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    @Test
    public void testMinimaxDepth2() throws Exception {
        AI ai = new AI("A", 2);
        Player human = new Player("H");
        Game game = new Game(ai, human, human);

        List<MoveChain> humanMoveChains = new ArrayList<MoveChain>();
        humanMoveChains.add(new MoveChain(new Move(5, 2, 4, 3))); // Turn 1
        humanMoveChains.add(new MoveChain(new Move(5, 6, 4, 5))); // Turn 2
        humanMoveChains.add(new MoveChain(new Move(6, 3, 5, 2))); // Turn 3
        humanMoveChains.add(new MoveChain(new Move(4, 3, 3, 2))); // ...
        humanMoveChains.add(new MoveChain(new Move(7, 4, 5, 2)));
        humanMoveChains.add(new MoveChain(new Move(5, 4, 4, 3)));
        humanMoveChains.add(new MoveChain(new Move(4, 3, 3, 2)));
        humanMoveChains.add(new MoveChain(new Move(7, 2, 5, 4)));
        humanMoveChains.add(new MoveChain(new Move(6, 1, 5, 2)));
        humanMoveChains.add(new MoveChain(new Move(7, 0, 6, 1)));
        humanMoveChains.add(new MoveChain(new Move(5, 2, 4, 1)));
        humanMoveChains.add(new MoveChain(new Move(6, 5, 5, 6)));
        humanMoveChains.add(new MoveChain(new Move(4, 5, 3, 4)));
        humanMoveChains.add(new MoveChain(new Move(7, 6, 6, 5)));
        humanMoveChains.add(new MoveChain(new Move(5, 6, 4, 5)));
        humanMoveChains.add(new MoveChain(new Move(6, 5, 5, 4)));
        humanMoveChains.add(new MoveChain(new Move(6, 7, 5, 6)));
        humanMoveChains.add(new MoveChain(new Move(5, 6, 4, 7)));
        humanMoveChains.add(new MoveChain(new Move(5, 4, 4, 3)));
        humanMoveChains.add(new MoveChain(new Move(4, 3, 2, 1)));
        humanMoveChains.add(new MoveChain(new Move(2, 1, 1, 2)));
        humanMoveChains.add(new MoveChain(new Move(1, 2, 0, 3)));
        humanMoveChains.add(new MoveChain(new Move(0, 3, 1, 4)));
        humanMoveChains.add(new MoveChain(new Move(1, 4, 2, 3)));
        humanMoveChains.add(new MoveChain(new Move(2, 3, 1, 2)));
        humanMoveChains.add(new MoveChain(new Move(1, 2, 2, 1)));
        humanMoveChains.add(new MoveChain(new Move(5, 0, 3, 2)));
        humanMoveChains.add(new MoveChain(new Move(3, 2, 2, 3)));
        humanMoveChains.add(new MoveChain(new Move(2, 3, 1, 4)));
        humanMoveChains.add(new MoveChain(new Move(1, 4, 0, 5)));
        humanMoveChains.add(new MoveChain(new Move(0, 5, 1, 6)));
        humanMoveChains.add(new MoveChain(new Move(1, 6, 2, 5)));
        humanMoveChains.add(new MoveChain(new Move(2, 5, 3, 6)));
        humanMoveChains.add(new MoveChain(new Move(2, 1, 3, 2)));
        humanMoveChains.add(new MoveChain(new Move(3, 2, 4, 3)));
        humanMoveChains.add(new MoveChain(new Move(4, 3, 3, 4)));
        humanMoveChains.add(new MoveChain(new Move(3, 6, 4, 5)));
        humanMoveChains.add(new MoveChain(new Move(4, 5, 3, 6)));
        humanMoveChains.add(new MoveChain(new Move(3, 4, 5, 6), new Move(5, 6, 7, 4)));
        humanMoveChains.add(new MoveChain(new Move(4, 7, 3, 6)));
        humanMoveChains.add(new MoveChain(new Move(7, 4, 6, 5)));
        humanMoveChains.add(new MoveChain(new Move(6, 5, 4, 3)));
        humanMoveChains.add(new MoveChain(new Move(4, 3, 5, 4)));
        humanMoveChains.add(new MoveChain(new Move(5, 4, 4, 3)));

        int turn = 1;
        for (MoveChain humanMc: humanMoveChains) {
            // Human Turn
            game.setGameState(game.doMoveChain(game.getGameState(), humanMc));
            System.out.println("\tHuman turn " + turn + ":" + humanMc.getMoves());

            // AI Turn
            MoveChain aiMc = ai.nextMoveChain();
            game.setGameState(game.doMoveChain(game.getGameState(), aiMc));
            System.out.println("\tAI turn " + turn + ": " + aiMc.getMoves());

            turn++;

            Utils.printBoard(game.getGameState().getBoard());
        }
    }

    @Test
    public void testMinimaxPushingDepth() throws Exception {
        AI ai = new AI("A", 8);
        Player human = new Player("H");
        Game game = new Game(ai, human, ai);
        game.doMoveChain(game.getGameState(), ai.nextMoveChain());
    }
}