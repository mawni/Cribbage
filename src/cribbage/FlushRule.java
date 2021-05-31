package cribbage;

import ch.aplu.jcardgame.*;

public class FlushRule extends ScoreRule {
    public static final String TYPE = "flush";
    //todo: scoring - all four cards in the hand are of the same suit (flush4) earns 4 points
    // additional point if the starter card is also of that suit (flush5)
    // note this is only checked in the Show

    public FlushRule() {
        setType(TYPE);
    }

    public int getScore() {
        int score = 0;

        // conditional to check what kind of flush
        // change value of score based on flush kind

        return score;
    }

    // method to check kind of flush? e.g. getFlush()
    // would check how many cards are in the flush and return flush type based on this
    // i.e. returns flush4 or flush5


    public boolean checkRule(Hand starterHand) {
        boolean result;
        int flushLength;
        starterHand.getNumberOfCardsWithSuit(SPADES);
        starterHand.getNumberOfCardsWithSuit(DIAMONDS);
        starterHand.getNumberOfCardsWithSuit(HEARTS);
        starterHand.getNumberOfCardsWithSuit(CLUBS);
    
    }
        
        // create an arraylist of suits in the hand?
        // alternatively check size of suits in hand
        // could use method getNumberOfCardsWithSuit() from Hand.class potentially
        // if size is 4 or 5, return true
        // perhaps check the size in a different method

}
