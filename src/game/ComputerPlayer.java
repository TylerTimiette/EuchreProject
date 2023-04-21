package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer  {

    public String name;
    public ArrayList<Card> hand;
    int clubScore = 0;
    int spadeScore = 0;
    int heartScore = 0;
    int diamondScore = 0;
    int highestValue = -1;
    Card.Suit bestSuit;
    Round round;

    public ComputerPlayer(ArrayList<Card> hand, String name, Round round) {
        this.hand = hand;
        this.name = name;
        this.round = round;
    }


    public Card bestViableCard() {
        Card topCard = hand.get(0);
        RoundData data = round.getData();
        HashMap<String, Card> cardMap = data.getCardMap();
        try {
            boolean inDemand = false;
            //Time for some heavy logic.

            //This is for our personal hand.
            for (Card personalCard : hand) {
                //Check if we have a card from the played suit. If we do, we must play it.
                if (personalCard.getSuit().equals(data.getSuit())) {
                    //Compare to the tabled card. Additionally set a flag that we cannot play trump or offsuit
                    inDemand = true;

                    //System.out.println(name + " has a card of the same suit.");

                    //THIS IS FOR THE CARDS CURRENTLY ON THE TABLE!!!
                    for (Map.Entry<String, Card> tabledCard : cardMap.entrySet()) {
                        if (personalCard.getNumericValue() > tabledCard.getValue().getNumericValue()) {
                            //A big bump in viability is provided because we have a higher value card of a suit that is in-demand.
                            personalCard.increaseViability(4);
                        } else {
                            //We still want to use the card, as we are forced to. Providing a bump in viability is the best way to do that.
                            personalCard.increaseViability(1);
                        }
                    }
                    break;
                }
            }

            boolean hasTrump = false;
            //We do not have a card that is in-demand.
            if (!inDemand) {
                //System.out.println(name + " does not have an in-demand card.");
                for (Card personalCard : hand) {
                    //Trump is in hand.
                    if (personalCard.getSuit().equals(data.getTrump()) || (personalCard.getValue() == Card.Value.JACK && personalCard.getSuit().getColor() == data.getTrump().getColor())) {
                        //Don't you love nested for-loops?
                        hasTrump = true;
                        for (Map.Entry<String, Card> tabledCard : cardMap.entrySet()) {
                            if ((tabledCard.getValue().getSuit().equals(data.getTrump()) && personalCard.getNumericValue() > tabledCard.getValue().getNumericValue())) {
                                //The enemy CPUs will have the same name. This is so that you don't teamkill.
                                if (!(tabledCard.getValue().holder().contains(personalCard.holder().substring(0, personalCard.holder().length() - 1)))) {
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
                                break;
                            }
                        }
                        break;
                    }
                }

                if (!hasTrump) {
                    Random random = new Random();
                    int rand = random.nextInt(hand.size());
                    Card randomCard = hand.get(rand);
                    System.out.println(name + " plays the " + randomCard.getValue() + " of " + randomCard.getSuit());
                    return randomCard;
                }
            }

            //System.out.println("Decision is being made...");
            for (Card personalCard : hand) {
                if (topCard.getViabilityScore() <= personalCard.getViabilityScore())
                    topCard = personalCard;
            }
            if (data.getHighestValue().getNumericValue() < topCard.getNumericValue()) {
                data.setHighestValue(topCard);
                if ((data.getSuit().equals(topCard.getSuit()) || topCard.getSuit().equals(data.getTrump())) || topCard.getSuit().getColor().equals(data.getTrump().getColor())) {
                    System.out.println("A new highcard has been set!");
                    if (name.contains("PLAYER2")) {
                        data.setWinner(true);
                    } else {
                        data.setWinner(false);
                    }
                }
            }
            //Very rarely there is an exception generated when the inputs are too fast. This covers for that. It's because of both garbage collection and a string that it thinks is null.
            //(The string is not null)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topCard;
    }


    public void play() {
        Card cardPlayed = bestViableCard();
        System.out.println("The bot " + name + " plays the " + cardPlayed.getValue() + " of " + cardPlayed.getSuit() + "!");
        hand.remove(bestViableCard());
        round.getData().getCardMap().put(name, cardPlayed);
    }




    //This function dictates an AI's interest in a given suit. It is likely that it will call for the suit with the most interest should it be given the chance.
    //It is a boolean, as they have the choice to not call.
    public boolean call() {
        Random rand = new Random();
        clubScore = 0;
        spadeScore = 0;
        heartScore = 0;
        diamondScore = 0;
        getBestSuit();
        System.out.println(name + " AI is deciding.");
        //The player will get first pick on what suit they would like to play, as it seems unfun to allow the AIs to pick 3/4ths of the time.
        if(rand.nextDouble() < 0.5) {
            System.out.println( name + " >> I would like to play " + getBestSuit());
            round.getData().setTrump(getBestSuit());
            return true;
        } else {
            System.out.println(name + " >> Pass.");
            return false;
        }
        //We assume that no call was made.
    }

    public Card.Suit getBestSuit() {
        for (Card personalCard : hand) {
            switch (personalCard.getSuit()) {
                case CLUBS -> {
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
                }
                case SPADES -> {
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
                }
                case HEARTS -> {
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
                }
                case DIAMONDS -> {
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
                }
            }
        }

        if (clubScore > highestValue) {
            highestValue = clubScore;
            bestSuit = Card.Suit.CLUBS;
        }
        if (spadeScore > highestValue) {
            highestValue = spadeScore;
            bestSuit = Card.Suit.SPADES;

        }
        if (heartScore > highestValue) {
            highestValue = heartScore;
            bestSuit = Card.Suit.HEARTS;
        }
        if (diamondScore > highestValue) {
            highestValue = diamondScore;
            bestSuit = Card.Suit.DIAMONDS;
        }
        //System.out.println(name + " HIGHEST SCORE IN: " + highestScore + " with score of " + highestValue);
        return bestSuit;
    }




    //Name the same as the previous call function, this is used for when it comes to passing/picking up a certain card.
    public boolean call(Card c) {
        //If our best suit is high
        if(bestSuit == c.getSuit()) {
            //Confidence scoring
            if(highestValue > 9) {
                replaceCard(c);
                return true;
                //They still think about it.
            } else {
                Random r = new Random();
                if(r.nextDouble() >= 0.49) {
                    replaceCard(c);
                    round.getData().setTrump(c.getSuit());
                    return true;
                } else
                    System.out.println("The bot " + name + " passes.");
            }
        } else {
            System.out.println("The bot " + name + " passes.");
            return false;
        }
        return false;
    }



    public boolean replaceCard(Card c) {
        System.out.println("The bot " + name+ " would like to play this suit.");
        System.out.println("Selecting card to replace...");
        boolean b = false;
        for(Card card : hand) {
            //System.out.println(card.getSuit() + " of " + card.getValue() + " num val = " + card.getNumericValue());
            if(c.getNumericValue() > card.getNumericValue()) {
                if(c.getSuit().equals(card.getSuit()) || c.getSuit().getColor().equals(card.getSuit().getColor())) {
                    //We don't want to replace a jack of a trump suit.
                    if(card.getValue().equals(Card.Value.JACK)) {
                        continue;
                    }
                }
                //Replace card.
                //System.out.println("Replacing " + card.getSuit() + " of " + card.getValue() + " num val = " + card.getNumericValue() + " for " + c.getSuit() + " of " + c.getValue());
                hand.set(hand.indexOf(card), c);
                b = true;
                break;
                //We don't announce which card we put down
            }
        }
        return b;
    }




    public String getName() {
        return name;
    }
}




