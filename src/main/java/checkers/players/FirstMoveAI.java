package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

import java.util.List;

/**
 * TODO
 * @author 144158
 * @version 02/12/2015
 */
public class FirstMoveAI extends AI {

    /**
     * TODO
     * @param name
     */
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
