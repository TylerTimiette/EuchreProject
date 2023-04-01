package Game;

import java.util.ArrayList;

public class Player {

        public static void main(String[] args) {

            ArrayList<Card> cardHand = HandGenerator.generateHand(5);
            for (Card card : cardHand) {
                System.out.println(card.getValue() + " of " + card.getSuit());
            }
            // System.out.println(ch.cards); show what happens when there is no toString()
        }
    }
