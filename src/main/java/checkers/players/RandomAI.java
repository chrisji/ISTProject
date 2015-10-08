package checkers.players;

import checkers.Game;
import checkers.Move;

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

    public Move nextMove(Game game) {
        List<Move> moves = game.getValidMoves(this);
        return moves.get(rand.nextInt(moves.size()));
    }
}
