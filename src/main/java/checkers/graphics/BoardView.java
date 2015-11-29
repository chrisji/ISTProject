package checkers.graphics;

import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.State;
import checkers.model.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Chris Inskip
 * @version 26/11/2015
 */
public class BoardView extends JPanel {

    // Currently selected square
    private BlackSquarePanel selectedPanel;

    // Panel properties
    private static final int BOARD_WIDTH = 500;
    private static final int BOARD_HEIGHT = 500;

    public BoardView() {
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setLayout(new GridLayout(Game.ROWS, Game.COLS));
        initEmptyBoard();
    }

    public void initEmptyBoard() {
        // Reset grid layout
        this.removeAll();

        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    this.add(new RedSqaurePanel());
                } else if (i % 2 != 0 && j % 2 != 0) {
                    this.add(new RedSqaurePanel());
                } else {
                    this.add(new BlackSquarePanel());
                }
            }
        }

        // Refresh
        this.revalidate();
    }

    public void updateGrid(final Controller controller) {
        this.removeAll();

        State gameState = controller.getGame().getGameState();
        Utils.printBoard(gameState.getBoard());
        Cell[][] boardState = gameState.getBoard();

        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                if (isRedSquarePosition(i, j)) {
                    this.add(new RedSqaurePanel());
                } else if (boardState[i][j].isEmpty()) { // Empty black square
                    JPanel square = new BlackSquarePanel();

                    final int rowClicked = i;
                    final int colClicked = j;

                    // Add listener for click events
                    square.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mouseClicked(e);
                            doAction();
                        }

                        public void doAction() {
                            controller.clickedEmptyCell(rowClicked, colClicked);
                        }
                    });

                    this.add(square);
                } else { // Occupied black square
                    // Get details about checker in that position, and create the square accordingly.
                    boolean isRed = boardState[i][j].getContents().getPlayer() == controller.getRedPlayer();
                    boolean isCrowned = boardState[i][j].getContents().isCrowned();
                    final BlackSquarePanel square = new BlackSquarePanel(isRed, isCrowned);

                    final int rowClicked = i;
                    final int colClicked = j;

                    // Add listener for click events
                    square.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            doAction();
                        }

                        public void doAction() {
                            boolean valid = controller.clickedOccupiedCell(rowClicked, colClicked);
                            if (valid) {
                                selectPanel(rowClicked, colClicked);
                            }
                        }
                    });

                    this.add(square);
                }
            }
        }

        // Refresh window
        this.revalidate();
    }

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
