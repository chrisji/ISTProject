package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

import java.util.List;
import java.util.Random;

/**
 * `RandomAI` is an `AI` that generates the set of valid move chains for its turn
 * and picks one at random.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class RandomAI extends AI {

    private Random rand;

    /**
     * @param name Custom name for the player.
     */
    public RandomAI(String name) {
        super(name, false); // `false`, since there are not multiple difficulty levels.
        this.rand = new Random();
    }

    /**
     * Obtains all valid move chains for this turn, and returns one at random.
     *
     * @return a random valid `MoveChain`, or `null` if no valid moves are possible.
     * @throws InvalidMoveException
     */
    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> moveChains = getGame().getMoveChains(getGame().getGameState(), this);

        // Return a random valid move chain, if one exists.
        if (!moveChains.isEmpty()) {
            return moveChains.get(rand.nextInt(moveChains.size()));
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
