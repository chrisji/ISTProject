package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

import java.util.List;

/**
 * `FirstMoveAI` is an `AI` that generates the set of valid move chains for its turn
 * and always picks the first move chain that it generates.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class FirstMoveAI extends AI {

    /**
     * @param name Custom name for the player.
     */
    public FirstMoveAI(String name) {
        super(name, false); // `false`, since there are not multiple difficulty levels.
    }

    /**
     * Obtains all valid move chains for this turn, and returns the first one.
     *
     * @return the first valid `MoveChain` found, or `null` if no valid moves are possible.
     * @throws InvalidMoveException
     */
    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> moveChains = getGame().getMoveChains(getGame().getGameState(), this);

        // Return the first valid move chain, if one exists.
        if (!moveChains.isEmpty()) {
            return moveChains.get(0);
        }

        // No possible moves.
        return null;
    }

    /**
     * Sets the difficulty level that this AI should play at.
     *
     * @param difficultyLevel the difficulty level that the AI should play at.
     */
    public void setDifficulty(int difficultyLevel) {
        // No-op, since this AI has no variable difficulty.
    }
}
