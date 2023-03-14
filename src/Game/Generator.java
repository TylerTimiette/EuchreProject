package Game;

import java.util.Random;
import Game.Card.Suit;
import Game.Card.Value;

public class Generator {

    /**
     * Generates a hand of a given size
     */
    public static Card[] generateHand(int handSize) {

        // we'll use this to generate random numbers
        Random random = new Random();

        // let's get these lengths once
        int numValues = Card.Value.values().length;
        int numSuits = Card.Suit.values().length;

        // declare and initialize a hand of cards
        Card[] hand = new Card[handSize];

        for (int i = 0; i < handSize; i++) {

            // get a random suit and value. Note we're not concerned about uniqueness
            // at this point
            Suit randomSuit = Card.Suit.values()[random.nextInt(numSuits)];
            Value randomValue = Card.Value.values()[random.nextInt(numValues)];

            // create a card and add it to the hand
            Card card = new Card(randomSuit, randomValue);
            hand[i] = card;

        }

        return hand;
    }
}
