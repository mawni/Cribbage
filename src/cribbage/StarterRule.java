package cribbage;

import ch.aplu.jcardgame.*;

// if starter is a jack, dealer gets 2 points
public class StarterRule extends ScoreRule {
    public StarterRule() {}

    // returns the score worth of the rule
    public int getScore() {
        return 2;
    }

    public boolean checkRule(Hand hand) {
        return false;
    }

    // takes in the starter card and checks if it's a jack
    // (jack rank == 11)
    public boolean checkRule(Card starterCard) {
        if (starterCard.getRank() == JACK) {
            return true;
        }
        else {
            return false;
        }
    }
}