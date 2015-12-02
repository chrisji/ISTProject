package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

/**
 * Abstract class that all AI players must extend. Contains a common interface for obtaining
 * the next move that the AI will make, and setting difficulty levels.
 *
 * @author 144158
 * @version 02/12/2015
 */
public abstract class AI extends Player {

    // Difficulty levels
    public static final int DIFFICULTY_SUICIDAL = 0;
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;
    public static final int DIFFICULTY_INSANE = 4;

    // Flag to determine whether this AI has different difficulties.
    private boolean hasVariableDifficulty;

    /**
     * @param name Custom name for the player.
     * @param hasVariableDifficulty `true` if the AI has multiple difficulty levels, `false` otherwise.
     */
    public AI(String name, boolean hasVariableDifficulty) {
        super(name);
        this.hasVariableDifficulty = hasVariableDifficulty;
    }

    /**
     * Returns `true` if the AI has variable difficulty levels, `false` otherwise.
     *
     * @return `true` if the AI has variable difficulty levels, `false` otherwise.
     */
    public boolean hasVariableDifficulty() {
        return this.hasVariableDifficulty;
    }

    /**
     * Sets the difficulty level that the AI should play at.
     *
     * @param difficultyLevel difficulty level that the AI should play at.
     */
    public abstract void setDifficulty(int difficultyLevel);

    /**
     * Calculates and returns the chain of moves of the current turn.
     *
     * @return the chain of moves of the current turn.
     * @throws InvalidMoveException if the AI simulates moves that may have been invalid.
     */
    public abstract MoveChain nextMoveChain() throws InvalidMoveException;
}
