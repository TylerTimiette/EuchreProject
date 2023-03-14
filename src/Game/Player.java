package Game;

public class Player {

        public static void main(String[] args) {

            Card[] cardHand = Generator.generateHand(5);
            for (Card card : cardHand) {
                System.out.println(card.getValue() + " of " + card.getSuit());
            }
            // System.out.println(ch.cards); show what happens when there is no toString()
        }
    }
