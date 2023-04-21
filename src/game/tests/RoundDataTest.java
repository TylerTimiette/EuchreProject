package game.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import game.Card;
import game.ComputerPlayer;
import game.Round;
import game.RoundData;
import org.junit.Test;

public class RoundDataTest {

    @Test
    public void testSetAndGetTrump() {
        RoundData rd = new RoundData();
        rd.setTrump(Card.Suit.HEARTS);
        assertEquals(Card.Suit.HEARTS, rd.getTrump());
    }

    @Test
    public void testSetAndGetSuit() {
        RoundData rd = new RoundData();
        rd.setSuit(Card.Suit.DIAMONDS);
        assertEquals(Card.Suit.DIAMONDS, rd.getSuit());
    }

    @Test
    public void testSetAndGetCardMap() {
        RoundData rd = new RoundData();
        HashMap<String, Card> cardMap = new HashMap<String, Card>();
        rd.cardMap = cardMap;
        assertEquals(cardMap, rd.getCardMap());
    }

    @Test
    public void testSetAndGetTrumpIn() {
        RoundData rd = new RoundData();
        rd.setTrumpIn(true);
        assertTrue(rd.isTrumpIn);
    }

    @Test
    public void testSetAndGetHighestValue() {
        RoundData rd = new RoundData();
        Card highestValue = new Card(Card.Suit.CLUBS, Card.Value.ACE);
        rd.setHighestValue(highestValue);
        assertEquals(highestValue, rd.getHighestValue());
    }

    @Test
    public void testSetAndGetWinner() {
        RoundData rd = new RoundData();
        rd.setWinner(true);
        assertTrue(rd.getWinner());
    }

    @Test
    public void testPlayedCards() {
        RoundData rd = new RoundData();
        ArrayList<Card> playedCards = new ArrayList<Card>();
        playedCards.add(new Card(Card.Suit.SPADES, Card.Value.NINE));
        rd.playedCards = playedCards;
        assertEquals(playedCards, rd.playedCards);
    }

    @Test
    public void testCpuList() {
        RoundData rd = new RoundData();
        ArrayList<ComputerPlayer> cpuList = new ArrayList<ComputerPlayer>();
        ComputerPlayer cpu = new ComputerPlayer(new ArrayList<Card>(), "CPU", new Round(rd));
        cpuList.add(cpu);
        rd.cpuList = cpuList;
        assertEquals(cpuList, rd.cpuList);
    }

}
