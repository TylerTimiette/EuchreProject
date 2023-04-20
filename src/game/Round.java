package game;

import java.util.ArrayList;
import java.util.HashMap;

public class Round {
    //This is a round manager.
    public HashMap<String, Card> cardMap = new HashMap<String, Card>();
    public ArrayList<ComputerPlayer> cpuList = new ArrayList<ComputerPlayer>();
    public int tricksPlayed = 0;
    public int tricksLost = 0;
    public int tricksWon = 0;
    public RoundData rd;


    public Round(RoundData rd) {
        this.rd = rd;
    }


    //SRP Gaming.
    public RoundData getData() {
        return rd;
    }


    public void start() {
        System.gc();
        cardMap.clear();
        Hands.inPlay.clear();
        cpuList.clear();
        System.out.println("CURRENT SCORES: \nPLAYER: " + Game.pointsWon + "\nCPU: " + Game.pointsLost);

        Player p = new Player(Hands.generateHand(5, "PLAYER"));
        for (int i = 1; i < 4; i++) {
            String name;
            if (i % 2 == 0)
                name = "PLAYER2";
            else
                name = "ENEMY" + i;
            ComputerPlayer cpu = new ComputerPlayer(Hands.generateHand(5, name), name, this);
            cpuList.add(cpu);
        }



        //I figured this was just the best possible way to do this.
        Card decision = Hands.tableCard();
        System.out.println("The card presented face up is the " + decision.getValue() + " of " + decision.getSuit());
        playRound(p, decision);
    }


    public void playRound(Player p,Card decision) {
        System.gc();
        p.call(decision, this);
        //It's annoying to have bots make decisions 3/4th of the time. So we let the player choose first each time.
        if (p.decision()) {
            playTrick(p);
        }

        //This is for if no calls are made. Keeps track of things.
        boolean noCall = true;
        for (ComputerPlayer cpu : cpuList) {
            //Not really a getter here. It also doubles as an initializer
            cpu.getBestSuit();
            if (cpu.call(decision)) {
                noCall = false;
                rd.setTrump(decision.getSuit());
                playTrick(p);
                break;
            }
        }

        if (noCall) {
            System.out.println("\nNo calls were made.");
            p.call(this);
            if (p.decision()) {
                playTrick(p);
            } else {
                for (ComputerPlayer cpu : cpuList) {
                    cpu.getBestSuit();
                    if (cpu.call()) {
                        noCall = false;
                        playTrick(p);
                        break;
                    }
                }
            }

        }
        //Everyone has passed twice.
        if (noCall) {
            System.out.println("Everyone has skipped. The cards will be re-shuffled.");
            start();
        }
    }

    public void playTrick(Player player) {
        ++tricksPlayed;
        promoteBowers(rd.getTrump().getColor(), player);
        if (tricksPlayed <= 5) {
            System.out.println("\n\nCurrent trick counter: " + tricksPlayed);
            //They move first. It's annoying to do initiative in Euchre. Also, it's a player & 3 bots. It cuts down more excess as well.
            player.prompt(this);
            for (ComputerPlayer cpu : cpuList) {
                    cpu.play();
            }

            if(getData().getWinner()) {
                tricksWon++;
                System.out.println("You won the trick.");
            }
            else {
                tricksLost++;
                System.out.println("You lost the trick.");
            }
            System.out.println("\n\nTRICKS WON: " + tricksWon + " TRICKS LOST: " + tricksLost);
            playTrick(player);
        } else {
            int points = 2;
            if(tricksWon > tricksLost) {
                    Game.setPointsWon(points);
            } else {
                Game.setPointsLost(points);
            }
            System.out.println("TRICK IS OVER.");
            if(Game.pointsWon < 10 && Game.pointsLost < 10) {
                player = null;
                System.gc();
                Game.play();
            }
            else {
                if(Game.pointsWon == 10)
                    System.out.println("YOU WON!");
                if(Game.pointsLost == 10)
                    System.out.println("YOU LOST.");
                System.exit(0);
            }
        }
    }


//Promoting bowers to their proper ranking based on their color.
    public void promoteBowers(Card.Suit.Color color, Player player) {
        rd.setHighestValue(null);
        for(Card playerCard : player.hand) {
            if(playerCard.getSuit().getColor().equals(color) && playerCard.getValue().equals(Card.Value.JACK)) {
                if(!playerCard.getSuit().equals(rd.getTrump())) {
                    playerCard.setNumericValue(90);
                } else {
                    playerCard.setNumericValue(100);
                }
            }
            if(playerCard.getValue().equals(Card.Value.JACK) && !playerCard.getSuit().getColor().equals(color) && !playerCard.getSuit().equals(rd.getTrump())) {
                playerCard.setNumericValue(11);
            }
        }
        for (ComputerPlayer cpu : cpuList) {
            for (Card cpuCard : cpu.hand) {
                if(cpuCard.getSuit().getColor().equals(color) && cpuCard.getValue().equals(Card.Value.JACK)) {
                    if(!cpuCard.getSuit().equals(rd.getTrump())) {
                        cpuCard.setNumericValue(90);
                    } else {
                        cpuCard.setNumericValue(100);
                    }
                }
                if(cpuCard.getValue().equals(Card.Value.JACK) && !cpuCard.getSuit().getColor().equals(color) && !cpuCard.getSuit().equals(rd.getTrump())) {
                    cpuCard.setNumericValue(11);
                }
            }
        }
        //System.out.println("Bowers promoted.");
    }
}


