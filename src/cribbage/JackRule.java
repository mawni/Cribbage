package cribbage;

import ch.aplu.jcardgame.*;

//if in their hand a player has Jack of same suit as the Starter card, they score 1 point
public class JackRule extends ScoreRule {

    public JackRule() {}

    // returns the score for the rule
    public int getScore() {
        return 1;
    }
   
    public boolean checkRule(Hand hand) {
        return false;
    }

    // check if hand has jack of same suit of starter
    public boolean checkRule(Hand hand, Card starterCard) {
        // getSuit() returns suit ENUM from card class
        // iterates through hand
        // check if card is a jack and then check if the suit is the same as starter card
        for (Card card : hand.getCardList()) {
            if ((card.getRank() == JACK) && (card.getSuit() == starterCard.getSuit())) {
                return true;
            }
        }
        return false;
    }
}

