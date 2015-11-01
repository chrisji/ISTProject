package checkers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris Inskip
 * @version 29/10/2015
 */
public class MoveChain implements Cloneable {
    List<Move> moves;

    public MoveChain() {
        this.moves = new ArrayList<Move>();
    }

    public MoveChain(Move... moves) {
        this.moves = new ArrayList<Move>();

        for (Move m: moves) {
            this.moves.add(m);
            System.out.println(m);
        }
    }

    public void addMove(Move move) {
        this.moves.add(move);
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    @Override
    public MoveChain clone() {
        MoveChain moveChain = new MoveChain();

        for (Move move: moves) {
            moveChain.addMove(move);
        }

        return moveChain;
    }
}
