package checkers.players;

import checkers.model.Game;

/**
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class Player {
    private String name;
    private Game game;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }
}
