package checkers;

import checkers.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class Game {
    private final Cell[][] board;
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int NUM_PIECES = 12;

    private Player p1;
    private Player p2;

    private Player currentTurn;

    public Game(Player player1, Player player2) {
        this.p1 = player1;
        this.p2 = player2;

        this.board = new Cell[ROWS][COLS];
        this.currentTurn = player1;

        setupBoard();
    }

    private void setupBoard() {
        // Init board with empty cells
        for (int i=0; i < Game.ROWS; i++) {
            for (int j=0; j < Game.COLS; j++) {
                this.board[i][j] = new Cell();
            }
        }

        // Add player 1 pieces to board
        setupPlayer1();

        // Add player 2 pieces to board
        setupPlayer2();
    }

    public Player getCurrentTurn() {
        return this.currentTurn;
    }

    public void incrementTurn() {
        if (this.currentTurn == p1) {
            this.currentTurn = p2;
        } else {
            this.currentTurn = p1;
        }
    }

    public void movePiece(Player player, int oldRow, int oldCol, int newRow, int newCol) throws InvalidMoveException {
        // Ensure it's the players turn to make a move
//        if (currentTurn != player) {
//            throw new InvalidMoveException();
//        }

        // Ensure the old cell is not out-of-bounds
        if (oldRow < 0 || oldRow >= ROWS || oldCol < 0 || oldCol >= COLS) {
            throw new InvalidMoveException();
        }

        // Ensure the new cell is not out-of-bounds
        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS) {
            throw new InvalidMoveException();
        }

        // Ensure the old cell is not empty
        if (this.board[oldRow][oldCol].isEmpty()) {
            throw new InvalidMoveException();
        }

        // Ensure the new cell is empty
        if (!this.board[newRow][newCol].isEmpty()) {
            throw new InvalidMoveException();
        }

        // Ensure piece to be moved is owned by the player
        if (this.board[oldRow][oldCol].getContents().getPlayer() != player) {
            throw new InvalidMoveException();
        }

        // Move piece
        Piece piece = this.board[oldRow][oldCol].removeContents();
        this.board[newRow][newCol].setContents(piece);
    }

    public List<Move> getValidPlayer1Moves() {
        List<Move> moves = new ArrayList<Move>();

        for (int i=0; i < ROWS; i++) {
            for (int j=0; j < COLS; j++) {

                // Check piece exists and belongs to player.
                if (!board[i][j].isEmpty() && board[i][j].getContents().getPlayer() == p1) {

                    // Moving down and right
                    if (i+1 < ROWS && j+1 < COLS && board[i+1][j+1].isEmpty()) {
                        moves.add(new Move(i, j, i+1, j+1, false));
                    }

                    // Moving down and left
                    if (i+1 < ROWS && j-1 >= 0 && board[i+1][j-1].isEmpty()) {
                        moves.add(new Move(i, j, i+1, j-1, false));
                    }

                    // Moving down and right by taking
                    if (i+2 < ROWS && j+2 < COLS && board[i+2][j+2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i+1][j+1].isEmpty() && board[i+1][j+1].getContents().getPlayer() == p2) {
                            moves.add(new Move(i, j, i+2, j+2, true));
                        }
                    }

                    // Moving down and left by taking
                    if (i+2 < ROWS && j-2 < COLS && board[i+2][j-2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i+1][j-1].isEmpty() && board[i+1][j-1].getContents().getPlayer() == p2) {
                            moves.add(new Move(i, j, i+2, j-2, true));
                        }
                    }
                }
            }
        }

        return moves;
    }

    public List<Move> getValidPlayer2Moves() {
        List<Move> nonTakeMoves = new ArrayList<Move>();
        List<Move> takeMoves = new ArrayList<Move>();

        for (int i=0; i < ROWS; i++) {
            for (int j=0; j < COLS; j++) {

                // Check piece exists and belongs to player.
                if (!board[i][j].isEmpty() && board[i][j].getContents().getPlayer() == p2) {

                    // Moving up and right
                    if (i-1 >= 0 && j+1 < COLS && board[i-1][j+1].isEmpty()) {
                        nonTakeMoves.add(new Move(i, j, i-1, j+1, false));
                    }

                    // Moving up and left
                    if (i-1 >= 0 && j-1 >= 0 && board[i-1][j-1].isEmpty()) {
                        nonTakeMoves.add(new Move(i, j, i-1, j-1, false));
                    }

                    // Moving up and right by taking
                    if (i-2 >= 0 && j+2 < COLS && board[i-2][j+2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i-1][j+1].isEmpty() && board[i-1][j+1].getContents().getPlayer() == p1) {
                            takeMoves.add(new Move(i, j, i-2, j+2, true));
                        }
                    }

                    // Moving up and left by taking
                    if (i-2 >= 0 && j-2 >= 0 && board[i-2][j-2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i-1][j-1].isEmpty() && board[i-1][j-1].getContents().getPlayer() == p1) {
                            takeMoves.add(new Move(i, j, i-2, j-2, true));
                        }
                    }
                }
            }
        }

        // If take moves exist, non take moves are invalid.
        if (!takeMoves.isEmpty()) {
            return takeMoves;
        }
        return nonTakeMoves;
    }

    // TODO: refactor this shit!
    private void setupPlayer1() {
        this.board[0][1].setContents(new Piece(p1));
        this.board[0][3].setContents(new Piece(p1));
        this.board[0][5].setContents(new Piece(p1));
        this.board[0][7].setContents(new Piece(p1));
        this.board[1][0].setContents(new Piece(p1));
        this.board[1][2].setContents(new Piece(p1));
        this.board[1][4].setContents(new Piece(p1));
        this.board[1][6].setContents(new Piece(p1));
        this.board[2][1].setContents(new Piece(p1));
        this.board[2][3].setContents(new Piece(p1));
        this.board[2][5].setContents(new Piece(p1));
        this.board[2][7].setContents(new Piece(p1));
    }

    // TODO: refactor this shit!
    private void setupPlayer2() {
        this.board[5][0].setContents(new Piece(p2));
        this.board[5][2].setContents(new Piece(p2));
        this.board[5][4].setContents(new Piece(p2));
        this.board[5][6].setContents(new Piece(p2));
        this.board[6][1].setContents(new Piece(p2));
        this.board[6][3].setContents(new Piece(p2));
        this.board[6][5].setContents(new Piece(p2));
        this.board[6][7].setContents(new Piece(p2));
        this.board[7][0].setContents(new Piece(p2));
        this.board[7][2].setContents(new Piece(p2));
        this.board[7][4].setContents(new Piece(p2));
        this.board[7][6].setContents(new Piece(p2));
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
}
