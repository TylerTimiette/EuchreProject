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
    public void prompt() {
        if (initiative == 1) {


        }
    }

    public void call(Card determiner) {


        System.out.println("You now have the option to PASS (n/no) or CALL (y/yes) on this suit.");
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

    public boolean decision() {
        return called;
    }

    /**public void setInitiative(int i) {
        initiative = i;
    }

    public int getInitiative() {
        return initiative;
    }**/

}
