package checkers.model;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class Cell implements Cloneable {
    private Piece piece;

    public Cell() {
        this.piece = null;
    }

    public Cell(Piece piece) {
        this.piece = piece;
    }

    public Piece getContents() {
        return piece;
    }

    public void setContents(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Piece removeContents() {
        Piece p = this.piece;
        this.piece = null;
        return p;
    }

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
