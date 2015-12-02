package checkers.players;

import checkers.model.Game;

/**
 * `Player` represents any checkers player, either human or AI. Implementing this class directly
 * results in a human player that must make moves manually. For AI players, extend the AI class
 * and implement the `nextMoveChain()` method.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class Player {
    private String name;
    private Game game;

    /**
     * @param name Custom name for the player.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Returns the given name for the player.
     *
     * @return the given name for the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the game object associated with this player.
     *
     * @param game the game object associated with this player.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Returns the `Game` object associated with this player.
     *
     * @return the `Game` object associated with this player.
     */
    public Game getGame() {
        return this.game;
    }
}
