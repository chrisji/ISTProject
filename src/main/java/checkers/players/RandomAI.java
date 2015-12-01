package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.MoveChain;

import java.util.List;
import java.util.Random;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class RandomAI extends AI {

    private Random rand;

    public RandomAI(String name) {
        super(name, false);
        this.rand = new Random();
    }

    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> moveChains = getGame().getMoveChains(getGame().getGameState(), this);

        // Return a random valid move chain, if one exists.
        if (!moveChains.isEmpty()) {
            return moveChains.get(rand.nextInt(moveChains.size()));
        }

        // No possible moves.
        return null;
    }

    public void setDifficulty(int difficultyLevel) {
        // No-op
    }
}
