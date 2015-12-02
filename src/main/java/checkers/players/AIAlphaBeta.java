package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.Cell;
import checkers.model.MoveChain;
import checkers.model.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *  AIAlphaBeta` is an `AI` that generates its next move chain using the minimax algorithm with
 *  alpha-beta pruning. The evaluation function for this zero-sum game is simply the the number of
 *  pieces that remain for player 1 minus the number of pieces that still remain for player 2.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class AIAlphaBeta extends AI {

    // Used to compare the number of static evaluations with other algorithms.
    private static long evalCounter = 0;

    private int depth;
    private int difficulty;

    /**
     * Creates a new AIAlphaBeta player with a maximum depth to explore of 8, and
     * a medium difficulty level.
     *
     * @param name Custom name for the player.
     */
    public AIAlphaBeta(String name) {
        super(name, true); // `true`, since there are multiple difficulty levels.
        this.depth = 8;
        this.difficulty = AI.DIFFICULTY_MEDIUM;
    }

    /**
     * Creates a new AIAlphaBeta player with the specified maximum depth to explore, and
     * a medium difficulty level.
     *
     * @param name Custom name for the player.
     * @param depth maximum depth that the algorithm should explore to.
     */
    public AIAlphaBeta(String name, int depth) {
        super(name, true);
        this.depth = depth;
        this.difficulty = AI.DIFFICULTY_MEDIUM;
    }

    /**
     * Sets the depth that the algorithm should explore to.
     *
     * @param depth the depth that the algorithm should explore to.
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Generates and returns the move chain for this AI's current turn. The
     * move chain is obtained using the minimax algorithm with alpha-beta
     * pruning, and takes into account the difficulty level.
     *
     * Difficulties and their actions:
     *  - Suicidal: always returns the worst scoring move chain.
     *  - Easy: returns one of the worst 3 scoring move chains at random.
     *  - Medium: returns one of the best 3 scoring move chains at random.
     *  - Hard: returns one of the best 2 scoring move chains at random.
     *  - Insane: always returns the best scoring move chain.
     *
     * @return the move chain for this AI's current turn.
     * @throws InvalidMoveException
     */
    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> possibleMoveChains = getGame().getMoveChains(getGame().getGameState(), this);
        List<Integer> scores = new ArrayList<Integer>(possibleMoveChains.size());

        if (!possibleMoveChains.isEmpty()) {
            // Initialise alpha and beta
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;

            // Calculate scores for each move chain.
            for (MoveChain moveChain: possibleMoveChains) {
                // Get the state from applying the move chain
                State resultState = getGame().doMoveChain(getGame().getGameState(), moveChain);
                int score = alphaBeta(resultState, depth - 1, alpha, beta, getGame().incrementTurn(this));
                scores.add(score);

                System.out.println("Move Chain:" + moveChain.getMoves().toString() + " alpha-beta score: " + score);
            }

            System.out.println("ALPHA-BETA COUNT: " + evalCounter);

            MoveChain selectedMoveChain = getDifficultyBasedMoveChain(possibleMoveChains, scores);
            System.out.println("\tSelected Chain:" + selectedMoveChain.getMoves().toString());
            return selectedMoveChain;

        }
        return null; // No possible move chains
    }

    /**
     * Implementation of the minimax algorithm with alpha-beta pruning. See the
     * pseudo-code below:
     *
     function alphabeta(node, depth, alpha, beta, player)
        if depth == 0 or node == leaf
            return the value of node

        if player == MAX
            bestValue = -∞
            for each child of node
                eval = alphabeta(child, depth - 1, alpha, beta, MIN)
                bestValue = max(bestValue, eval)
                alpha = max(aplha, bestValue)
                if alpha >= beta:
                    break // beta cutoff
            return bestValue

        if player == MIN
            bestValue = +∞
            for each child of node
                eval = alphabeta(child, depth - 1, alpha, beta,  MAX)
                bestValue = min(bestValue, eval)
                beta = min(beta, bestValue)
                if alpha >= beta:
                    break // alpha cutoff
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

    /**
     * Given a game state, returns the number of pieces that remain for player 1
     * minus the number of pieces that still remain for player 2.
     *
     * @param s game `State` to evaluate for.
     * @return the number of pieces that remain for player 1 minus the number of
     * pieces that still remain for player 2.
     */
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

        evalCounter++;
        return score;
    }

    /**
     * Given a list of `MoveChain`s, return the MoveChain that best suits
     * the specified difficulty level for this AI.
     *
     * Difficulties and their actions:
     *  - Suicidal: always returns the worst scoring move chain.
     *  - Easy: returns one of the worst 3 scoring move chains at random.
     *  - Medium: returns one of the best 3 scoring move chains at random.
     *  - Hard: returns one of the best 2 scoring move chains at random.
     *  - Insane: always returns the best scoring move chain.
     *
     * @param moveChains possible move chains.
     * @param scores scores for each possible move chain.
     * @return the MoveChain that best suits the specified difficulty level for this AI.
     */
    public MoveChain getDifficultyBasedMoveChain(List<MoveChain> moveChains, List<Integer> scores) {
        if (moveChains.isEmpty()) {
            return null;
        }

        // Sort the scores, so that the appropriate score for the difficulty can be selected
        Integer[] sortedScores = new Integer[scores.size()];
        Arrays.sort(scores.toArray(sortedScores));

        boolean isMax = this == getGame().getPlayer1();
        Random random = new Random();
        int desiredScore = sortedScores[0];

        if (difficulty == AI.DIFFICULTY_SUICIDAL) {
            // Pick the worst score
            if (isMax) {
                desiredScore = sortedScores[0];
            } else {
                desiredScore = sortedScores[sortedScores.length-1];
            }
        } else if (difficulty == AI.DIFFICULTY_EASY) {
            // Randomly pick from the worst 3 scores
            if (isMax) {
                desiredScore = sortedScores[random.nextInt(Math.min(3, sortedScores.length))];
            } else {
                desiredScore = sortedScores[sortedScores.length - (random.nextInt(Math.min(3, sortedScores.length)) + 1)];
            }
        } else if (difficulty == AI.DIFFICULTY_MEDIUM) {
            // Randomly pick from the best 3 scores
            if (isMax) {
                desiredScore = sortedScores[sortedScores.length - (random.nextInt(Math.min(3, sortedScores.length)) + 1)];
            } else {
                desiredScore = sortedScores[random.nextInt(Math.min(3, sortedScores.length))];
            }
        } else if (difficulty == AI.DIFFICULTY_HARD) {
            // Randomly pick from the best 2 scores
            if (isMax) {
                desiredScore = sortedScores[sortedScores.length - (random.nextInt(Math.min(2, sortedScores.length)) + 1)];
            } else {
                desiredScore = sortedScores[random.nextInt(Math.min(2, sortedScores.length))];
            }
        } else if (difficulty == AI.DIFFICULTY_INSANE) {
            // Pick the best score
            if (isMax) {
                desiredScore = sortedScores[sortedScores.length-1];
            } else {
                desiredScore = sortedScores[0];
            }
        }

        // Return move with desired score.
        for (int i = 0; i < moveChains.size(); i++) {
            if (scores.get(i) == desiredScore) {
                return moveChains.get(i);
            }
        }

        // No move chains in move chain list
        return null;
    }

    /**
     * Sets the difficulty level that the AI should play at.
     *
     * @param difficulty difficulty level that the AI should play at.
     */
    public void setDifficulty(int difficulty) {
        this.setDepth(8);
        this.difficulty = difficulty;
    }
}
