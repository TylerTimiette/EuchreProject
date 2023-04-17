package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer {

    public String name;
    public ArrayList<Card> hand;
    int clubScore = 0;
    int spadeScore = 0;
    int heartScore = 0;
    int diamondScore = 0;

    Game.Card.Suit bestSuit;
    Round round;

    public ComputerPlayer(ArrayList<Card> hand, String name, Round round) {
        this.hand = hand;
        this.name = name;
        this.round = round;
    }

    //We pass this with a Round in order to avoid too much necessary static. Additionally, the larger game logic should probably pass the round data to the bots anyways.
    public Card bestViableCard(Round roundData) {
        Card topCard = hand.get(0);
        HashMap<String, Card> cardMap = roundData.getCardMap();
        //Time for some heavy logic.
        //No cards have been played yet, so the bot will pick a card at random.
        if (cardMap.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(hand.size());
            Card picked = hand.get(randomIndex);
            topCard = picked;

            if (topCard.getSuit().toString().equalsIgnoreCase(roundData.getTrump().toString()))
                roundData.setTrumpIn(true);

            roundData.setSuit(topCard.getSuit());
            return topCard;
            //Here we go.
        } else {

            boolean inDemand = false;
            //This is for our personal hand.
            for (Card personalCard : hand) {
                //THIS IS FOR THE CARDS CURRENTLY ON THE TABLE!!!
                for (Map.Entry<String, Card> tabledCard : cardMap.entrySet()) {
                    //Check if we have a card from the played suit. If we do, we must play it.
                    if (personalCard.getSuit().equals(roundData.getSuit())) {
                        //Compare to the tabled card. Additionally set a flag that we cannot play trump or offsuit
                        inDemand = true;
                        if (tabledCard.getValue().getSuit().equals(roundData.getSuit())) {
                            if (personalCard.getNumericValue() > tabledCard.getValue().getNumericValue()) {
                                //A big bump in viability is provided because we have a higher value card of a suit that is in-demand.
                                personalCard.increaseViability(4);
                            } else {
                                //We still want to use the card, as we are forced to. Providing a small bump in viability is the best way to do that.
                                personalCard.increaseViability(1);
                            }
                        }
                    }
                }
                //We have a trump card

                //We do not have a card that is in-demand.
                if (inDemand == false) {
                    //Trump is in
                    if (personalCard.getSuit().equals(roundData.getTrump()) || (personalCard.getValue() == Card.Value.JACK && personalCard.getSuit().getColor() == roundData.getTrump().getColor())) {
                        //Don't you love nested for-loops?
                        for (Map.Entry<String, Card> tabledCard : cardMap.entrySet()) {
                            if ((tabledCard.getValue().getSuit().equals(roundData.getTrump()) && personalCard.getNumericValue() > tabledCard.getValue().getNumericValue())) {
                                //The enemy CPUs will have the same name. This is so that you don't teamkill.
                                if (!(tabledCard.getValue().holder().equalsIgnoreCase(personalCard.holder()))) {
                                    personalCard.increaseViability(10);
                                } else {
                                    //They are on the same team. The AI should avoid trumping its partner if possible.
                                    for (Card nest : hand) {
                                        if (!(nest.getSuit().equals(tabledCard.getValue().getSuit()))) {
                                            nest.increaseViability(20);
                                        } else
                                            nest.increaseViability(15); //This should only proc when the AI has no other option.
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Decision is being made...");
        for (Card personalCard : hand) {
            if (topCard.getViabilityScore() <= personalCard.getViabilityScore())
                topCard = personalCard;
        }
        if (roundData.highestTotalValue().getNumericValue() < topCard.getNumericValue()) {
            roundData.setHighestTotalValue(topCard);
        }
        return topCard;
    }


    public void play(Round round) {
        Card cardPlayed = bestViableCard(round);
        System.out.println("The " + name + " plays the " + cardPlayed.getValue().toString() + " of " + cardPlayed.getSuit() + "!");
    }


    public void clear() {
        //Done just in case I've messed up somewhere and the arrays are not deleted properly
        hand.clear();

    }


    //This function dictates an AI's interest in a given suit. It is likely that it will call for the suit with the most interest should it be given the chance.
    //It is a boolean, as they have the choice to not call.
    public boolean call() {
        Random rand = new Random();
        getBestSuit();
        if (clubScore == 11 || spadeScore == 11 || heartScore == 11 || diamondScore == 11) {
            if (rand.nextDouble() < 0.25) {
                System.out.println(name + " >> I would like to go alone.");
                System.out.println("The AI (" + name + ") has the most confidence in " + getBestSuit() + ", and wishes to play it.");
                return true;
            }

        }


        //The player will get first pick on what suit they would like to play, as it seems unfun to allow the AIs to pick 3/4ths of the time.
        if(rand.nextDouble() < 0.5) {
            System.out.println( name + " >> I would like to play that suit.");
            return true;
        } else {
            System.out.println(name + " >> Pass.");
            return false;
        }
        //We assume that no call was made.
    }

    public Game.Card.Suit getBestSuit() {
        for (Card personalCard : hand) {
            switch (personalCard.getSuit()) {
                case CLUBS:
                    if (personalCard.getSuit().getColor() == Card.Suit.Color.BLACK) {
                        clubScore += 1;
                        if (personalCard.getValue() == Card.Value.QUEEN) {
                            clubScore += 3;
                        } else if (personalCard.getValue() == Card.Value.KING) {
                            clubScore += 4;
                        } else if (personalCard.getValue() == Card.Value.ACE) {
                            clubScore += 2;
                        }
                        if (personalCard.getValue() == Card.Value.JACK) {
                            clubScore += 5;
                        }
                    }
                    break;
                case SPADES:
                    if (personalCard.getSuit().getColor() == Card.Suit.Color.BLACK) {
                        spadeScore += 1;
                        if (personalCard.getValue() == Card.Value.QUEEN) {
                            spadeScore += 3;
                        } else if (personalCard.getValue() == Card.Value.KING) {
                            spadeScore += 4;
                        } else if (personalCard.getValue() == Card.Value.ACE) {
                            spadeScore += 2;
                        }
                        if (personalCard.getValue() == Card.Value.JACK) {
                            spadeScore += 5;
                        }
                    }
                    break;
                case HEARTS:
                    if (personalCard.getSuit().getColor() == Card.Suit.Color.RED) {
                        heartScore += 1;
                        if (personalCard.getValue() == Card.Value.QUEEN) {
                            heartScore += 2;
                        } else if (personalCard.getValue() == Card.Value.KING) {
                            heartScore += 4;
                        } else if (personalCard.getValue() == Card.Value.ACE) {
                            heartScore += 3;
                        }
                        if (personalCard.getValue() == Card.Value.JACK) {
                            heartScore += 5;
                        }
                    }
                    break;
                case DIAMONDS:
                    if (personalCard.getSuit().getColor() == Card.Suit.Color.RED) {
                        diamondScore += 1;
                        if (personalCard.getValue() == Card.Value.QUEEN) {
                            diamondScore += 2;
                        } else if (personalCard.getValue() == Card.Value.KING) {
                            diamondScore += 4;
                        } else if (personalCard.getValue() == Card.Value.ACE) {
                            diamondScore += 3;
                        }
                        if (personalCard.getValue() == Card.Value.JACK) {
                            diamondScore += 5;
                        }
                    }
                    break;
            }
        }
        String highestScore = "";
        int highestValue = -1;

        if (clubScore > highestValue) {
            highestValue = clubScore;
            highestScore = "Clubs";
            bestSuit = Card.Suit.CLUBS;
        }
        if (spadeScore > highestValue) {
            highestValue = spadeScore;
            highestScore = "Spades";
            bestSuit = Card.Suit.SPADES;
        }
        if (heartScore > highestValue) {
            highestValue = heartScore;
            highestScore = "Hearts";
            bestSuit = Card.Suit.HEARTS;
        }
        if (diamondScore > highestValue) {
            highestValue = diamondScore;
            highestScore = "Diamonds";
            bestSuit = Card.Suit.DIAMONDS;
        }
        return bestSuit;
    }




    //Name the same as the previous call function, this is used for when it comes to passing/picking up a certain card.
    public boolean call(Card c) {
        if(bestSuit == c.getSuit()) {

        }
        return false;
    }




}




