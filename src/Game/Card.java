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
    public enum Suit {
        HEARTS, CLUBS, SPADES, DIAMONDS
    }

    public enum Value {
        ACE, NINE, TEN, JACK, QUEEN, KING
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
    public Value getValue(String s) {
        return this.value;
    }

    public Suit getSuit() {
        return this.suit;
    }
}