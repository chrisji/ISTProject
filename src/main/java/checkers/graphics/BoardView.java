package checkers.graphics;

import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.State;
import checkers.players.AI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * `BoardView` is a panel that holds the grid of red and black squares.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class BoardView extends JPanel {

    // Currently selected square
    private BlackSquarePanel selectedPanel;

    /**
     * Creates a new `BoardView` containing no pieces.
     */
    public BoardView() {
        this.setLayout(new GridLayout(Game.ROWS, Game.COLS));
        this.setBackground(new Color(47, 33, 16));
        this.setBorder(BorderFactory.createLineBorder(new Color(47, 33, 16), 5));

        initEmptyBoard();
    }

    /**
     * Sets the board to be completely empty (i.e. no pieces)
     */
    public void initEmptyBoard() {
        // Reset grid layout
        this.removeAll();

        // Add empty red and black squares in the correct positions
        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                if (isRedSquarePosition(i, j)) {
                    this.add(new RedSquarePanel());
                } else {
                    this.add(new BlackSquarePanel());
                }
            }
        }

        // Refresh
        this.revalidate();
    }

    /**
     * Updates the grid with squares of the game's current board configuration.
     * Additionally, assigning mouse listeners to the appropriate squares.
     *
     * @param controller
     */
    public void updateGrid(final Controller controller) {
        // Reset grid layout
        this.removeAll();

        // Get the underlying game's current board.
        State gameState = controller.getGame().getGameState();
        Cell[][] boardState = gameState.getBoard();

        // Add the squares and pieces to the grid.
        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                if (isRedSquarePosition(i, j)) {
                    this.add(new RedSquarePanel());
                } else if (boardState[i][j].isEmpty()) { // Empty black square
                    BlackSquarePanel square = new BlackSquarePanel();

                    final int rowClicked = i;
                    final int colClicked = j;

                    // Add listener for click events
                    square.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mouseClicked(e);
                            controller.clickedEmptyCell(rowClicked, colClicked);
                        }
                    });

                    this.add(square);
                } else { // Occupied black square
                    // Get details about checker in that position
                    boolean isBlack = boardState[i][j].getContents().getPlayer() == controller.getBlackPlayer();
                    boolean isCrowned = boardState[i][j].getContents().isCrowned();

                    // Create square from obtained details
                    final BlackSquarePanel square = new BlackSquarePanel(isBlack, isCrowned);

                    boolean isAI = boardState[i][j].getContents().getPlayer() instanceof AI;

                    if (!isAI) {
                        final int rowClicked = i;
                        final int colClicked = j;

                        // Add listener for click events
                        square.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                super.mousePressed(e);

                                boolean valid = controller.clickedOccupiedCell(rowClicked, colClicked);
                                if (valid) {
                                    selectPanel(rowClicked, colClicked);
                                } else {
                                    deselectPanel(rowClicked, colClicked);
                                }
                            }
                        });
                    }

                    this.add(square);
                }
            }
        }

        // Refresh window
        this.revalidate();
    }

    /**
     * Attempts to 'select' the square at the specified position.
     *
     * @param row row index of the square to 'select'.
     * @param col column index of the square to 'select'.
     */
    private void selectPanel(int row, int col) {
        if (isBlackSquarePosition(row, col)) {
            // Deselect previously selected square
            if (selectedPanel != null) {
                selectedPanel.deselect();
            }

            try {
                // Select square at the given position
                selectedPanel = ((BlackSquarePanel) getComponentFromGrid(row, col));
                selectedPanel.select();
            } catch (ClassCastException e) {
                // Component was not a BlackSquarePanel - should not happen!
                e.printStackTrace();
            }
        }
    }

    /**
     * Attempts to 'deselect' the square at the specified position.
     *
     * @param row row index of the square to 'deselect'.
     * @param col column index of the square to 'deselect'.
     */
    private void deselectPanel(int row, int col) {
        if (isBlackSquarePosition(row, col) && selectedPanel != null) {
            selectedPanel.deselect();
        }
    }

    /**
     * Attempts to 'highlight' the square at the specified position.
     *
     * @param row row index of the square to 'highlight'.
     * @param col column index of the square to 'highlight'.
     */
    public void highlightSquare(int row, int col) {
        try {
            // Select square at the given position
            BlackSquarePanel squareToSelect = ((BlackSquarePanel) getComponentFromGrid(row, col));
            squareToSelect.select();
        } catch (ClassCastException e) {
            // Component was not a BlackSquarePanel
            e.printStackTrace();
        }
    }

    /**
     * Attempts to set the square at the specified position as a hint square.
     *
     * @param row row index of the square to 'hint-select'.
     * @param col column index of the square to 'hint-select'.
     */
    public void hintSelectSquare(int row, int col) {
        try {
            // Select square at the given position
            BlackSquarePanel squareToSelect = ((BlackSquarePanel) getComponentFromGrid(row, col));
            squareToSelect.hintSelect();
        } catch (ClassCastException e) {
            // Component was not a BlackSquarePanel
            e.printStackTrace();
        }
    }

    /**
     * Returns `true` if the given position is the position of a red square.
     *
     * @param row row of the position to check.
     * @param col column of the position to check.
     * @return `true` if the given position is the position of a red square.
     */
    private boolean isRedSquarePosition(int row, int col) {
        return (row % 2 == 0 && col % 2 == 0) || (row % 2 != 0 && col % 2 != 0);
    }

    /**
     * Returns `true` if the given position is the position of a black square.
     *
     * @param row row of the position to check.
     * @param col column of the position to check.
     * @return `true` if the given position is the position of a black square.
     */
    private boolean isBlackSquarePosition(int row, int col) {
        return !isRedSquarePosition(row, col);
    }

    /**
     * Returns the `Component` stored at the provided co-ordinates in the grid layout.
     *
     * The grid layout stores components linearly, therefore this method first converts
     * the 2D co-ordinates into a single linear index for use with the grid layout.
     *
     * @param row row of the `Component` to return
     * @param col column of the `Component` to return
     * @return Component stored at the provided co-ordinates in the grid layout.
     */
    private Component getComponentFromGrid(int row, int col) {
        // Convert 2D co-ordinates to linear index for use with the grid layout.
        int index = (row*Game.COLS) + col;

        // Return the `Component` from the grid layout.
        return this.getComponent(index);
    }
}
