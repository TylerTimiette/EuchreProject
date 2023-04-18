package Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Round {
    //This is a round manager.
    public Card.Suit trump;
    public Card.Suit suit;
    public Card highestTotalValue;
    public boolean isTrumpIn = false;
    public HashMap<String, Card> cardMap = new HashMap<String, Card>();
    public ArrayList<ComputerPlayer> cpuList = new ArrayList<ComputerPlayer>();
    public int tricksPlayed = 0;
    public int tricksLost = 0;
    public int tricksWon = 0;

/**    public Round(Card.Suit trump) {
        this.trump = trump;
    } Saving for archival purposes.**/

    public Round() {

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

    public void start() {
        cardMap.clear();
        Hands.inPlay.clear();
        cpuList.clear();

        Player p = new Player(Hands.generateHand(5));
        for(int i = 1; i < 4; i++) {
            ComputerPlayer cpu = new ComputerPlayer(Hands.generateHand(5), "CPU"+i, this);
            cpuList.add(cpu);
        }
            //I figured this was just the best possible way to do this.
            Card decision = Hands.generateHand(1).get(0);
            System.out.println("The card presented face up is the " + decision.getValue() + " of " + decision.getSuit());

            //It's annoying to have bots make decisions 3/4th of the time. So we let the player choose first each time.
            if(p.decision()) {
                //playTrick();
            }

            //This is for if no calls are made. Keeps track of things.
            boolean noCall = true;
            for(ComputerPlayer cpu : cpuList) {
                //Not really a getter here. It also doubles as an initializer
                cpu.getBestSuit();
                if(cpu.call(decision)) {
                    noCall = false;
                    break;
                }
            }

            if(noCall) {
                System.out.println("\nNo calls were made.");
                p.call();
                if(p.decision()) {
                    //playTrick();
                }
                else {
                    for(ComputerPlayer cpu : cpuList) {
                        cpu.getBestSuit();
                        if(cpu.call()) {
                            noCall = false;
                            break;
                        }
                    }
                }

            }



        }


        public void playTrick(Player player) {
            if(player.getInitiative() == 1) {
                //player.prompt();
            }
        }
    }


