package cribbage;

import ch.aplu.jcardgame.*;

// if starter is a jack, dealer gets 2 points
public class StarterRule extends ScoreRule {
    public static final String TYPE = "starter";

    public StarterRule(){
        setPoints(2);
        setType(TYPE);
    }
    
    //note the superclass ScoreRule.getPoints() method can work as is, without override
    
    // takes in the starter card and checks if it's a jack. jack rank == 11
    @Override
    public boolean checkRule(Hand hand) {
        if (hand.getCardList().get(0).getRank() == Cribbage.Rank.JACK) {
            setCards(hand);
            return true;
        }
        else {
            return false;
        }
    }
}