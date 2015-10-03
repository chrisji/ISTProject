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

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
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
        // Returns true if there was a jump
        return (Math.abs(fromRow - toRow) > 1) && (Math.abs(fromCol - toCol) > 1);
    }

    public String toString() {
        return String.format("(%d, %d) -> (%d, %d) take=%b", fromRow, fromCol, toRow, toCol, isTake());
    }

    public int calcTakenRow() {
        if (fromRow < toRow) {
            return fromRow + 1;
        }
        return fromRow - 1;
    }

    public int calcTakenCol() {
        if (fromCol < toCol) {
            return fromCol + 1;
        }
        return fromCol - 1;
    }
}
