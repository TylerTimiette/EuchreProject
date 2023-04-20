package game;

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
    public RoundData rd;

    int playerScore;
    int botScore;

    public Round(RoundData rd, int playerScore, int botScore) {
        this.rd = rd;
    }


    //SRP Gaming.
    public RoundData getData() {
        return rd;
    }


    public void start() {
        cardMap.clear();
        Hands.inPlay.clear();
        cpuList.clear();
        System.out.println("CURRENT SCORES: \nPLAYER: " + playerScore + "\nCPU: " + botScore);

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
        Card decision = Hands.generateHand(1, "table").get(0);
        System.out.println("The card presented face up is the " + decision.getValue() + " of " + decision.getSuit());

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
                System.out.println("someone called lol shits broke af");
                noCall = false;
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
                        break;
                    }
                }
            }

        }
        //Everyone has passed twice.
        if (noCall == true) {
            System.out.println("Everyone has skipped. The cards will be re-shuffled.");
            start();
        }


    }


    public void playTrick(Player player) {
        ++tricksPlayed;
        if (tricksPlayed <= 5) {
            //They move first. It's annoying to do initiative in Euchre. Also, it's a player vs 3 bots. It cuts down more excess as well.
            player.prompt(this);
            boolean cpuAlone = false;
            boolean playerAlone = false;
            for (ComputerPlayer cpu : cpuList) {
                if(!(player.isGoingIn() && cpu.getName().equalsIgnoreCase("player2")))
                    cpu.play();
            }

            if(getData().getWinner())
                tricksWon++;
            else
                tricksLost++;
        } else {
            if(tricksWon > tricksLost) {
                if(player.isGoingIn()) {
                    if(tricksWon == 5)
                        Game.pointsWon += 4;
                }
                //You only get a special amount of points for winning 5 tricks when going alone. Otherwise you get the default.

                if(tricksWon == 3)
                    Game.pointsWon += 2;
            } else {
                Game.pointsLost += 2;
            }
            System.out.println("TRICK IS OVER.");
        }
    }
}


