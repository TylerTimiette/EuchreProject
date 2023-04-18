package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    public ArrayList<Card> hand;
    public int initiative = 0;
    public boolean called = false;


    public Player(ArrayList<Card> hand) {
        this.hand = hand;
    }

    //main method for the player
    public void prompt(ArrayList<Card> cardsPlayed) {


        }


    public void call(Card determiner) {

        System.out.println("You now have the option to PASS (n/no) or CALL (y/yes) on the " + determiner.getValue() + " of " + determiner.getSuit());
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        //Check for valid input
        if (s.matches("(?i)^(y|n(o)?|yes|call|pass)$")) {
            //They want the card.
            if (s.matches("^(?i)(yes|y|call)$")) {
                for (int i = 1; i <= hand.size(); i++) {
                    System.out.println("[" + i + "] " + hand.get(i).getValue() + " of " + hand.get(i).getSuit());
                }
                //Picking up a suit means you need to pick up the card on the table. This doesn't apply if it's been skipped and is on second round.
                System.out.println("Please pick a card to replace with the " + determiner.getValue() + " of " + determiner.getSuit());
                replaceCard(determiner);
                called = true;
            }

            //They don't want the card.
            if (s.matches("^(?i)(no|n|pass)$")) {
                called = false;
            }
        } else {
            //It's easier to just recursively run it again, so we do.
            System.out.println("Invalid input.");
            call(determiner);
        }
    }

    public void replaceCard(Card c) {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();

        try {
            //Replace cards
            int i = Integer.parseInt(s);
            if (i > 5) {
                Hands.inPlay.remove(i);
                hand.set(i, c);
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
    public void call() {
        System.out.println("YOUR CURRENT CARDS\n");
        for (int i = 1; i <= hand.size(); i++) {
            System.out.println("[" + i + "] " + hand.get(i).getValue() + " of " + hand.get(i).getSuit());
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Pick a suit (hearts/spades/club/diamonds) that you currently have in your hand, or pass (n/no)");
        String s = scan.next();
        if(s.matches("(?i)^(d(iamonds)?|c(lubs)?|s(pades)?|h(earts)?|pass)$")) {
            //We're determining what suit they want to play.
            Game.Card.Suit suit;
            if(s.equalsIgnoreCase("d") || s.equalsIgnoreCase("diamonds")) {
                suit = Game.Card.Suit.DIAMONDS;
            } else if(s.equalsIgnoreCase("c") || s.equalsIgnoreCase("clubs")) {
                suit = Game.Card.Suit.CLUBS;
            } else if(s.equalsIgnoreCase("s") || s.equalsIgnoreCase("spades")) {
                suit = Game.Card.Suit.SPADES;
            } else if(s.equalsIgnoreCase("h") || s.equalsIgnoreCase("hearts")) {
                suit = Game.Card.Suit.HEARTS;
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
                    System.out.println("You have chosen the suit!");
                }
            } else {
                called = false;
            }
        }
    }





    public boolean decision() {
        return called;
    }



public void setInitiative(int i) {
        initiative = i;
    }

    public int getInitiative() {
        return initiative;
    }

}
