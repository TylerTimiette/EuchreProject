package Game;

import java.util.ArrayList;
import java.util.Random;
import Game.Card.Suit;
import Game.Card.Value;

public class Hands {

    /**
     * Generates a hand of a given size
     */
    //These are the cards currently in play; they're stored here just for the purpose of checking if an illegal card is somehow being played later down the line & to prevent duplicates.
        public static ArrayList<String> inPlay = new ArrayList<String>();
    public static ArrayList<Card> generateHand(int handSize) {



        // declare and initialize a hand of cards
        ArrayList<Card> hand = new ArrayList<Card>();

        for (int i = 0; i < handSize; i++) {


            // create a card and add it to the hand
            hand.add(recursiveCard());
        }


        return hand;
    }

    public static Card recursiveCard() {
        // we'll use this to generate random numbers
        Random random = new Random();

        // let's get these lengths once
        int numValues = Card.Value.values().length;
        int numSuits = Card.Suit.values().length;
        // get a random suit and value.
        Suit randomSuit = Card.Suit.values()[random.nextInt(numSuits)];
        Value randomValue = Card.Value.values()[random.nextInt(numValues)];
        Card card = new Card(randomSuit, randomValue);

        //Debug.
        System.out.println("Card generated: " + card.getValue() + " of " + card.getSuit());
        //There's definitely other ways of doing this, but I am lazy.
        if(!inPlay.contains(card.getValue().toString()+card.getSuit().toString())) {
            inPlay.add(card.getValue().toString()+card.getSuit().toString());
            return card;
        } else
            //We do the recursion here because having to regenerate an entire hand over and over probably leaves performance on the table. Why do that when we could simply... not?
            recursiveCard();

        return card;
    }
}
