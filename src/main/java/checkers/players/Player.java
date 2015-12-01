package checkers.players;

import checkers.model.Game;

/**
 * TODO
 * @author 144158
 * @version 02/12/2015
 */
public class Player {
    private String name;
    private Game game;

    /**
     * TODO
     * @param name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * TODO
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * TODO
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * TODO
     * @return
     */
    public Game getGame() {
        return this.game;
    }
}
