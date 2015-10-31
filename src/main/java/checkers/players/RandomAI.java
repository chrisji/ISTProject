package checkers.players;

import checkers.exceptions.InvalidMoveException;
import checkers.model.Move;
import checkers.model.MoveChain;

import java.util.List;
import java.util.Random;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public class RandomAI extends AI {

    private Random rand;

    public RandomAI(String name) {
        super(name, 0);
        this.rand = new Random();
    }

    public MoveChain nextMoveChain() throws InvalidMoveException {
        List<MoveChain> moveChains = getGame().getMoveChains(getGame().getGameState(), this);
        return moveChains.get(rand.nextInt(moveChains.size()));
    }
}
