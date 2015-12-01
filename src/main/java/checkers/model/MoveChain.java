package checkers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author 144158
 * @version 02/12/2015
 */
public class MoveChain implements Cloneable {
    List<Move> moves;

    public MoveChain() {
        this.moves = new ArrayList<Move>();
    }

    /**
     * TODO
     *
     * @param moves
     */
    public MoveChain(Move... moves) {
        this.moves = new ArrayList<Move>();

        for (Move m: moves) {
            this.moves.add(m);
            System.out.println(m);
        }
    }

    /**
     * TODO
     *
     * @param move
     */
    public void addMove(Move move) {
        this.moves.add(move);
    }

    /**
     * TODO
     *
     * @return
     */
    public List<Move> getMoves() {
        return this.moves;
    }

    /**
     * TODO
     * @return
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
