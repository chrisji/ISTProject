package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

import java.util.List;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public class AI extends Player {

    public AI(String name) {
        super(name);
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
}
