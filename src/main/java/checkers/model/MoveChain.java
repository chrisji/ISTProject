package checkers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure that is used to represent a sequence `Move`s making up a turn.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class MoveChain implements Cloneable {
    List<Move> moves;

    /**
     * Creates a new `MoveChain`, with an empty sequence of moves.
     */
    public MoveChain() {
        this.moves = new ArrayList<Move>();
    }

    /**
     * Creates a new `MoveChain`, with a given sequence of moves.
     *
     * @param moves
     */
    public MoveChain(Move... moves) {
        this.moves = new ArrayList<Move>();

        for (Move m: moves) {
            this.moves.add(m);
        }
    }

    /**
     * Adds a move to the move chain sequence
     *
     * @param move `Move` to add to the move chain sequence.
     */
    public void addMove(Move move) {
        this.moves.add(move);
    }

    /**
     * Returns the `List` of `Move`s that this move chain is storing.
     *
     * @return `List` of `Move`s that this move chain is storing.
     */
    public List<Move> getMoves() {
        return this.moves;
    }

    /**
     * Clones and returns the current `MoveChain`. Since the `Move`s that move
     * chain is storing are immutable, they are not cloned.
     *
     * @return the cloned `MoveChain`.
     */
    @Override
    public MoveChain clone() {
        MoveChain moveChain = new MoveChain();

        for (Move move: moves) {
            moveChain.addMove(move);
        }

        return moveChain;
    }
}
