package game.tests;
import game.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
public class CardTest {
    @Test
    void getValue() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        Assertions.assertEquals(Card.Value.ACE, card.getValue());
    }

    @Test
    void getSuit() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        Assertions.assertEquals(Card.Suit.HEARTS, card.getSuit());
    }

    @Test
    void getNumericValue() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        Assertions.assertEquals(14, card.getNumericValue());
    }

    @Test
    void setNumericValue() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        card.setNumericValue(1);
        Assertions.assertEquals(1, card.getNumericValue());
    }

    @Test
    void holder() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        card.setHolder("Player 1");
        Assertions.assertEquals("Player 1", card.holder());
    }

    @Test
    void setHolder() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        card.setHolder("Player 2");
        Assertions.assertEquals("Player 2", card.holder());
    }

    @Test
    void increaseViability() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        card.increaseViability(10);
        Assertions.assertEquals(10, card.getViabilityScore());
    }

    @Test
    void getViabilityScore() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        card.increaseViability(10);
        Assertions.assertEquals(10, card.getViabilityScore());
    }

    @Test
    void testToString() {
        Card card = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        Assertions.assertEquals("ACE of HEARTS", card.getValue() + " of " + card.getSuit());
    }

    @Test
    void testEquality() {
        Card card1 = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        Card card2 = new Card(Card.Suit.HEARTS, Card.Value.ACE);
        Card card3 = new Card(Card.Suit.CLUBS, Card.Value.ACE);
        Assertions.assertNotEquals(card1, card3);
    }
}
