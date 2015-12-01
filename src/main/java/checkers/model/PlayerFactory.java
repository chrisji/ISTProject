package checkers.model;

import checkers.players.AIAlphaBeta;
import checkers.players.AIMiniMax;
import checkers.players.FirstMoveAI;
import checkers.players.Player;
import checkers.players.RandomAI;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class PlayerFactory {

    public static final int HUMAN = 0;
    public static final int MINIMAX_AI = 1;
    public static final int ALPHA_BETA_AI = 2;
    public static final int FIRST_MOVE_AI = 3;
    public static final int RANDOM_MOVE_AI = 4;

    public static Player buildPlayer(int player, String name) {
        if (player == HUMAN) {
            return new Player(name);
        } else if (player == MINIMAX_AI) {
            return new AIMiniMax(name);
        } else if (player == ALPHA_BETA_AI) {
            return new AIAlphaBeta(name);
        } else if (player == FIRST_MOVE_AI) {
            return new FirstMoveAI(name);
        } else if (player == RANDOM_MOVE_AI) {
            return new RandomAI(name);
        }
        return null;
    }
}
