package checkers.model;

import checkers.exceptions.InvalidMoveException;
import checkers.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 144158
 * @version 03/10/2015
 */
public class Game {

    public static final int ROWS = 8;
    public static final int COLS = 8;

    private State gameState;
    private Player player1;
    private Player player2;
    private Player winner;
    private Player startingPlayer;

    public Game(Player player1, Player player2, Player startingPlayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.startingPlayer = startingPlayer;
        this.winner = null;

        player1.setGame(this);
        player2.setGame(this);

        Cell[][] board = Utils.generateInitialBoard(Game.ROWS, Game.COLS, player1, player2);
        gameState = new State(board, startingPlayer, null);
    }

    public Player incrementTurn(Player player) {
        return player == player1 ? player2 : player1;
    }

    /**
     * Returns `true` if the current
     * @param state
     * @return
     */
    public boolean hasWinner(State state) {
        return getWinner(state) != null;
    }

    /**
     * Returns the "winning" player for a given state, or `null` if no player has won. A player is declared winner if
     * they have no more valid moves: this can occur if the player has no more pieces left, or no remaining pieces can
     * be validly moved.
     *
     * @param state the state to check if a player has won.
     * @return the winning `Player` if one exists, `null` otherwise.
     */
    public Player getWinner(State state) {
        // Check for a winner (current player doesn't have any valid moves left)
        if (state.getTurn() == player1 && getValidPlayer1Moves(state).isEmpty()) {
            return player2; // Player 1 has no more valid moves... Player 2 wins.
        } else if (state.getTurn() == player2 && getValidPlayer2Moves(state).isEmpty()) {
            return player1; // Player 2 has no more valid moves... Player 1 wins.
        }

        return null; // No player has won.
    }

    public State movePiece(State currentState, Move move) throws InvalidMoveException {
        Player player = currentState.getTurn();

        Cell[][] board = Utils.cloneBoard(currentState.getBoard());

        // Ensure the move is valid
        validateMove(currentState, player, move);

        // Move piece, and mark as the current piece being used for this turn
        Piece currentPiece = board[move.getFromRow()][move.getFromCol()].removeContents();
        board[move.getToRow()][move.getToCol()].setContents(currentPiece);

        if (isCrowningMove(player, move)) {
            currentPiece.crown();
        }

        if (move.isTake()) {
            // Remove taken piece from board
            board[move.calcTakenRow()][move.calcTakenCol()].removeContents();

            // Increment turn if player has no more valid moves, or if the piece was crowned
            if (getValidMoves(new State(board, player, currentPiece)).isEmpty() || isCrowningMove(player, move)) {
                return new State(board, incrementTurn(player), null);
            }
        } else {
            // Move was not a take... end of turn
            return new State(board, incrementTurn(player), null);
        }

        return new State(board, player, currentPiece);
    }

    /**
     * Given a `State` and a `MoveChain`, return the resulting state obtained by applying the
     * `MoveChain` to the `State`.
     *
     * @param state `State` to apply the `MoveChain` to.
     * @param moveChain `MoveChain` to apply to the state.
     * @return the resultant `State` obtained by applying the `MoveChain to the give state.
     * @throws InvalidMoveException if any `Move` in the `MoveChain` is not a legal/valid move.
     */
    public State doMoveChain(State state, MoveChain moveChain) throws InvalidMoveException {
        State currentState = state;

        for (Move move : moveChain.getMoves()) {
            currentState = movePiece(currentState, move);
        }

        return currentState;
    }

    public List<MoveChain> getMoveChains(State state, Player player) throws InvalidMoveException {
        return getMoveChainsHelper(state, player, new ArrayList<MoveChain>(), new MoveChain());
    }

    private List<MoveChain> getMoveChainsHelper(State state, Player player, List<MoveChain> moveChains, MoveChain moveChain) throws InvalidMoveException {
        if (player != state.getTurn()) {
            return moveChains;
        }

        List<Move> moves = getValidMoves(state);

        for (Move move: moves) {
            State nextState = movePiece(state, move);
            MoveChain clonedMoveChain = moveChain.clone();
            clonedMoveChain.addMove(move);

            // Check to see if chain still going.
            if (nextState.getTurn() != player) {
                // End of move chaining
                moveChains.add(clonedMoveChain);
//                System.out.println(clonedMoveChain.getMoves());
            } else {
                // Moves still available to chain together
                getMoveChainsHelper(nextState, player, moveChains, clonedMoveChain);
            }
        }

        return moveChains;
    }

    public List<Move> getValidMoves(State state) {
        if (state.getTurn() == player1) {
            return getValidPlayer1Moves(state);
        } else {
            return getValidPlayer2Moves(state);
        }
    }

    // TODO: Do some refactoring to merge player1 and player2 checks.
    public List<Move> getValidPlayer1Moves(State state) {
        Cell[][] board = state.getBoard();
        Piece currentPiece = state.getCurrentPiece();

        List<Move> nonCapturingMoves = new ArrayList<Move>();
        List<Move> capturingMoves = new ArrayList<Move>();

        for (int i=0; i < ROWS; i++) {
            for (int j=0; j < COLS; j++) {
                // Check piece exists, belongs to the player and is either the players first move for this turn or they are moving the same piece on their turn.
                if (!board[i][j].isEmpty() && board[i][j].getContents().getPlayer() == player1 && (currentPiece == null || board[i][j].getContents() == currentPiece)) {

                    // If piece has already been moved, disallow non-capturing move
                    if (currentPiece == null) {
                        // Moving down and right
                        if (i+1 < ROWS && j+1 < COLS && board[i+1][j+1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i + 1, j + 1));
                        }

                        // Moving down and left
                        if (i+1 < ROWS && j-1 >= 0 && board[i+1][j-1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i + 1, j - 1));
                        }
                    }

                    // Moving down and right by taking
                    if (i+2 < ROWS && j+2 < COLS && board[i+2][j+2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i+1][j+1].isEmpty() && board[i+1][j+1].getContents().getPlayer() == player2) {
                            capturingMoves.add(new Move(i, j, i + 2, j + 2));
                        }
                    }

                    // Moving down and left by taking
                    if (i+2 < ROWS && j-2 >= 0 && board[i+2][j-2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i+1][j-1].isEmpty() && board[i+1][j-1].getContents().getPlayer() == player2) {
                            capturingMoves.add(new Move(i, j, i + 2, j - 2));
                        }
                    }

                    // Backwards rules for king pieces
                    if (board[i][j].getContents().isCrowned()) {
                        // If piece has already been moved, disallow non-capturing move
                        if (currentPiece == null) {
                            // Moving up and right
                            if (i-1 >= 0 && j + 1 < COLS && board[i-1][j+1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i - 1, j + 1));
                            }

                            // Moving up and left
                            if (i-1 >= 0 && j-1 >= 0 && board[i-1][j-1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i - 1, j - 1));
                            }
                        }

                        // Moving up and right by taking
                        if (i-2 >= 0 && j+2 < COLS && board[i-2][j+2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i-1][j+1].isEmpty() && board[i-1][j+1].getContents().getPlayer() == player2) {
                                capturingMoves.add(new Move(i, j, i - 2, j + 2));
                            }
                        }

                        // Moving up and left by taking
                        if (i-2 >= 0 && j-2 >= 0 && board[i-2][j-2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i-1][j-1].isEmpty() && board[i-1][j-1].getContents().getPlayer() == player2) {
                                capturingMoves.add(new Move(i, j, i - 2, j - 2));
                            }
                        }
                    }
                }
            }
        }

        // If capturing moves exist, then any non-capturing moves are invalid.
        if (!capturingMoves.isEmpty()) {
            return capturingMoves;
        }
        return nonCapturingMoves;
    }

    public List<Move> getValidPlayer2Moves(State state) {
        Cell[][] board = state.getBoard();
        Piece currentPiece = state.getCurrentPiece();

        List<Move> nonCapturingMoves = new ArrayList<Move>();
        List<Move> capturingMoves = new ArrayList<Move>();

        for (int i=0; i < ROWS; i++) {
            for (int j=0; j < COLS; j++) {

                // Check piece exists, belongs to the player and is either the player's first move for this turn or they are moving the same piece on their turn.
                if (!board[i][j].isEmpty() && board[i][j].getContents().getPlayer() == player2 && (currentPiece == null || board[i][j].getContents() == currentPiece)) {

                    // If piece has already been moved, disallow non-capturing move
                    if (currentPiece == null) {
                        // Moving up and right
                        if (i-1 >= 0 && j+1 < COLS && board[i-1][j+1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i - 1, j + 1));
                        }

                        // Moving up and left
                        if (i-1 >= 0 && j-1 >= 0 && board[i-1][j-1].isEmpty()) {
                            nonCapturingMoves.add(new Move(i, j, i - 1, j - 1));
                        }
                    }

                    // Moving up and right by taking
                    if (i-2 >= 0 && j+2 < COLS && board[i-2][j+2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i-1][j+1].isEmpty() && board[i-1][j+1].getContents().getPlayer() == player1) {
                            capturingMoves.add(new Move(i, j, i - 2, j + 2));
                        }
                    }

                    // Moving up and left by taking
                    if (i-2 >= 0 && j-2 >= 0 && board[i-2][j-2].isEmpty()) {
                        // Check opponent exists to jump over
                        if (!board[i-1][j-1].isEmpty() && board[i-1][j-1].getContents().getPlayer() == player1) {
                            capturingMoves.add(new Move(i, j, i - 2, j - 2));
                        }
                    }

                    // Backwards rules for king pieces
                    if (board[i][j].getContents().isCrowned()) {
                        // If piece has already been moved, disallow non-capturing move
                        if (currentPiece == null) {
                            // Moving down and right
                            if (i+1 < ROWS && j+1 < COLS && board[i+1][j+1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i + 1, j + 1));
                            }

                            // Moving down and left
                            if (i+1 < ROWS && j-1 >= 0 && board[i+1][j-1].isEmpty()) {
                                nonCapturingMoves.add(new Move(i, j, i + 1, j - 1));
                            }
                        }

                        // Moving down and right by taking
                        if (i+2 < ROWS && j+2 < COLS && board[i+2][j+2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i+1][j+1].isEmpty() && board[i+1][j+1].getContents().getPlayer() == player1) {
                                capturingMoves.add(new Move(i, j, i + 2, j + 2));
                            }
                        }

                        // Moving down and left by taking
                        if (i+2 < ROWS && j-2 >= 0 && board[i+2][j-2].isEmpty()) {
                            // Check opponent exists to jump over
                            if (!board[i+1][j-1].isEmpty() && board[i+1][j-1].getContents().getPlayer() == player1) {
                                capturingMoves.add(new Move(i, j, i + 2, j - 2));
                            }
                        }
                    }
                }
            }
        }

        // If capturing moves exist, then any non-capturing moves are invalid.
        if (!capturingMoves.isEmpty()) {
            return capturingMoves;
        }
        return nonCapturingMoves;
    }

    private void validateMove(State state, Player player, Move move) throws InvalidMoveException {
        Player currentTurn = state.getTurn();

        // Ensure it's the players turn to make a move
        if (currentTurn != player) {
            throw new InvalidMoveException("Invalid move! It's not your turn.");
        }

        // Ensure the old cell is not out-of-bounds
        if (move.getFromRow() < 0 || move.getFromRow() >= ROWS || move.getFromCol() < 0 || move.getFromCol() >= COLS) {
            throw new InvalidMoveException("Cannot move a piece from outside the board's dimensions");
        }

        // Ensure the new cell is not out-of-bounds
        if (move.getToRow() < 0 || move.getToRow() >= ROWS || move.getToCol() < 0 || move.getToCol() >= COLS) {
            throw new InvalidMoveException("Cannot move a piece to outside the board's dimensions");
        }

        // Ensure the old cell is not empty
        if (state.getBoard()[move.getFromRow()][move.getFromCol()].isEmpty()) {
            throw new InvalidMoveException("There is no piece in that square!");
        }

        // Ensure the new cell is empty
        if (!state.getBoard()[move.getToRow()][move.getToCol()].isEmpty()) {
            throw new InvalidMoveException("There is already a piece in that square!");
        }

        // Ensure piece to be moved is owned by the player
        if (state.getBoard()[move.getFromRow()][move.getFromCol()].getContents().getPlayer() != player) {
            throw new InvalidMoveException("The piece attempting to be moved is not owned by the current player");
        }

        if (player == player1) {
            List<Move> moves = getValidPlayer1Moves(state);

            // Check to see if move if a valid move, and if not find the reason.
            if (!moves.contains(move)) {
                if (moves.isEmpty()) {
                    throw new InvalidMoveException("There are no more valid moves left!");
                } else if (moves.get(0).isTake()) {
                    throw new InvalidMoveException("Invalid move! Your move must be a capturing move. (Click show help for a hint or see the rules for clarification)");
                } else {
                    throw new InvalidMoveException("Invalid move! The pieces can only move diagonally by a single square. (Click 'show help' for a hint, or see the rules for clarification)");
                }
            }
        }

        if (player == player2) {
            List<Move> moves = getValidPlayer2Moves(state);

            // Check to see if move if a valid move, and if not find the reason.
            if (!moves.contains(move)) {
                if (moves.isEmpty()) {
                    throw new InvalidMoveException("There are no more valid moves left!");
                } else if (moves.get(0).isTake()) {
                    throw new InvalidMoveException("Invalid move! Your move must be a capturing move. (Click 'show help' for a hint, or see the rules for clarification)");
                } else {
                    throw new InvalidMoveException("Invalid move! The pieces can only move diagonally by a single square. (Click 'show help' for a hint, or see the rules for clarification)");
                }
            }
        }
    }

    private boolean isCrowningMove(Player player, Move move) {
        // Check if player 1 piece has reached the 'bottom' of the board
        if (player == player1 && move.getToRow() == 7) {
            return true;
        }

        // Check if player 2 piece has reached the 'top' of the board
        if (player == player2 && move.getToRow() == 0) {
            return true;
        }

        return false;
    }

    public boolean hasChangedPlayer(State fromState, State toState) {
        return fromState.getTurn().equals(toState.getTurn());
    }

    public State getGameState() {
        return this.gameState;
    }

    public void setGameState(State state) {
        this.gameState = state;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Player getStartingPlayer() {
        return this.startingPlayer;
    }
}
