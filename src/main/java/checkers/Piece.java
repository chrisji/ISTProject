package checkers;

import checkers.players.Player;

/**
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class Piece {
    private boolean crowned;
    private Player player;

    public Piece(Player player) {
        this.crowned = false;
        this.player = player;
    }

    public void crown() {
        this.crowned = true;
    }

    public boolean isCrowned() {
        return this.crowned;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String toString() {
        String pieceInitial = getPlayer().getName().substring(0, 1);

        if (isCrowned()) {
            return pieceInitial.toUpperCase();
        } else {
            return pieceInitial.toLowerCase();
        }
    }
}
