package checkers.graphics;

import checkers.exceptions.InvalidMoveException;
import checkers.model.Cell;
import checkers.model.Game;
import checkers.model.Move;
import checkers.model.MoveChain;
import checkers.players.AI;
import checkers.players.AIAlphaBeta;
import checkers.players.AIMiniMax;
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

    private static final int WIDTH = 1024;
    private static final int HEIGHT = (WIDTH / 16) * 9;

    // Views
    MainView mainView;
    BoardView boardView;
    PreGameSettingsView preGameSettingsView;

    private Game game;
    private Player redPlayer;

    private boolean moveInProgress = false;
    private int fromRow = -1;
    private int fromCol = -1;

    public Controller() {
        // Init views
        boardView = new BoardView();
        preGameSettingsView = new PreGameSettingsView();
        mainView = new MainView(boardView, preGameSettingsView);

        // JFrame properties
        this.add(mainView);
        this.setTitle("IST: Checkers Game  |  " + WIDTH + "x" + HEIGHT);
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

    public Player getRedPlayer() {
        return game.getStartingPlayer();
    }

    // TODO: TIDY UP
    public void updateBoard() {
        boardView.updateGrid(this);
        boardView.revalidate();
        mainView.revalidate();
        this.revalidate();

        mainView.repaint();
        boardView.repaint();
        this.repaint();

        boardView.requestFocus();
        mainView.requestFocus();
        this.requestFocus();
    }

    // TODO: tidy up
    public void refreshBoard() {
        boardView.revalidate();
        mainView.revalidate();
        this.revalidate();

        mainView.repaint();
        boardView.repaint();
        this.repaint();

        boardView.requestFocus();
        mainView.requestFocus();
        this.requestFocus();
    }

    public boolean clickedEmptyCell(int toRow, int toCol) {
        System.out.println("Clicked empty cell (" + toRow + ", " + toCol + ")");

        if (moveInProgress) {
            System.out.println("\t...and move was in progress");
            try {
                // Construct move
                Move move = new Move(fromRow, fromCol, toRow, toCol);

                // Attempt to apply the move
                game.setGameState(game.movePiece(game.getGameState(), move));
                updateBoard();

                doAITurn();

                System.out.println("\t...move successful! (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
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


    public void startTurnSequence() {
        if (game.getStartingPlayer() instanceof AI) {
            doAITurn();
        }
    }

    // TODO REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void doAITurn() {
        try {
            final List<Move> moves = ((AI) game.getPlayer1()).nextMoveChain().getMoves();

            final Timer thinkingTimer = new Timer(1000, new ActionListener() {
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
        if (moveNumber < moves.size()) {
            final Move chainedMove = moves.get(moveNumber);

            final Timer highlight1Timer = new Timer(1000, null);
            final Timer highlight2Timer = new Timer(1000, null);
            final Timer moveTimer = new Timer(500, null);

            // Highlight the moving from square, and then move onto the next sequence of the animation
            ActionListener highlight1Listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boardView.highlightSquare(chainedMove.getFromRow(), chainedMove.getFromCol());
                    refreshBoard();
                    highlight2Timer.start();
                }
            };

            // Highlight the moving to square, and then move onto the next sequence of the animation
            ActionListener highlight2Listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boardView.highlightSquare(chainedMove.getToRow(), chainedMove.getToCol());
                    refreshBoard();
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
        }
    }

    public static void main(String[] args) {
        // Example game config
        AI firstAI = new AI("AI");
        AI minimax = new AIMiniMax("MM", 5);
        AI alphaBeta = new AIAlphaBeta("AB", 5);
        AI randAI = new RandomAI("Rand_AI");

        Player human = new Player("HUMAN");
        Player human2 = new Player("HUMAN");

        Game g = new Game(minimax, human, minimax);

        Controller controller = new Controller();
        controller.setGame(g);
        controller.updateBoard();
        controller.startTurnSequence();
    }
}
