package checkers.players;

import checkers.model.Move;

import java.util.List;
import java.util.Random;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public class RandomAI extends AI {

    private Random rand;

    public RandomAI(String name) {
        super(name);
        this.rand = new Random();
    }

    public Move nextMove() {
        List<Move> moves = getGame().getValidMoves(getGame().getGameState());
        return moves.get(rand.nextInt(moves.size()));
    }
}
