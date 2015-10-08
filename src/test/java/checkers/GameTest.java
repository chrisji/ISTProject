package checkers;

import checkers.players.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 03/10/2015.
 */
public class GameTest {

    @Test
    public void testIncrementTurn() throws Exception {
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Game g = new Game(p1, p2, p1);

        assertEquals(p1, g.getCurrentTurn());

        g.incrementTurn();
        assertEquals(p2, g.getCurrentTurn());

        g.incrementTurn();
        assertEquals(p1, g.getCurrentTurn());
    }
}