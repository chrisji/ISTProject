package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

import java.util.List;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public class FirstMoveAI extends AI {

    public FirstMoveAI(String name) {
        super(name, false);
    }

    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> moveChains = getGame().getMoveChains(getGame().getGameState(), this);

        // Return the first valid move chain, if one exists.
        if (!moveChains.isEmpty()) {
            return moveChains.get(0);
        }

        // No possible moves.
        return null;
    }

    public void setDifficulty(int difficultyLevel) {
        // No-op
    }
}
