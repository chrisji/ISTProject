package checkers.model;

import checkers.players.Player;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class State {
    private Cell[][] board;
    private Player currentPlayer;
    private Piece currentPiece;

    /**
     * TODO
     * @param board
     * @param currentPlayer
     * @param currentPiece
     */
    public State(Cell[][] board, Player currentPlayer, Piece currentPiece) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.currentPiece = currentPiece;
    }

    /**
     * TODO
     * @return
     */
    public Player getTurn() {
        return this.currentPlayer;
    }

    /**
     * TODO
     * @return
     */
    public Cell[][] getBoard() {
        return this.board;
    }

    /**
     * TODO
     * @return
     */
    public Piece getCurrentPiece() {
        return this.currentPiece;
    }
}
