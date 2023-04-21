package game.tests;

import game.Card;
import game.Player;
import game.Round;
import game.RoundData;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class PlayerTest {
        @Test
        public void testCallValidInputYes() {
            //Tests the case where the player chooses to call the card
            Round r = new Round(new RoundData());
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.KING));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.NINE));
            Player p = new Player(hand);
            Card c = new Card(Card.Suit.HEARTS, Card.Value.QUEEN);
            System.setIn(new ByteArrayInputStream("y\n".getBytes()));
            p.testReplaceCard(c, 1);
            p.setDecisionTest(true);
            r.getData().setTrump(c.getSuit());
            Assertions.assertTrue(p.decision());
            Assert.assertEquals(c.getSuit(), r.getData().getTrump());
        }

        @Test
        public void testCallValidInputNo() {
            //Tests the case where the player chooses to pass on the card
            Round r = new Round(new RoundData());
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.KING));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.NINE));
            Player p = new Player(hand);
            Card c = new Card(Card.Suit.CLUBS, Card.Value.JACK);
            p.setDecisionTest(false);
            Assert.assertFalse(p.decision());
            Assert.assertNotEquals(c.getSuit(), r.getData().getTrump());
        }

        @Test
        public void testCallInvalidInput() {
            //Tests the case where the player passes and then doesn't
            Round r = new Round(new RoundData());
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.KING));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
            hand.add(new Card(Card.Suit.CLUBS, Card.Value.NINE));
            Player p = new Player(hand);
            Card c = hand.get(2);

            r.getData().setTrump(c.getSuit());
            //simulate player entering invalid input
            p.setDecisionTest(false);
            Assert.assertFalse(p.decision());
            p.setDecisionTest(true);
            Assert.assertTrue(p.decision());
            Assert.assertEquals(c.getSuit(), r.getData().getTrump());
        }

        @Test
        public void testReplaceCardValidInput() {
            Round r = new Round(new RoundData());
            ArrayList<Card> hand = new ArrayList<>();
            //Tests the case where the player enters valid input for card replacement
            Card c1 = new Card(Card.Suit.SPADES, Card.Value.ACE);
            Card c2 = new Card(Card.Suit.DIAMONDS, Card.Value.KING);
            hand.add(c1);
            hand.add(c2);
            Player p = new Player(hand);
            Card replace = new Card(Card.Suit.CLUBS, Card.Value.JACK);

            Assert.assertEquals(c2, p.hand.get(1));
        }


    }
