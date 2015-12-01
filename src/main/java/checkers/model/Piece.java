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

    /**
     * TODO
     */
    public void crown() {
        this.crowned = true;
    }

    /**
     * TODO
     * @return
     */
    public boolean isCrowned() {
        return this.crowned;
    }

    /**
     * TODO
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * TODO
     */
    @Override
    public String toString() {
        String pieceInitial = getPlayer().getName().substring(0, 1);

        if (isCrowned()) {
            return pieceInitial.toUpperCase();
        } else {
            return pieceInitial.toLowerCase();
        }
    }

    /**
     * TODO
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Piece clone() throws CloneNotSupportedException {
        Piece p = new Piece(player);

        if (isCrowned()) {
            p.crown();
        }

        return p;
    }
}
