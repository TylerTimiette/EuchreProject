package game;


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


        public static void setPointsWon(int i) {
            pointsWon += i;
        }
        public static void setPointsLost(int i) {
            pointsLost += i;
        }

        public static void play() {
            System.out.println("Starting game.");
            Round r = new Round(new RoundData());
            r.start();
            if(!(pointsWon == 10 && pointsLost == 10)) {
                play();
            }
            r = null;
            System.gc();
        }
    }
