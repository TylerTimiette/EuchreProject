package game;

import java.util.ArrayList;
import java.util.HashMap;

public class RoundData {


    //This is a round manager.
    public Card.Suit trump;
    public Card.Suit suit;
    public Card highestValue;
    public boolean winner;
    public boolean isTrumpIn = false;
    public HashMap<String, Card> cardMap = new HashMap<String, Card>();

    public ArrayList<Card> playedCards = new ArrayList<Card>();
    public ArrayList<ComputerPlayer> cpuList = new ArrayList<ComputerPlayer>();


    /**    public Round(Card.Suit trump) {
     this.trump = trump;
     } Saving for archival purposes.**/

    public RoundData() {

    }



    public Card.Suit getTrump() {
        return trump;
    }
    public void setTrump(Card.Suit suit) { trump = suit; }


    public Card.Suit getSuit() {
        return suit;
    }

    public void setSuit(Card.Suit set) {

        System.out.println("The suit for this round is " + set);
        suit = set;
    }

    public HashMap getCardMap() {
        return cardMap;
    }

    public void setTrumpIn(boolean b) {
        isTrumpIn = b;
    }


    public void setHighestValue(Card c) {
        highestValue = c;
    }

    public Card getHighestValue() { return highestValue; }

    public boolean getWinner() { return winner; }

    public void setWinner(boolean b) { winner = b; }


}
