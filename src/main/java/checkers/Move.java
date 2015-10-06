package checkers;

/**
 * Data structure representing a `move` (a transition from one cell on the board to another).
 *
 * This class also contains helper functions for comparing `Move` equality, checking to see if this move
 * was a capturing move, and obtaining the co-ordinates of the piece that this move captured (in the case
 * of a capturing move).
 *
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class Move {
    private final int fromRow;
    private final int fromCol;
    private final int toRow;
    private final int toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    /**
     * Returns the row that the move is transitioning the piece from.
     *
     * @return the row that the move is transitioning the piece from.
     */
    public int getFromRow() {
        return fromRow;
    }

    /**
     * Returns the column that the move is transitioning the piece from.
     *
     * @return the column that the move is transitioning the piece from.
     */
    public int getFromCol() {
        return fromCol;
    }

    /**
     * Returns the row that the move is transitioning the piece to.
     *
     * @return the row that the move is transitioning the piece to.
     */
    public int getToRow() {
        return toRow;
    }

    /**
     * Returns the column that the move is transitioning the piece to.
     *
     * @return the column that the move is transitioning the piece to.
     */
    public int getToCol() {
        return toCol;
    }

    /**
     * Returns true if this move has resulted in a piece being captured, false otherwise.
     *
     * @return true if this is a capturing move, false otherwise
     */
    public boolean isTake() {
        return (Math.abs(fromRow - toRow) > 1) && (Math.abs(fromCol - toCol) > 1);
    }

    /**
     * Returns the row of the piece that was taken by performing this move.
     *
     * @return the row of the piece that was taken by performing this move.
     */
    public int calcTakenRow() {
        return (fromRow + toRow) / 2;
    }

    /**
     * Returns the column of the piece that was taken by performing this move.
     *
     * @return the column of the piece that was taken by performing this move.
     */
    public int calcTakenCol() {
        return (fromCol + toCol) / 2;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) -> (%d, %d) isTake=%b", fromRow, fromCol, toRow, toCol, isTake());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Move move = (Move) o;

        if (fromRow != move.getFromRow() || fromCol != move.getFromCol()) {
            return false;
        }

        if (toRow != move.getToRow() || toCol != move.getToCol()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromRow;
        result = 31 * result + fromCol;
        result = 31 * result + toRow;
        result = 31 * result + toCol;
        return result;
    }
}
