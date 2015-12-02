package checkers.model;

import checkers.players.Player;

/**
 * `State` represents an immutable state of the game. This data structure stores the
 * board, current player, and the piece that the current player is moving in the case
 * of multi-jump moves.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class State {
    private Cell[][] board;
    private Player currentPlayer;
    private Piece currentPiece;

    /**
     * Creates a new `State` with the specified board, current player, and current piece.
     *
     * @param board the current board
     * @param currentPlayer the player that is currently making a move.
     * @param currentPiece the piece that is currently being moved in the case of multi-jump moves.
     */
    public State(Cell[][] board, Player currentPlayer, Piece currentPiece) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.currentPiece = currentPiece;
    }

    /**
     * Returns the player who's currently allowed to make moves.
     *
     * @return the player who's currently allowed to make moves.
     */
    public Player getTurn() {
        return this.currentPlayer;
    }

    /**
     * Returns the board (2D array of cells) for this state.
     *
     * @return the board (2D array of `Cell`s) for this state.
     */
    public Cell[][] getBoard() {
        return this.board;
    }

    /**
     * Returns the current piece being moved in the case of multi-jump moves.
     *
     * @return the current `Piece` being moved, or `null` if no piece is being moved.
     */
    public Piece getCurrentPiece() {
        return this.currentPiece;
    }
}
