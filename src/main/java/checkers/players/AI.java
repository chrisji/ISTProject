package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.Cell;
import checkers.model.Move;
import checkers.model.MoveChain;
import checkers.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public class AI extends Player {

    public AI(String name) {
        super(name);
    }

    public Move nextMove() {
        return getGame().getValidMoves(getGame().getGameState()).get(0);
    }

    /**
     function minimax(node, depth, player)
        if depth == 0 or node == leaf
            return the value of node

        if player == MAX
            bestValue = -∞
            for each child of node
                eval = minimax(child, depth - 1, MIN)
                bestValue = max(bestValue, eval)
            return bestValue

        if player == MIN
            bestValue = +∞
            for each child of node
                eval = minimax(child, depth - 1, MAX)
                bestValue = min(bestValue, eval)
            return bestValue
     */

    private int minimax(State state, int depth, Player player) {
        if (depth == 0 || getGame().getValidMoves(state).isEmpty()) {
            return evaluateState(state);
        }

        if (player == getGame().getPlayer1()) {
            // MAX player's turn
            int bestValue = Integer.MIN_VALUE;

            List<MoveChain> moveChains = new ArrayList<MoveChain>();
            try {
                moveChains = getGame().getMoveChains(state, player, moveChains, new MoveChain());

                // Recursively find the best value
                for (MoveChain moveChain : moveChains) {
                    int eval = minimax(getGame().doMoveChain(state, moveChain), depth - 1, getGame().getPlayer2());
                    bestValue = Math.max(bestValue, eval);
                }
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }

            return bestValue;
        } else {
            // MIN player's turn
            int bestValue = Integer.MAX_VALUE;
            List<MoveChain> moveChains = new ArrayList<MoveChain>();
            try {
                // Get all valid chains of moves.
                moveChains = getGame().getMoveChains(state, player, moveChains, new MoveChain());

                // Recursively find the best value
                for (MoveChain moveChain : moveChains) {
                    int eval = minimax(getGame().doMoveChain(state, moveChain), depth - 1, getGame().getPlayer1());
                    bestValue = Math.min(bestValue, eval);
                }

            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }

            return bestValue;
        }
    }

    private int evaluateState(State s) {
        Cell[][] board = s.getBoard();

        int score = 0;

        int rows = board.length;
        int cols = board[0].length;

        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                if (!board[i][j].isEmpty()) {
                    // If piece belongs to player1 increment score, if player2 decrement score.
                    if (board[i][j].getContents().getPlayer() == getGame().getPlayer1()) {
                        score++;
                    } else {
                        score--;
                    }
                }
            }
        }

        return score;
    }
}
