package checkers.graphics;

import checkers.exceptions.InvalidMoveException;
import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.Move;
import checkers.model.MoveChain;
import checkers.players.AI;
import checkers.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * `Controller` is the controller for the checker's MVC pattern. It stores and
 * manipulates the underlying `Game`, and updates the front-end views accordingly.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class Controller extends JFrame {

    // Window properties
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final String TITLE = "144158";

    // Views
    private MainView mainView;
    private BoardView boardView;
    private SettingsPanel settingsPanel;

    // Underlying game model
    private Game game;

    private boolean quitGameRequest = false;
    private boolean moveInProgress = false;
    private int fromRow = -1;
    private int fromCol = -1;

    /**
     * Creates the controller for the checker's MVC pattern, and displays the starting
     * screen of the GUI.
     */
    public Controller() {
        // Init views
        boardView = new BoardView();
        settingsPanel = new SettingsPanel(new PreGameSettingsView(this), new InGameSettingsPanel(this));
        mainView = new MainView(boardView, settingsPanel);
        this.add(mainView);

        // JFrame properties
        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Sets the underlying game model that the controller should operate on.
     *
     * @param game underlying `Game` model.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Returns the underlying game model that the controller is operating on.
     *
     * @return the underlying `Game` model.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Returns the `Player` that is controlling the 'black' pieces
     *
     * @return the `Player` that is controlling the 'black' pieces
     */
    public Player getBlackPlayer() {
        return game.getStartingPlayer();
    }

    /**
     * Updates the board to keep it in sync with the model.
     */
    public void updateBoard() {
        boardView.updateGrid(this);
        refreshDisplay();
    }

    /**
     * Ensures all of the views are repainted to represent any changes.
     */
    public void refreshDisplay() {
        mainView.repaint();
        boardView.repaint();
        settingsPanel.repaint();
        this.repaint();

        mainView.revalidate();
        boardView.revalidate();
        settingsPanel.revalidate();
        this.revalidate();
    }

    /**
     * Does the action that happens when the an empty cell is clicked. This will
     * check to see a piece is being moved, and if so try and move it to the empty cell.
     * If this move is not valid, then it will show the corresponding invalid move message
     * in the messages panel.
     *
     * @param toRow empty cell's row index.
     * @param toCol empty cell's column index.
     * @return `true` if the cell clicked resulted in a valid move, `false` otherwise.
     */
    public boolean clickedEmptyCell(int toRow, int toCol) {
        if (moveInProgress) {
            Player currentPlayer = game.getGameState().getTurn();

            try {
                // Construct move
                Move move = new Move(fromRow, fromCol, toRow, toCol);

                // Attempt to apply the move
                game.setGameState(game.movePiece(game.getGameState(), move));
                updateBoard();
                settingsPanel.setMessages();

                // Move was successful, so move on to the next player's turn
                if (currentPlayer != game.getGameState().getTurn()) {
                    doNextTurn();
                }
            } catch (InvalidMoveException e) {
                settingsPanel.setMessages(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        // Move was valid.
        return true;
    }

    /**
     * Does the action that happens when the an occupied cell is clicked.
     *
     * @param fromRow row index of the occupied cell.
     * @param fromCol column index of the occupied cell.
     * @return `true` if the piece can be moved by the current player, `false` otherwise.
     */
    public boolean clickedOccupiedCell(int fromRow, int fromCol) {
        // Get the underlying cell that has been clicked.
        Cell clickedCell = game.getGameState().getBoard()[fromRow][fromCol];

        Player currentPlayer = game.getGameState().getTurn();
        Player pieceOwner = clickedCell.getContents().getPlayer();

        // Return `true` if the piece can be moved by the current player (i.e. it's their turn).
        if (currentPlayer.equals(pieceOwner)) {
            moveInProgress = true;
            this.fromRow = fromRow;
            this.fromCol = fromCol;
            return true;
        } else {
            moveInProgress = false;
            return false;
        }
    }

    /**
     * Updates the board to represent the game's board configuration, and
     * performs the first move.
     */
    public void startGame() {
        settingsPanel.resetInGameSettings();
        settingsPanel.showInGameSettings();
        updateBoard();
        doNextTurn();
    }

    /**
     * Highlights all squares that are movable for the current player.
     */
    public void showHint() {
        try {
            List<MoveChain> moveChains = game.getMoveChains(game.getGameState(), game.getGameState().getTurn());
            for (MoveChain mc : moveChains) {
                Move firstMove = mc.getMoves().get(0);
                boardView.hintSelectSquare(firstMove.getFromRow(), firstMove.getFromCol());
            }
        } catch (InvalidMoveException e) {
            // Shouldn't happen!
        }
    }

    /**
     * Pops up a window to show the winning player color.
     */
    public void showWinner() {
        String winner = "Red wins!";
        if (game.getWinner(game.getGameState()) == game.getStartingPlayer()) {
            winner = "Black wins!";
        }

        settingsPanel.setMessages(winner);
        JOptionPane.showMessageDialog(this, winner, "Game Over", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Goes back to the main menu, ready for a new game to be set-up.
     */
    public void gotoMainMenu() {
        moveInProgress = false;
        quitGameRequest = false;

        boardView.initEmptyBoard();
        settingsPanel.resetPreGameSettings();
        settingsPanel.showPreGameSettings();
        refreshDisplay();
    }

    /**
     * Send a request for the main menu to be shown. This is needed since move animations have to end
     * before the main menu can be shown. The main menu will therefore be shown as soon as the
     * animations have finished.
     */
    public void requestMainMenu() {
        if (!game.hasWinner(game.getGameState()) && game.getGameState().getTurn() instanceof AI) {
            quitGameRequest = true;
        } else {
            gotoMainMenu();
        }
    }

    /**
     * Switches to the next player (and in the case of an AI, performs their next turn).
     * If a player has won, then the game is over, and the winner will be shown.
     */
    public void doNextTurn() {
        if (!quitGameRequest) {
            if (game.hasWinner(game.getGameState())) {
                showWinner();
                moveInProgress = false;
            } else {
                Player currentPlayer = game.getGameState().getTurn();

                if (currentPlayer == getGame().getStartingPlayer()) {
                    settingsPanel.setMessages("Black's turn");
                } else {
                    settingsPanel.setMessages("Red's turn");
                }

                if (currentPlayer instanceof AI) {
                    doAITurn((AI) currentPlayer);
                }
            }
        }
    }

    /**
     * Pops up a window showing the checkers rules.
     */
    public void showRules() {
        StringBuilder builder = new StringBuilder();
        try {
            for (String line : Files.readAllLines(Paths.get("checkers/res/rules.txt"))) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JTextArea textArea = new JTextArea(builder.toString());
        textArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(450, 600);

        JDialog popUp = new JDialog();
        popUp.setSize(450, 600);
        popUp.add(scrollPane);
        popUp.setResizable(false);
        popUp.setLocationRelativeTo(this);

        popUp.setVisible(true);
    }

    /**
     * Performs the AI's turn with appropriate move-by-move animations.
     *
     * @param ai the `AI` to carry out the turn for.
     */
    private void doAITurn(AI ai) {
        try {
            final List<Move> moves = ai.nextMoveChain().getMoves();

            // Time before the AI actually starts the move process.
            final Timer thinkingTimer = new Timer(200, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doNextAIMove(moves, 0);
                }
            });

            thinkingTimer.setRepeats(false);
            thinkingTimer.start();
        } catch (InvalidMoveException e) {
            // Shouldn't happen!
        }
    }

    /**
     * Does a single move of the AI's move sequence, and then performs the next
     * move in the sequence until the move sequence is finished.
     *
     * @param moves List representing the move sequence to carry out.
     * @param moveNumber the index of the `Move` of the move sequence to perform.
     */
    private void doNextAIMove(final List<Move> moves, final int moveNumber) {
        if (quitGameRequest) {
            gotoMainMenu();
        } else if (moveNumber < moves.size()) {
            final Move chainedMove = moves.get(moveNumber);

            final Timer highlight1Timer = new Timer(700, null);
            final Timer highlight2Timer = new Timer(700, null);
            final Timer moveTimer = new Timer(500, null);

            // Highlight the moving from square, and then move onto the next sequence of the animation
            ActionListener highlight1Listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boardView.highlightSquare(chainedMove.getFromRow(), chainedMove.getFromCol());
                    refreshDisplay();
                    highlight2Timer.start();
                }
            };

            // Highlight the moving to square, and then move onto the next sequence of the animation
            ActionListener highlight2Listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boardView.highlightSquare(chainedMove.getToRow(), chainedMove.getToCol());
                    refreshDisplay();
                    moveTimer.start();
                }
            };

            // Make the move, and then perform the next move animation (in the case of multi jumps)
            ActionListener moveListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        game.setGameState(game.movePiece(game.getGameState(), chainedMove));

                        // Updates the board (will also remove previous highlights)
                        updateBoard();
                        doNextAIMove(moves, moveNumber + 1);
                    } catch (InvalidMoveException me) {
                        settingsPanel.setMessages(me.getMessage());
                        me.printStackTrace();
                    }
                }
            };

            highlight1Timer.addActionListener(highlight1Listener);
            highlight1Timer.setRepeats(false);

            highlight2Timer.addActionListener(highlight2Listener);
            highlight2Timer.setRepeats(false);

            moveTimer.addActionListener(moveListener);
            moveTimer.setRepeats(false);

            highlight1Timer.start();
        } else if (!quitGameRequest) {
            doNextTurn();
        }
    }
}
