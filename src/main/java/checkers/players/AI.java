package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public abstract class AI extends Player {

    // Difficulty levels
    public static final int DIFFICULTY_SUICIDAL = 0;
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;
    public static final int DIFFICULTY_INSANE = 4;

    private boolean hasVariableDifficulty;

    public AI(String name, boolean hasVariableDifficulty) {
        super(name);
        this.hasVariableDifficulty = hasVariableDifficulty;
    }

    public boolean hasVariableDifficulty() {
        return this.hasVariableDifficulty;
    }

    public abstract void setDifficulty(int difficultyLevel);

    public abstract MoveChain nextMoveChain() throws InvalidMoveException;
}
