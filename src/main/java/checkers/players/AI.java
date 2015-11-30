package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public abstract class AI extends Player {

    public AI(String name) {
        super(name);
    }

    public abstract MoveChain nextMoveChain() throws InvalidMoveException;

    public abstract void setDifficulty(int difficultyLevel);
}
