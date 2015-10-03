package checkers;

/**
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class Cell {
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
}
