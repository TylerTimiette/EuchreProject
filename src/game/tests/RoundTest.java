package game.tests;

import game.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RoundTest {

        @Test
        public void testGetData() {
            RoundData rd = new RoundData();
            Round r = new Round(rd);
            assertEquals(rd, r.getData());
        }

        //This checks that the round was successfully instantiated. For actual round testing, please run the program.
        @Test
        public void testStart() {
            RoundData rd = new RoundData();
            Round r = new Round(rd);

            assertEquals(0, Hands.inPlay.size());
            assertEquals(0, r.tricksPlayed);
            assertEquals(0, r.tricksLost);
            assertEquals(0, r.tricksWon);
        }




    }

