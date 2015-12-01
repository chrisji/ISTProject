package checkers.model;

import checkers.players.Player;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class Utils {

    /**
     * TODO
     * @param rows
     * @param cols
     * @param player1
     * @param player2
     * @return
     */
    public static Cell[][] generateInitialBoard(int rows, int cols, Player player1, Player player2) {
        Cell[][] board = new Cell[rows][cols];

        // Init board with empty cells
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                board[i][j] = new Cell();
            }
        }

        // Add pieces to the board
        board = addPlayer1Pieces(board, player1);
        board = addPlayer2Pieces(board, player2);
        return board;
    }

    // TODO: refactor this shit?
    private static Cell[][] addPlayer1Pieces(Cell[][] board, Player player1) {
        board[0][1].setContents(new Piece(player1));
        board[0][3].setContents(new Piece(player1));
        board[0][5].setContents(new Piece(player1));
        board[0][7].setContents(new Piece(player1));
        board[1][0].setContents(new Piece(player1));
        board[1][2].setContents(new Piece(player1));
        board[1][4].setContents(new Piece(player1));
        board[1][6].setContents(new Piece(player1));
        board[2][1].setContents(new Piece(player1));
        board[2][3].setContents(new Piece(player1));
        board[2][5].setContents(new Piece(player1));
        board[2][7].setContents(new Piece(player1));
        return board;
    }

    // TODO: refactor this shit?
    private static Cell[][] addPlayer2Pieces(Cell[][] board, Player player2) {
        board[5][0].setContents(new Piece(player2));
        board[5][2].setContents(new Piece(player2));
        board[5][4].setContents(new Piece(player2));
        board[5][6].setContents(new Piece(player2));
        board[6][1].setContents(new Piece(player2));
        board[6][3].setContents(new Piece(player2));
        board[6][5].setContents(new Piece(player2));
        board[6][7].setContents(new Piece(player2));
        board[7][0].setContents(new Piece(player2));
        board[7][2].setContents(new Piece(player2));
        board[7][4].setContents(new Piece(player2));
        board[7][6].setContents(new Piece(player2));
        return board;
    }

    /**
     * TODO
     *
     * @param board
     * @return
     */
    public static Cell[][] cloneBoard(Cell[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        Cell[][] newBoard = new Cell[rows][cols];

        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                try {
                    newBoard[i][j] = board[i][j].clone();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return newBoard;
    }

    /**
     * Visual Debugging.
     *
     * Prints the board out to the console.
     */
    public static void printBoard(Cell[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        // Top numbers
        System.out.print("  ");
        for (int i=0; i < rows; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println();


        for (int i=0; i < rows; i++) {
            System.out.println("   --- --- --- --- --- --- --- ---  ");
            System.out.print(i + " | "); // Left side numbers

            for (int j=0; j < cols; j++) {
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
