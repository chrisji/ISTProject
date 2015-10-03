package checkers;

/**
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class Move {
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    private boolean isTake;

    public Move(int fromRow, int fromCol, int toRow, int toCol, boolean isTake) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isTake = isTake;
    }
    
    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public boolean isTake() {
        return isTake;
    }

    public String toString() {
        return String.format("(%d, %d) -> (%d, %d) take=%b", fromRow, fromCol, toRow, toCol, isTake);
    }
}
