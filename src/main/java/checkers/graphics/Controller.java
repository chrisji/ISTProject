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
 * @author 144158
 * @version 26/11/2015
 */
public class Controller extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    // Views
    private MainView mainView;
    private BoardView boardView;
    private SettingsPanel settingsPanel;

    private Game game;
    private boolean quitGameRequest = false;

    private boolean moveInProgress = false;
    private int fromRow = -1;
    private int fromCol = -1;

    public Controller() {
        // Init views
        boardView = new BoardView();
        settingsPanel = new SettingsPanel(new PreGameSettingsView(this), new InGameSettingsPanel(this));
        mainView = new MainView(boardView, settingsPanel);

        // JFrame properties
        this.add(mainView);
        this.setTitle("144158");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public Player getBlackPlayer() {
        return game.getStartingPlayer();
    }

    public void updateBoard() {
        boardView.updateGrid(this);
        refreshDisplay();
    }

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

                if (currentPlayer != game.getGameState().getTurn()) {
                    doNextTurn();
                }
            } catch (InvalidMoveException e) {
                settingsPanel.setMessages(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

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

    public void startGame() {
        settingsPanel.resetInGameSettings();
        settingsPanel.showInGameSettings();
        updateBoard();
        doNextTurn();
    }

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

    public void showWinner() {
        String winner = "Red wins!";
        if (game.getWinner(game.getGameState()) == game.getStartingPlayer()) {
            winner = "Black wins!";
        }

        settingsPanel.setMessages(winner);
        JOptionPane.showMessageDialog(this, winner, "Game Over", JOptionPane.PLAIN_MESSAGE);
    }

    public void gotoMainMenu() {
        moveInProgress = false;
        quitGameRequest = false;

        boardView.initEmptyBoard();
        settingsPanel.resetPreGameSettings();
        settingsPanel.showPreGameSettings();
        refreshDisplay();
    }

    public void requestMainMenu() {
        if (!game.hasWinner(game.getGameState()) && game.getGameState().getTurn() instanceof AI) {
            quitGameRequest = true;
        } else {
            gotoMainMenu();
        }
    }

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

    public void showRules() {
        StringBuilder builder = new StringBuilder();
        try {
            for (String line : Files.readAllLines(Paths.get("res/rules.txt"))) {
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

    private void doAITurn(AI ai) {
        try {
            final List<Move> moves = ai.nextMoveChain().getMoves();

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

    public static void main(String[] args) {
        new Controller();
    }
}
