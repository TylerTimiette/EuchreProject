package game.tests;

import game.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testPointsWon() {
        Game.setPointsWon(3);
        assertEquals(3, Game.pointsWon);
    }

    @Test
    public void testPointsLost() {
        Game.setPointsLost(2);
        assertEquals(2, Game.pointsLost);
    }


    //There isn't really a way to test start-game. The best solution is to just ACTUALLY run the program

}
