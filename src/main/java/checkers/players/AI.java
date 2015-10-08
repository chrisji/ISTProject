package checkers.players;

import checkers.Game;
import checkers.Move;

import java.util.Random;

/**
 * @author Chris Inskip
 * @version 08/10/2015
 */
public class AI extends Player {

    private Random rand;

    public AI(String name) {
        super(name);
        this.rand = new Random();
    }

    public Move nextMove(Game game) {
        return game.getValidMoves(this).get(0);
    }
}
