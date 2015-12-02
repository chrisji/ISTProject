package checkers.model;

/**
 * `Cell` represents a square on the board. This cell can either be empty, or
 * contain a checkers piece.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class Cell implements Cloneable {
    private Piece piece;

    /**
     * Creates an empty `Cell`.
     */
    public Cell() {
        this.piece = null;
    }

    /**
     * Create a `Cell` that contains the specified piece.
     * @param piece
     */
    public Cell(Piece piece) {
        this.piece = piece;
    }

    /**
     * Returns the `Piece` that is occupying this cell.
     * @return the `Piece` that is occupying this cell, or `null` if empty.
     */
    public Piece getContents() {
        return piece;
    }

    /**
     * Sets the contents of the cell to the specified piece.
     *
     * @param piece `Piece` to add to this cell.
     */
    public void setContents(Piece piece) {
        this.piece = piece;
    }

    /**
     * Returns `true` if this cell does not contain a piece, `false` otherwise.
     *
     * @return `true` if this cell does not contain a piece, `false` otherwise.
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Removes and returns the piece contained in this cell.
     *
     * @return the piece contained in this cell, and `null` if no piece exists.
     */
    public Piece removeContents() {
        Piece p = this.piece;
        this.piece = null;
        return p;
    }

    /**
     * Clones the current cell AND the piece that it contains.
     *
     * @return the cloned cell.
     * @throws CloneNotSupportedException
     */
    @Override
    protected Cell clone() throws CloneNotSupportedException {
        super.clone();

        if (piece == null) {
            return new Cell();
        } else {
            return new Cell(piece.clone());
        }
    }
}
