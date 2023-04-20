package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    public ArrayList<Card> hand;
    public int initiative = 0;
    public boolean called = false;
    public Round round;
    boolean goingIn = false;

    public Player(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public Player() {
    }

    //main method for the player
    public void prompt(Round round) {
            RoundData data = round.getData();
            System.out.println("It is your turn to play. The trump is set to " + data.getTrump() + ", and the current suit is unset.");
            Card.Suit suit = data.getSuit();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println("[" + (i+1) + "] " + hand.get(i).getValue() + " of " + hand.get(i).getSuit());
            }

            System.out.println("Please pick a card to play. (Using the index numbers)");
            Scanner scan = new Scanner(System.in);
            try {
               int i =  Integer.parseInt(scan.next());
               //Check that they put a good number in.
               if(i <= hand.size()) {
                   System.out.println("You have decided to play the " + hand.get(i-1).getValue() + " of " + hand.get(i-1).getSuit());
                   if(data.getHighestValue() == null || (data.getHighestValue().getNumericValue() < hand.get(i-1).getNumericValue()))
                       data.setHighestValue(hand.get(i-1));
                   if(data.getTrump().equals(hand.get(i).getSuit())) {
                       data.setTrumpIn(true);
                   }

                   data.getCardMap().put("PLAYER", hand.get(i-1));
                   data.setWinner(true);
                   hand.remove(i-1);
               } else {
                   //Invalid number
                   prompt(round);
               }

            } catch (Exception e) {
                System.out.println("An invalid number was put in. Please try again.");
                e.printStackTrace();
                prompt(round);
            }
        }

    public void call(Card determiner, Round round) {
        RoundData data = round.getData();
        System.out.println(hand.size());
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("[" + (i+1) + "] " + hand.get(i).getValue() + " of " + hand.get(i).getSuit());
        }
        System.out.println("You now have the option to PASS (n/no) or CALL (y/yes) on the " + determiner.getValue() + " of " + determiner.getSuit());
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        //Check for valid input
        if (s.matches("(?i)^(y|n(o)?|yes|call|pass)$")) {
            //They want the card.
            if (s.matches("^(?i)(yes|y|call)$")) {
                for (int i = 0; i < hand.size(); i++) {
                    System.out.println("[" + (i+1) + "] " + hand.get(i).getValue() + " of " + hand.get(i).getSuit());
                }
                //Picking up a suit means you need to pick up the card on the table. This doesn't apply if it's been skipped and is on second round.
                System.out.println("Please pick a card to replace with the " + determiner.getValue() + " of " + determiner.getSuit());
                replaceCard(determiner);
                round.getData().setTrump(determiner.getSuit());
                called = true;
            }
            //They don't want the card.
            if (s.matches("^(?i)(no|n|pass)$")) {
                called = false;
                System.out.println("You passed.");
            }
        } else {
            //It's easier to just recursively run it again, so we do.
            System.out.println("Invalid input.");
            call(determiner, round);
        }
    }

    public void replaceCard(Card c) {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();

        try {
            //Replace cards
            int i = Integer.parseInt(s);
            if (i <= 5 && i > 0) {
                Hands.inPlay.remove(i);
                hand.set(i-1, c);
                //Invalid number was chosen
            } else {
                System.out.println("Please choose a valid number.");
                replaceCard(c);
            }
            //NaN
        } catch (NumberFormatException e) {
            System.out.println("Please choose a valid number.");
            replaceCard(c);
        }
    }

    //When they have all passed, the player is prompted for what they want to play
    public void call(Round round) {
        RoundData data = round.getData();
        System.out.println("YOUR CURRENT CARDS\n");
        for (int i = 1; i < hand.size(); i++) {
            System.out.println("[" + (i+1) + "] " + hand.get((i)).getValue() + " of " + hand.get((i)).getSuit());
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Pick a suit (hearts/spades/club/diamonds) that you currently have in your hand, or pass (n/no/pass)");
        String s = scan.next();
        if(s.matches("(?i)^(d(iamonds)?|c(lubs)?|s(pades)?|h(earts)?|pass|n|no)$")) {
            //We're determining what suit they want to play.
            Card.Suit suit;
            if(s.equalsIgnoreCase("d") || s.equalsIgnoreCase("diamonds")) {
                suit = Card.Suit.DIAMONDS;
            } else if(s.equalsIgnoreCase("c") || s.equalsIgnoreCase("clubs")) {
                suit = Card.Suit.CLUBS;
            } else if(s.equalsIgnoreCase("s") || s.equalsIgnoreCase("spades")) {
                suit = Card.Suit.SPADES;
            } else if(s.equalsIgnoreCase("h") || s.equalsIgnoreCase("hearts")) {
                suit = Card.Suit.HEARTS;
            } else {
                //either they passed or an error occurred. regardless, not good
                suit = null;
            }

            if(suit != null) {
                for(Card card : hand) {
                    if(card.getSuit() == suit) {
                        called = true;
                    } else if(card.getValue().equals(Card.Value.JACK) && (card.getSuit().getColor().equals(suit.getColor()))) {
                        called = true;
                    }
                }
                if(called) {
                    System.out.println("You have chosen the trump suit!");
                    round.getData().setTrump(suit);
                }
                setGoingIn();
            } else {
                //Passes. Or maybe there was an error.
                called = false;
                System.out.println("You have passed.");
            }
        }
    }





    public boolean decision() {
        return called;
    }

    public boolean isGoingIn() { return goingIn; }
    public void setGoingIn() {
        System.out.println("Are you going in alone? This means you will not receive backup from your friendly CPU, but have the potential to win more points by doing so. Y/N");
        Scanner scan = new Scanner(System.in);
        String s = scan.next();

        if (s.matches("(?i)^(y|n(o)?|yes)$")) {
            if (s.matches("(?i)^(y|yes?)$")) {
                goingIn = true;
                System.out.println("You are going in alone.");
            }

            if (s.matches("(?)^(n|no)$")) {
                goingIn = false;
                System.out.println("You are not going in alone.");
            }

        } else {
            setGoingIn();
        }
    }





}
