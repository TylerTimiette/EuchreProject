package game;

import java.util.ArrayList;

public class Game {


    public static int pointsWon = 0;
    public static int pointsLost = 0;
        public static void main(String[] args) {

            /**ArrayList<Card> cardHand = Hands.generateHand(5);
            for (Card card : cardHand) {
                System.out.println(card.getValue() + " of " + card.getSuit());
            }**/
            play();

            // System.out.println(ch.cards); show what happens when there is no toString()
        }


        public static void play() {
            System.out.println("Starting game.");
            Round r = new Round(new RoundData(), pointsWon, pointsLost);
            r.start();
            if(pointsWon != 10 && pointsLost != 10) {
                play();
            } else {
                System.out.println("GAME OVER.");
                if(pointsWon > pointsLost) {
                    System.out.println("YOU WIN!");
                }
            }
        }
    }
