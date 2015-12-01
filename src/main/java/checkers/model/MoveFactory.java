package checkers.model;

import checkers.exceptions.InvalidMoveException;

/**
 * @author 144158
 * @version 01/11/2015
 */
public class MoveFactory {

    // [fromRow][fromCol][toRow][toCol]
    private static Move[][][][] moves = new Move[8][8][8][8];

    // Used to keep references to the same move, to save memory.
    public static Move buildMove(int fromRow, int fromCol, int toRow, int toCol) throws InvalidMoveException {
        if (fromRow > moves.length || fromCol > moves[0].length) {
            throw new InvalidMoveException("Attempting to move from out of bound location");
        }

        if (toRow > moves[0][0].length || toCol > moves[0][0][0].length) {
            throw new InvalidMoveException("Attempting to move to out of bound location");
        }

        if (moves[fromRow][fromCol][toRow][toCol] == null) {
            Move move = new Move(fromRow, fromCol, toRow, toCol);
            moves[fromRow][fromCol][toRow][toCol] = move;
        }

        return moves[fromRow][fromCol][toRow][toCol];
    }
}
