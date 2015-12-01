package checkers.model;

/**
 * TODO
 *
 * @author 144158
 * @version 02/12/2015
 */
public class Cell implements Cloneable {
    private Piece piece;

    /**
     * TODO
     */
    public Cell() {
        this.piece = null;
    }

    /**
     * TODO
     * @param piece
     */
    public Cell(Piece piece) {
        this.piece = piece;
    }

    /**
     * TODO
     * @return
     */
    public Piece getContents() {
        return piece;
    }

    /**
     * TODO
     * @param piece
     */
    public void setContents(Piece piece) {
        this.piece = piece;
    }

    /**
     * TODO
     *
     * @return
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * TODO
     * @return
     */
    public Piece removeContents() {
        Piece p = this.piece;
        this.piece = null;
        return p;
    }

    /**
     * TODO
     *
     * @return
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
