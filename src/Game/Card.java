package Game;


/**
 * Starter code for the Card class. To be used in Week 4.
 *
 * @author dancye, 2019
 * @modified Paul Bonenfant Feb 2022
 * @modified Megha Patel 2023
 * @modified Tyler Jenkins 2023
 */
public class Card {


    /**Euchre is a card game where there's only around 24 cards usually. Bit of a far-cry from Uno...
     * However, these changes do not lock you out of very many card games.
     * Go Fish could still be played with suits & values. Not Uno, though.
     * The generator still supports a variety of games, allowing it to be used in, say, another card game with minimal editing required.
     * Basically, this class can polymorph if need be. It can be used for anything involving card games.
     */

  //Bowers of a same color are also trump. This is defined in the suit.
    public enum Suit {
        HEARTS(Color.RED),
        CLUBS(Color.BLACK),
        SPADES(Color.BLACK),
        DIAMONDS(Color.RED);

        private Color color;

        Suit(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
        public enum Color {
            RED, BLACK
        }
    }

    public String holder;
    public int viabilityScore;

    //This simplifies finding the highest value later.
    public enum Value {
        ACE(14), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

        private final int numericValue;

        private Value(int numericValue) {
            this.numericValue = numericValue;
        }

        public int numericValue() {
            return numericValue;
        }
    }

    private final Suit suit;
    private final Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Value getValue() {
        return this.value;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public int getNumericValue() {
        return this.getValue().numericValue();
    }

    public String holder() {
        return holder;
    }

    public void setHolder(String s) {
        holder = s;
    }
    //This is exclusive to robots and helps them figure out which card to run with.
    public void increaseViability(int increase) {
        viabilityScore += increase;
    }

    public int getViabilityScore() {
        return viabilityScore;
    }
}