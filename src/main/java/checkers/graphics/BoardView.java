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

    BlackSquarePanel selectedPanel;

    public BoardView(final Controller controller) {
        this.setBackground(new Color(40, 100, 0));
        this.setSize(500, 500);
        this.setLayout(new GridLayout(Game.ROWS, Game.COLS));

        initEmptyBoard();
    }

    public void initEmptyBoard() {
        this.removeAll();
        this.setLayout(new GridLayout(Game.ROWS, Game.COLS));
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
        this.setLayout(new GridLayout(Game.ROWS, Game.COLS));

        State gameState = controller.getGame().getGameState();
        Utils.printBoard(gameState.getBoard());
        Cell[][] boardState = gameState.getBoard();

        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    // Position of an empty red square
                    this.add(new RedSqaurePanel());
                } else if (i % 2 != 0 && j % 2 != 0) {
                    // Position of an empty red square
                    this.add(new RedSqaurePanel());
                } else if (boardState[i][j].isEmpty()) {
                    // Position of an empty black square
                    JPanel square = new BlackSquarePanel();

                    final int rowClicked = i;
                    final int colClicked = j;

                    // Add listener for click events
                    square.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            doAction();
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mouseClicked(e);
                            doAction();
                        }

                        public void doAction() {
                            controller.clickedEmptyCell(rowClicked, colClicked);
                            revalidate();
                        }
                    });

                    this.add(square);
                } else {
                    // Position of an occupied black square
                    boolean isRed = boardState[i][j].getContents().getPlayer() == controller.getRedPlayer();
                    boolean isCrowned = boardState[i][j].getContents().isCrowned();
                    final BlackSquarePanel square = new BlackSquarePanel(isRed, isCrowned);

                    final int rowClicked = i;
                    final int colClicked = j;

                    // Add listener for click events
                    square.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            doAction();
                        }

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
                            revalidate();
                        }
                    });

                    this.add(square);
                }
            }
        }

        // Refresh window
        this.revalidate();
    }

    private void selectPanel(int i, int j) {
        if (i % 2 == 0 && j % 2 == 0) {
            // Red cell, no-op
        } else if (i % 2 != 0 && j % 2 != 0) {
            // Red cell, no-op
        } else {
            // Deselect old cell
            if (selectedPanel != null) {
                selectedPanel.deselect();
            }

            // Select new cell
            selectedPanel = ((BlackSquarePanel) getComponentFromGrid(i, j));
            selectedPanel.select();
        }
    }

    private Component getComponentFromGrid(int i, int j) {
        int index = i*Game.COLS + j;
        return this.getComponent(index);
    }

//    private void deselectAllOccupied() {
//        for (Component c : getComponents()) {
//            System.out.println(c);
//            if
//        }
//    }
}
