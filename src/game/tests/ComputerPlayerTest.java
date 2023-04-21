package game.tests;

import game.*;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ComputerPlayerTest {

    @Test
    public void testPlay() {
        Round round = new Round(new RoundData());
        round.getData().setTrump(Card.Suit.CLUBS);
        ArrayList<Card> hand = new ArrayList<>();
        round.getData().setHighestValue(new Card(Card.Suit.HEARTS, Card.Value.ACE));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.KING));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.NINE));
        ComputerPlayer player = new ComputerPlayer(hand, "test", round);
        player.play();
        assertEquals(3, player.hand.size());
        assertEquals(1, round.getData().getCardMap().size());
        assertTrue(round.getData().getCardMap().containsKey("test"));
    }

    @Test
    public void testCall() {
        Round round = new Round(new RoundData());
        ArrayList<Card> hand = new ArrayList<>();
        round.getData().setHighestValue(new Card(Card.Suit.HEARTS, Card.Value.ACE));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.KING));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
        hand.add(new Card(Card.Suit.CLUBS, Card.Value.NINE));
        round.getData().setTrump(Card.Suit.CLUBS);
        ComputerPlayer player = new ComputerPlayer(hand, "test", round);
        boolean shouldCall = player.call();
        if(shouldCall)
            assertTrue(shouldCall);
        else
            assertFalse(shouldCall);
        assertEquals(Card.Suit.CLUBS, player.getBestSuit());
    }
}