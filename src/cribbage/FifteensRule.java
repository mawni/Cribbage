package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class FifteensRule extends ScoreRule {
    public static final String TYPE = "fifteen";

    public FifteensRule(){
        setPoints(2);
        setType(TYPE);
    }

    //todo parse in the hand
    public int getScore() {
        int score = 0;
        //for each set of 15
        return score;
    }
    
    @Override
    public boolean checkRule(Hand hand) {
        return false;
    }
    //checks if there are cards that equate to 15
    public boolean checkRule(Hand hand, Card starterCard) {
        // do smth with the hand 
        if () {
            return true;
        }
        else {
            return false;
        }
    }
}
