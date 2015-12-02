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
 * `AIMiniMax` is an `AI` that generates its next move chain using the minimax algorithm. For this
 * algorithm no tree pruning is performed (see AIAlphaBeta for minimax with alpha-beta pruning).
 *
 * @author 144158
 * @version 02/12/2015
 */
public class AIMiniMax extends AI {

    // Used to compare the number of static evaluations with other algorithms.
    private static long evalCounter = 0;

    private int depth;
    private int difficulty;

    /**
     * Creates a new AIMiniMax player with a maximum depth to explore of 6, and
     * a medium difficulty level.
     *
     * @param name Custom name for the player.
     */
    public AIMiniMax(String name) {
        super(name, true); // `true`, since there are multiple difficulty levels.
        this.depth = 6;
        this.difficulty = AI.DIFFICULTY_MEDIUM;
    }

    /**
     * Creates a new AIMiniMax player with the specified maximum depth to explore, and
     * a medium difficulty level.
     *
     * @param name Custom name for the player.
     * @param depth maximum depth that the algorithm should explore to.
     */
    public AIMiniMax(String name, int depth) {
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
     * move chain is obatined using the minimax algorithm with no pruning, and
     * takes into account the difficulty level.
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
            // Calculate scores for each move chain.
            for (MoveChain moveChain: possibleMoveChains) {
                // Get the state from applying the move chain
                State resultState = getGame().doMoveChain(getGame().getGameState(), moveChain);
                int score = minimax(resultState, depth-1, getGame().incrementTurn(this));
                scores.add(score);

                System.out.println("Move Chain:" + moveChain.getMoves().toString() + " minimax score: " + score);
            }

            System.out.println("MINIMAX COUNT: " + evalCounter);

            MoveChain selectedMoveChain = getDifficultyBasedMoveChain(possibleMoveChains, scores);
            System.out.println("\tSelected Chain:" + selectedMoveChain.getMoves().toString());
            return selectedMoveChain;
        }

        return null; // No possible move chains
    }

    /**
     *
     * TODO
     *
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

    /**
     * TODO
     * @param s
     * @return
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

        evalCounter++; // TODO: remove debugging
        return score;
    }

    /**
     * TODO
     *
     * @param value1
     * @param value2
     * @return
     */
    public int getBestValue(int value1, int value2) {
        boolean isMax = this == getGame().getPlayer1();

        if (isMax) {
            return Math.max(value1, value2);
        } else {
            return Math.min(value1, value2);
        }
    }

    /**
     * TODO
     *
     * @return
     */
    public int getWorstValue() {
        boolean isMax = this == getGame().getPlayer1();

        if (isMax) {
            return Integer.MIN_VALUE;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * TODO
     *
     * @param moveChains
     * @param scores
     * @return
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

        System.out.println("Desired score for difficulty=" + difficulty + " is " + desiredScore);

        // Return move with desired score.
        for (int i = 0; i < moveChains.size(); i++) {
            if (scores.get(i) == desiredScore) {
                return moveChains.get(i);
            }
        }

        return null;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
