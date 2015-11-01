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

    private Move bestNextMove;
    private int depth;

    public AI(String name, int depth) {
        super(name);
        this.depth = depth;
    }

    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> possibleMoveChains = getGame().getMoveChains(getGame().getGameState(), this);

        if (!possibleMoveChains.isEmpty()) {
            // Initialise best move chain and best value.
            MoveChain bestChain = possibleMoveChains.remove(0);

            int bestValue = minimax(getGame().doMoveChain(getGame().getGameState(), bestChain), depth - 1, getGame().incrementTurn(this));
            System.out.println("Move Chain:" + bestChain.getMoves().toString() + " minimax score: " + bestValue);

            // Iterate through other possible moves and find best move.
            for (MoveChain moveChain: possibleMoveChains) {
                State resultState = getGame().doMoveChain(getGame().getGameState(), moveChain);

                int prevBest = bestValue;
                int score = minimax(resultState, depth-1, getGame().incrementTurn(this));

                // Update best value
                bestValue = getBestValue(bestValue, score);
                System.out.println("Move Chain:" + moveChain.getMoves().toString() + " minimax score: " + score);

                // If previous value is now not the best, update best moveChain
                if (prevBest != bestValue) {
                    bestChain = moveChain;
                }
            }

            return bestChain;
        }

        return null;
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
    public int minimax(State state, int depth, Player player) {
        if (depth == 0 || getGame().getValidMoves(state).isEmpty()) {
            return evaluateState(state);
        }

        if (player == getGame().getPlayer1()) {
            // MAX player's turn
            int bestValue = Integer.MIN_VALUE;

            try {
                // Recursively find the best value
                for (MoveChain moveChain : getGame().getMoveChains(state, player)) {
                    int eval = minimax(getGame().doMoveChain(state, moveChain), depth - 1, getGame().getPlayer2());
                    bestValue = Math.max(bestValue, eval);
//                    System.out.println("MAX, BEST VALUE=" + bestValue + ", DEPTH=" + depth);
                }
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }

            return bestValue;
        } else {
            // MIN player's turn
            int bestValue = Integer.MAX_VALUE;
            try {
                // Recursively find the best value
                for (MoveChain moveChain : getGame().getMoveChains(state, player)) {
                    int eval = minimax(getGame().doMoveChain(state, moveChain), depth - 1, getGame().getPlayer1());
                    bestValue = Math.min(bestValue, eval);
//                    System.out.println("MIN, BEST VALUE=" + bestValue + ", DEPTH=" + depth);
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

    public int getBestValue(int value1, int value2) {
        boolean isMax = this == getGame().getPlayer1();

        if (isMax) {
            return Math.max(value1, value2);
        } else {
            return Math.min(value1, value2);
        }
    }

    public int getWorstValue() {
        boolean isMax = this == getGame().getPlayer1();

        if (isMax) {
            return Integer.MIN_VALUE;
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
