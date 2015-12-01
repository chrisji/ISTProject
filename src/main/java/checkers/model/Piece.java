package checkers.model;

import checkers.players.Player;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class Piece implements Cloneable {
    private boolean crowned;
    private final Player player;

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

    @Override
    protected Piece clone() throws CloneNotSupportedException {
        Piece p = new Piece(player);

        if (isCrowned()) {
            p.crown();
        }

        return p;
    }
}
