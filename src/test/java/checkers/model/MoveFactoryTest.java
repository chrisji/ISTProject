package checkers.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 01/11/2015.
 */
public class MoveFactoryTest {

    @Test
    public void testBuildMove() throws Exception {
        Move m1 = MoveFactory.buildMove(0, 0, 2, 2);
        Move m2 = MoveFactory.buildMove(0, 0, 2, 2);
        assertSame(m1, m2);

        Move m3 = new Move(0, 0, 2, 2);
        assertEquals(m1, m3);
        assertNotSame(m1, m3);
    }
}