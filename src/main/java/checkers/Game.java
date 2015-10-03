package checkers;

import checkers.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class Game {
    private final Cell[][] board;
    private static final int ROWS = 8;
    private static final int COLS = 8;

    private Player player1;
    private Player player2;

    private Player currentTurn;
    private Piece currentPiece;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.board = new Cell[ROWS][COLS];
        this.currentTurn = player1;
        this.currentPiece = null;

        setupBoard();
    }

    private void setupBoard() {
        // Init board with empty cells
        for (int i=0; i < Game.ROWS; i++) {
            for (int j=0; j < Game.COLS; j++) {
                board[i][j] = new Cell();
            }
        }

        // Add pieces to the board
        setupPlayer1();
        setupPlayer2();
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void incrementTurn() {
        currentPiece = null;
        currentTurn = currentTurn == player1 ? player2 : player1;
    }

    public void movePiece(Player player, Move move) throws InvalidMoveException {
        // Ensure the move is valid
        validateMove(player, move);

        // Move piece, and mark as the current piece being used for this turn
        currentPiece = board[move.getFromRow()][move.getFromCol()].removeContents();
        board[move.getToRow()][move.getToCol()].setContents(currentPiece);

        // Remove taken piece from board
        if (move.isTake()) {
            board[move.calcTakenRow()][move.calcTakenCol()].removeContents();

            // Increment turn if play has no more valid moves
            if (player == player1 && getValidPlayer1Moves().isEmpty()) {
                incrementTurn();
            } else if (player == player2 && getValidPlayer2Moves().isEmpty()) {
                incrementTurn();
            }
        } else {
            incrementTurn();
        }
    }

    public List<Move> getValidPlayer1Moves() {
        List<Move> nonCapturingMoves = new ArrayList<Move>();
        List<Move> capturingMoves = new ArrayList<Move>();

        for (int i=0; i < ROWS; i++) {
            for (int j=0; j < COLS; j++) {
                // Check piece exists, belongs to the player and is either the players first move for this turn or they are moving the same piece on their turn.
                if (!board[i][j].isEmpty() && board[i][j].getContents().getPlayer() == player1 && (currentPiece == null || board[i][j].getContents() == currentPiece)) {

                    // If piece has already been moved, disallow non-capturing move
                    if (currentPiece == null) {
                        // Moving down and right
                        if (i+1 < ROWS && j+1 < COLS && board[i+1][j+1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i + 1, j + 1));
                        }

                        // Moving down and left
                        if (i+1 < ROWS && j-1 >= 0 && board[i+1][j-1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i + 1, j - 1));
                        }
                    }

                    // Moving down and right by taking
                    if (i+2 < ROWS && j+2 < COLS && board[i+2][j+2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i+1][j+1].isEmpty() && board[i+1][j+1].getContents().getPlayer() == player2) {
                            capturingMoves.add(new Move(i, j, i + 2, j + 2));
                        }
                    }

                    // Moving down and left by taking
                    if (i+2 < ROWS && j-2 >= 0 && board[i+2][j-2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i+1][j-1].isEmpty() && board[i+1][j-1].getContents().getPlayer() == player2) {
                            capturingMoves.add(new Move(i, j, i + 2, j - 2));
                        }
                    }

                    // Backwards rules for king pieces
                    if (board[i][j].getContents().isCrowned()) {
                        // If piece has already been moved, disallow non-capturing move
                        if (currentPiece == null) {
                            // Moving up and right
                            if (i-1 >= 0 && j + 1 < COLS && board[i-1][j+1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i - 1, j + 1));
                            }

                            // Moving up and left
                            if (i-1 >= 0 && j-1 >= 0 && board[i-1][j-1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i - 1, j - 1));
                            }
                        }

                        // Moving up and right by taking
                        if (i-2 >= 0 && j+2 < COLS && board[i-2][j+2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i-1][j+1].isEmpty() && board[i-1][j+1].getContents().getPlayer() == player2) {
                                capturingMoves.add(new Move(i, j, i - 2, j + 2));
                            }
                        }

                        // Moving up and left by taking
                        if (i-2 >= 0 && j-2 >= 0 && board[i-2][j-2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i-1][j-1].isEmpty() && board[i-1][j-1].getContents().getPlayer() == player2) {
                                capturingMoves.add(new Move(i, j, i - 2, j - 2));
                            }
                        }
                    }
                }
            }
        }

        // If capturing moves exist, then any non-capturing moves are invalid.
        if (!capturingMoves.isEmpty()) {
            return capturingMoves;
        }
        return nonCapturingMoves;
    }

    public List<Move> getValidPlayer2Moves() {
        List<Move> nonCapturingMoves = new ArrayList<Move>();
        List<Move> capturingMoves = new ArrayList<Move>();

        for (int i=0; i < ROWS; i++) {
            for (int j=0; j < COLS; j++) {

                // Check piece exists, belongs to the player and is either the player's first move for this turn or they are moving the same piece on their turn.
                if (!board[i][j].isEmpty() && board[i][j].getContents().getPlayer() == player2 && (currentPiece == null || board[i][j].getContents() == currentPiece)) {

                    // If piece has already been moved, disallow non-capturing move
                    if (currentPiece == null) {
                        // Moving up and right
                        if (i-1 >= 0 && j+1 < COLS && board[i-1][j+1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i - 1, j + 1));
                        }

                        // Moving up and left
                        if (i-1 >= 0 && j-1 >= 0 && board[i-1][j-1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i - 1, j - 1));
                        }
                    }

                    // Moving up and right by taking
                    if (i-2 >= 0 && j+2 < COLS && board[i-2][j+2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i-1][j+1].isEmpty() && board[i-1][j+1].getContents().getPlayer() == player1) {
                            capturingMoves.add(new Move(i, j, i - 2, j + 2));
                        }
                    }

                    // Moving up and left by taking
                    if (i-2 >= 0 && j-2 >= 0 && board[i-2][j-2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i-1][j-1].isEmpty() && board[i-1][j-1].getContents().getPlayer() == player1) {
                            capturingMoves.add(new Move(i, j, i - 2, j - 2));
                        }
                    }

                    // Backwards rules for king pieces
                    if (board[i][j].getContents().isCrowned()) {
                        // If piece has already been moved, disallow non-capturing move
                        if (currentPiece == null) {
                            // Moving down and right
                            if (i+1 < ROWS && j+1 < COLS && board[i+1][j+1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i + 1, j + 1));
                            }

                            // Moving down and left
                            if (i+1 < ROWS && j-1 >= 0 && board[i+1][j-1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i + 1, j - 1));
                            }
                        }

                        // Moving down and right by taking
                        if (i+2 < ROWS && j+2 < COLS && board[i+2][j+2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i+1][j+1].isEmpty() && board[i+1][j+1].getContents().getPlayer() == player1) {
                                capturingMoves.add(new Move(i, j, i + 2, j + 2));
                            }
                        }

                        // Moving down and left by taking
                        if (i+2 < ROWS && j-2 >= 0 && board[i+2][j-2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i+1][j-1].isEmpty() && board[i+1][j-1].getContents().getPlayer() == player1) {
                                capturingMoves.add(new Move(i, j, i + 2, j - 2));
                            }
                        }
                    }
                }
            }
        }

        // If capturing moves exist, then any non-capturing moves are invalid.
        if (!capturingMoves.isEmpty()) {
            return capturingMoves;
        }
        return nonCapturingMoves;
    }

    // TODO: refactor this shit!
    private void setupPlayer1() {
        this.board[0][1].setContents(new Piece(player1));
        this.board[0][3].setContents(new Piece(player1));
        this.board[0][5].setContents(new Piece(player1));
        this.board[0][7].setContents(new Piece(player1));
        this.board[1][0].setContents(new Piece(player1));
        this.board[1][2].setContents(new Piece(player1));
        this.board[1][4].setContents(new Piece(player1));
        this.board[1][6].setContents(new Piece(player1));
        this.board[2][1].setContents(new Piece(player1));
        this.board[2][3].setContents(new Piece(player1));
        this.board[2][5].setContents(new Piece(player1));
        this.board[2][7].setContents(new Piece(player1));
    }

    // TODO: refactor this shit!
    private void setupPlayer2() {
        this.board[5][0].setContents(new Piece(player2));
        this.board[5][2].setContents(new Piece(player2));
        this.board[5][4].setContents(new Piece(player2));
        this.board[5][6].setContents(new Piece(player2));
        this.board[6][1].setContents(new Piece(player2));
        this.board[6][3].setContents(new Piece(player2));
        this.board[6][5].setContents(new Piece(player2));
        this.board[6][7].setContents(new Piece(player2));
        this.board[7][0].setContents(new Piece(player2));
        this.board[7][2].setContents(new Piece(player2));
        this.board[7][4].setContents(new Piece(player2));
        this.board[7][6].setContents(new Piece(player2));
    }

    /**
     * Visual Debugging.
     *
     * Prints the board out to the console. E.g.
     *  --- --- --- --- --- --- --- ---
     * |   | c |   | c |   | c |   | c |
     *  --- --- --- --- --- --- --- ---
     * | c |   | c |   | c |   | c |   |
     *  --- --- --- --- --- --- --- ---
     * |   | c |   | c |   | c |   | c |
     *  --- --- --- --- --- --- --- ---
     * |   |   |   |   |   |   |   |   |
     *  --- --- --- --- --- --- --- ---
     * |   |   |   |   |   |   |   |   |
     *  --- --- --- --- --- --- --- ---
     * | a |   | a |   | a |   | a |   |
     *  --- --- --- --- --- --- --- ---
     * |   | a |   | a |   | a |   | a |
     *  --- --- --- --- --- --- --- ---
     * | a |   | a |   | a |   | a |   |
     *  --- --- --- --- --- --- --- ---
     */
    public void printBoard() {
        // Top numbers
        System.out.print("  ");
        for (int i=0; i < COLS; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println();


        for (int i=0; i < ROWS; i++) {
            System.out.println("   --- --- --- --- --- --- --- ---  ");
            System.out.print(i + " | "); // Left side numbers

            for (int j=0; j < COLS; j++) {
                if (board[i][j].getContents() != null) {
                    System.out.print(board[i][j].getContents() + " | ");
                } else {
                    System.out.print(" " + " | ");
                }
            }
            System.out.println();
        }
        System.out.println("   --- --- --- --- --- --- --- ---  ");
    }

    private void validateMove(Player player, Move move) throws InvalidMoveException {
        // Ensure it's the players turn to make a move
        if (currentTurn != player) {
            throw new InvalidMoveException("It is not " + player.getName() + "'s turn");
        }

        // Ensure the old cell is not out-of-bounds
        if (move.getFromRow() < 0 || move.getFromRow() >= ROWS || move.getFromCol() < 0 || move.getFromCol() >= COLS) {
            throw new InvalidMoveException("Cannot move a piece from outside the board's dimensions");
        }

        // Ensure the new cell is not out-of-bounds
        if (move.getToRow() < 0 || move.getToRow() >= ROWS || move.getToCol() < 0 || move.getToCol() >= COLS) {
            throw new InvalidMoveException("Cannot move a piece to outside the board's dimensions");
        }

        // Ensure the old cell is not empty
        if (this.board[move.getFromRow()][move.getFromCol()].isEmpty()) {
            throw new InvalidMoveException("There is no piece in cell [" + move.getFromRow() + ", " + move.getFromCol() + "]");
        }

        // Ensure the new cell is empty
        if (!this.board[move.getToRow()][move.getToCol()].isEmpty()) {
            throw new InvalidMoveException("There is already a piece in cell [" + move.getToRow() + ", " + move.getToCol() + "]");
        }

        // Ensure piece to be moved is owned by the player
        if (this.board[move.getFromRow()][move.getFromCol()].getContents().getPlayer() != player) {
            throw new InvalidMoveException("The piece attempting to be moved is not owned by the current player");
        }
    }
}
