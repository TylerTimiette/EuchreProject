package game;

import java.util.ArrayList;
import java.util.Random;
import game.Card.Suit;
import game.Card.Value;

public class Hands {

    /**
     * Generates a hand of a given size
     */
    //These are the cards currently in play; they're stored here just for the purpose of checking if an illegal card is somehow being played later down the line & to prevent duplicates.
        public static ArrayList<String> inPlay = new ArrayList<String>();
    public static ArrayList<Card> generateHand(int handSize, String name) {
        // declare and initialize a hand of cards
        ArrayList<Card> hand = new ArrayList<Card>();

        for (int i = 0; i < handSize; i++) {
            // create a card and add it to the hand
            hand.add(recursiveCard(name));
        }
       // System.out.println("\n\n");


        return hand;
    }




    public static Card recursiveCard(String name) {
        System.gc();
        Random random = new Random();
        int numValues = Card.Value.values().length;
        int numSuits = Card.Suit.values().length;

        Suit randomSuit;
        Value randomValue;
        Card card;
        //Repeatedly generate cards until one that isn't in play is found.
        do {
            randomSuit = Card.Suit.values()[random.nextInt(numSuits)];
            randomValue = Card.Value.values()[random.nextInt(numValues)];
            card = new Card(randomSuit, randomValue);
        } while (inPlay.contains(card.getValue().toString() + card.getSuit().toString()));

        inPlay.add(card.getValue().toString() + card.getSuit().toString());
        card.setHolder(name);
       // System.out.println(name + " has a " + card.getValue() + " of " + card.getSuit());
        return card;
    }

    public static Card tableCard() {
        Random random = new Random();
        int numValues = Card.Value.values().length;
        int numSuits = Card.Suit.values().length;

        Suit randomSuit;
        Value randomValue;
        Card card;
        //Repeatedly generate cards until one that isn't in play is found.
        do {
            randomSuit = Card.Suit.values()[random.nextInt(numSuits)];
            randomValue = Card.Value.values()[random.nextInt(numValues)];
            card = new Card(randomSuit, randomValue);
        } while (inPlay.contains(card.getValue().toString() + card.getSuit().toString()));

        inPlay.add(card.getValue().toString() + card.getSuit().toString());
        return card;
    }
}
