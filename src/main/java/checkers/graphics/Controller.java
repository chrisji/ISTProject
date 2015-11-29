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

                // Do AI move TODO REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if (game.getGameState().getTurn() instanceof AI) {
                    final MoveChain moves = ((AI) game.getPlayer1()).nextMoveChain();

                    for (Move m: moves.getMoves()) {

                        final Move chainedMove = m;
                        ActionListener listener = new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    game.setGameState(game.movePiece(game.getGameState(), chainedMove));
                                    updateBoard();
                                } catch (InvalidMoveException me){

                                }
                            }
                        };
                        Timer timer = new Timer(1000, listener);
                        timer.setRepeats(false);
                        timer.start();
                    }
                }

                System.out.println("\t...move successful! (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
            } catch (InvalidMoveException e) {
                e.printStackTrace();
                // Invalid move, tell the main view
                // TODO
                updateBoard();
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

    public static void main(String[] args) {
        // Example game config
        AI firstAI = new AI("AI");
        AI minimax = new AIMiniMax("MM", 10);
        AI alphaBeta = new AIAlphaBeta("AB", 8);
        AI randAI = new RandomAI("Rand_AI");

        Player human = new Player("HUMAN");
        Player human2 = new Player("HUMAN");

        Game g = new Game(minimax, human, human);

        Controller controller = new Controller();
        controller.setGame(g);
        controller.updateBoard();
    }
}
