package checkers.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class MoveChainTest {

    @Test
    public void testInitWithMultiple() {
        Move m1 = new Move(0, 0, 1, 1);
        Move m2 = new Move(1, 1, 2, 2);
        Move m3 = new Move(2, 2, 3, 3);

        MoveChain mc = new MoveChain(m1, m2, m3);
        assertEquals(3, mc.getMoves().size());
    }
}