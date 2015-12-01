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

    public State(Cell[][] board, Player currentPlayer, Piece currentPiece) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.currentPiece = currentPiece;
    }

    public Player getTurn() {
        return this.currentPlayer;
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public Piece getCurrentPiece() {
        return this.currentPiece;
    }
}
