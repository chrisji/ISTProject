package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.Cell;
import checkers.model.Move;
import checkers.model.MoveChain;
import checkers.model.State;

import java.util.List;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class AIAlphaBetaOld extends AI {

    private int depth = 1;
    private static long evalCounter = 0;

    public AIAlphaBetaOld(String name) {
        super(name, true);
    }

    public AIAlphaBetaOld(String name, int depth) {
        super(name, true);
        this.depth = depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void setDifficulty(int difficultyLevel) {

    }

    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> possibleMoveChains = getGame().getMoveChains(getGame().getGameState(), this);

        if (!possibleMoveChains.isEmpty()) {
            // Initialise alpha and beta
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;

            // Initialise best value and move chain to be the first move chain.
            MoveChain bestChain = possibleMoveChains.remove(0);
            State resultState = getGame().doMoveChain(getGame().getGameState(), bestChain);
            int bestValue = alphaBeta(resultState, depth - 1, alpha, beta, getGame().incrementTurn(this));
            System.out.println("Move Chain:" + bestChain.getMoves().toString() + " alpha-beta score: " + bestValue);

            // Iterate through other possible moves and update the best move chain.
            for (MoveChain moveChain: possibleMoveChains) {
                // Get the state from applying the move chain
                resultState = getGame().doMoveChain(getGame().getGameState(), moveChain);

                int prevBest = bestValue;
                int score = alphaBeta(resultState, depth - 1, alpha, beta, getGame().incrementTurn(this));

                // Update best value
                bestValue = getBestValue(bestValue, score);
                System.out.println("Move Chain:" + moveChain.getMoves().toString() + " alpha-beta score: " + score);

                // If previous value is no longer the best, update best moveChain
                if (prevBest != bestValue) {
                    bestChain = moveChain;
                }
            }

            System.out.println("ALPHA-BETA COUNT: " + evalCounter);
            return bestChain;
        }
        return null; // No possible move chains
    }

    /**
     function alphabeta(node, depth, alpha, beta, player)
        if depth == 0 or node == leaf
            return the value of node

        if player == MAX
            bestValue = -∞
            for each child of node
                eval = alphabeta(child, depth - 1, alpha, beta, MIN)
                bestValue = max(bestValue, eval)
                alpha := max(aplha, bestValue)
                if alpha >= beta:
                    // break... beta cutoff
            return bestValue

        if player == MIN
            bestValue = +∞
            for each child of node
                eval = alphabeta(child, depth - 1, alpha, beta,  MAX)
                bestValue = min(bestValue, eval)
                beta := min(beta, bestValue)
                if alpha >= beta:
                    // break... alpha cutoff
            return bestValue
     */
    public int alphaBeta(State state, int depth, int alpha, int beta, Player player) {
        if (depth == 0 || getGame().getValidMoves(state).isEmpty()) {
            return evaluateState(state);
        }

        if (player == getGame().getPlayer1()) {
            // MAX player's turn
            int bestValue = Integer.MIN_VALUE;
            try {
                // Recursively find the best value
                for (MoveChain moveChain : getGame().getMoveChains(state, player)) {
                    int eval = alphaBeta(getGame().doMoveChain(state, moveChain), depth - 1, alpha, beta, getGame().getPlayer2());

                    bestValue = Math.max(bestValue, eval);
                    alpha = Math.max(alpha, bestValue);

                    if (alpha >= beta) {
                        break; // beta cut-off
                    }
                }
            } catch (InvalidMoveException e) {
                // Move in the obtained move chains was invalid - this should never happen.
                e.printStackTrace();
            }

            return bestValue;
        } else {
            // MIN player's turn
            int bestValue = Integer.MAX_VALUE;
            try {
                // Recursively find the best value
                for (MoveChain moveChain : getGame().getMoveChains(state, player)) {
                    int eval = alphaBeta(getGame().doMoveChain(state, moveChain), depth - 1, alpha, beta, getGame().getPlayer1());

                    bestValue = Math.min(bestValue, eval);
                    beta = Math.min(beta, bestValue);

                    if (alpha >= beta) {
                        break; // alpha cut-off
                    }
                }
            } catch (InvalidMoveException e) {
                // Move in the obtained move chains was invalid - this should never happen.
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

        evalCounter++; // TODO: remove debugging
        return score;
    }

    public int getBestValue(int value1, int value2) {
        // Check to see if this player is the game's player 1, and therefore 'MAX'.
        boolean isMax = this == getGame().getPlayer1();

        if (isMax) {
            return Math.max(value1, value2);
        } else {
            return Math.min(value1, value2);
        }
    }

    public int getWorstValue() {
        // Check to see if this player is the game's player 1, and therefore 'MAX'.
        boolean isMax = this == getGame().getPlayer1();

        if (isMax) {
            return Integer.MIN_VALUE;
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
