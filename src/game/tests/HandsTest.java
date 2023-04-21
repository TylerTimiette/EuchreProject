package game.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import game.Card;
import game.Hands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HandsTest {

    @Before
    public void setUp() throws Exception {
        // Clear inPlay before each test
        Hands.inPlay.clear();
    }

    @After
    public void tearDown() throws Exception {
        // Clear inPlay after each test
        Hands.inPlay.clear();
    }

    @Test
    public void testGenerateHand() {
        ArrayList<Card> hand = Hands.generateHand(5, "test");
        assertEquals(5, hand.size());
        for (Card card : hand) {
            assertEquals("test", card.holder());
        }
        // Check that no cards in the hand are duplicates
        ArrayList<String> cardStrings = new ArrayList<String>();
        for (Card card : hand) {
            String cardString = card.getValue().toString() + card.getSuit().toString();
            assertFalse(cardStrings.contains(cardString));
            cardStrings.add(cardString);
        }
    }

    @Test
    public void testRecursiveCard() {
        // Test generating a single card with no cards in play
        Hands.inPlay.clear();
        Card card = Hands.recursiveCard("bob");
        assertNotNull(card);
        assertEquals("bob", card.holder());
        // Test generating a card when all cards are already in play
        Hands.inPlay.add(card.getValue().toString() + card.getSuit().toString());
        card = Hands.recursiveCard("Bob");
        assertNotNull(card);
    }

    @Test
    public void testTableCard() {
        // Test generating a single table card with no cards in play
        Hands.inPlay.clear();
        Card card = Hands.tableCard();
        assertNotNull(card);
        // Test generating a table card when all cards are already in play
        Hands.inPlay.add(card.getValue().toString() + card.getSuit().toString());
        card = Hands.tableCard();
        assertNotNull(card);
    }

}
