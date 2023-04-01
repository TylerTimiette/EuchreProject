package Game;

import java.util.HashMap;

public class Round {
    //This is a round manager.
    public Card.Suit trump;
    public Card.Suit suit;
    public Card highestTotalValue;
    public boolean isTrumpIn = false;
    public HashMap<String, Card> cardMap = new HashMap<String, Card>();

    public int tricksPlayed = 0;
    public int tricksLost = 0;
    public int tricksWon = 0;

    public Round(Card.Suit trump) {
        this.trump = trump;
    }



    public Card.Suit getTrump() {
        return trump;
    }

    public Card.Suit getSuit() {
        return suit;
    }

    public void setSuit(Card.Suit set) {
        suit = set;
    }

    public HashMap getCardMap() {
        return cardMap;
    }


    public boolean trumpIn() {
        return isTrumpIn;
    }

    public void setTrumpIn(boolean b) {
        isTrumpIn = b;
    }

    public Card highestTotalValue() {
        return highestTotalValue();
    }

    public void setHighestTotalValue(Card c) {
        highestTotalValue = c;
    }


}