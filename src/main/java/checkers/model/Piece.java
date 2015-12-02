package checkers.model;

import checkers.players.Player;

/**
 * `Piece` is the data structure for a checkers piece in the game, storing the reference to the
 * player that owns the piece, and whether the piece has been crowned.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class Piece implements Cloneable {
    private boolean crowned;
    private final Player player;

    /**
     * Creates a new Piece that is owned by the specified player, and is not crowned.
     * @param player
     */
    public Piece(Player player) {
        this.crowned = false;
        this.player = player;
    }

    /**
     * Crowns the piece (turns it into a king)
     */
    public void crown() {
        this.crowned = true;
    }

    /**
     * Returns whether this piece has been crowned.
     *
     * @return `true` if this piece has been crowned, `false` otherwise.
     */
    public boolean isCrowned() {
        return this.crowned;
    }

    /**
     * Returns the player that "owns" this piece.
     *
     * @return the `Player` that is the owner of this piece.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns a `String` representation of this piece (lowercase player
     * initial for a normal piece, uppercase player initial for a crowned piece).
     *
     * @return a `String` representation of this piece.
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
     * Clones and returns this piece. The associated `Player` is not cloned.
     *
     * @return the cloned `Piece`.
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
