package checkers.graphics;

import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.State;
import checkers.model.Utils;
import checkers.players.AI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TODO
 *
 * @author 144158
 * @version 02/12/2015
 */
public class BoardView extends JPanel {

    // Currently selected square
    private BlackSquarePanel selectedPanel;

    // Panel properties
    private static final int BOARD_WIDTH = 500;
    private static final int BOARD_HEIGHT = 500;

    /**
     * TODO
     */
    public BoardView() {
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);

        Dimension dimension = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        this.setMaximumSize(dimension);
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);

        this.setBackground(new Color(47, 33, 16));
        this.setLayout(new GridLayout(Game.ROWS, Game.COLS));
        this.setBorder(BorderFactory.createLineBorder(new Color(47, 33, 16), 5));

        initEmptyBoard();
    }

    /**
     * TODO
     */
    public void initEmptyBoard() {
        // Reset grid layout
        this.removeAll();

        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    this.add(new RedSquarePanel());
                } else if (i % 2 != 0 && j % 2 != 0) {
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
     * TODO
     * @param controller
     */
    public void updateGrid(final Controller controller) {
        this.removeAll();

        State gameState = controller.getGame().getGameState();
        Utils.printBoard(gameState.getBoard());
        Cell[][] boardState = gameState.getBoard();

        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                if (isRedSquarePosition(i, j)) {
                    this.add(new RedSquarePanel());
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
                    boolean isBlack = boardState[i][j].getContents().getPlayer() == controller.getBlackPlayer();
                    boolean isCrowned = boardState[i][j].getContents().isCrowned();
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
                                doAction();
                            }

                            public void doAction() {
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
     * TODO
     * @param row
     * @param col
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
     * TODO
     * @param row
     * @param col
     */
    private void deselectPanel(int row, int col) {
        if (isBlackSquarePosition(row, col) && selectedPanel != null) {
            selectedPanel.deselect();
        }
    }

    /**
     * TODO
     * @param row
     * @param col
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
     * TODO
     * @param row
     * @param col
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
