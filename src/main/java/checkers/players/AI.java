package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

/**
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

    // TODO
    private boolean hasVariableDifficulty;

    /**
     * TODO
     *
     * @param name
     * @param hasVariableDifficulty
     */
    public AI(String name, boolean hasVariableDifficulty) {
        super(name);
        this.hasVariableDifficulty = hasVariableDifficulty;
    }

    /**
     * TODO
     *
     * @return
     */
    public boolean hasVariableDifficulty() {
        return this.hasVariableDifficulty;
    }

    /**
     * TODO
     * @param difficultyLevel
     */
    public abstract void setDifficulty(int difficultyLevel);

    /**
     * TODO
     * @return
     * @throws InvalidMoveException
     */
    public abstract MoveChain nextMoveChain() throws InvalidMoveException;
}
