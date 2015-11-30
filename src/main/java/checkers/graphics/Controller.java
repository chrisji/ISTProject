package checkers.graphics;

import checkers.exceptions.InvalidMoveException;
import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.Move;
import checkers.model.MoveChain;
import checkers.players.AI;
import checkers.players.AIAlphaBeta;
import checkers.players.AIMiniMax;
import checkers.players.FirstMoveAI;
import checkers.players.Player;
import checkers.players.RandomAI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Chris Inskip
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

    // TODO: TIDY UP
    public void updateBoard() {
        boardView.updateGrid(this);
        refreshDisplay();
    }

    // TODO: tidy up
    public void refreshDisplay() {
        mainView.repaint();
        boardView.repaint();
        this.repaint();
    }

    public boolean clickedEmptyCell(int toRow, int toCol) {
        System.out.println("Clicked empty cell (" + toRow + ", " + toCol + ")");

        if (moveInProgress) {
            System.out.println("\t...and move was in progress");

            Player currentPlayer = game.getGameState().getTurn();

            try {
                // Construct move
                Move move = new Move(fromRow, fromCol, toRow, toCol);

                // Attempt to apply the move
                game.setGameState(game.movePiece(game.getGameState(), move));
                updateBoard();
                System.out.println("\t...move successful! (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");

                // TODO refactor...
                if (currentPlayer != game.getGameState().getTurn() && game.getGameState().getTurn() instanceof AI) {
                    doAITurn((AI) game.getGameState().getTurn());
                }
            } catch (InvalidMoveException e) {
                e.printStackTrace();
                // Invalid move, tell the main view
                // TODO
                return false;
            }
        }

        return true;
    }

    public boolean clickedOccupiedCell(int fromRow, int fromCol) {
        System.out.println("Clicked occupied cell (" + fromRow + ", " + fromCol + ")");

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
        updateBoard();
        doNextTurn();
        settingsPanel.resetInGameSettings();
        settingsPanel.showInGameSettings();
    }

    public void showHint() {
        try {
            List<MoveChain> moveChains = game.getMoveChains(game.getGameState(), game.getGameState().getTurn());
            for (MoveChain mc : moveChains) {
                boardView.hintSelectSquare()
            }
        } catch (InvalidMoveException e) {
            // Shouldn't happen
        }
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
        if (game.getGameState().getTurn() instanceof AI) {
            quitGameRequest = true;
        } else {
            gotoMainMenu();
        }
    }

    public void doNextTurn() {
        if (!quitGameRequest) {
            Player currentPlayer = game.getGameState().getTurn();
            if (currentPlayer instanceof AI) {
                doAITurn((AI) currentPlayer);
            }
        }
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
            e.printStackTrace();
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
        // Example game config
        AI firstAI = new FirstMoveAI("AI");
        AI minimax = new AIMiniMax("MM", 5);
        AI alphaBeta = new AIAlphaBeta("AB", 5);
        AI randAI = new RandomAI("Rand_AI");

        Player human = new Player("HUMAN");
        Player human2 = new Player("HUMAN");

        Game g = new Game(minimax, human, minimax);

        Controller controller = new Controller();
//        controller.updateBoard();
//        controller.startTurnSequence();
    }
}
