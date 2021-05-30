package cribbage;

import ch.aplu.jcardgame.*;

//todo: implement pairs rule
// can have pair2, pair3, pair4
// note that pair logic is different for show vs play
// the Play: pairs must be sequential
// the Show: pairs don't have to be sequential and includes starter card

public class PairsRule extends ScoreRule {
    public PairsRule() {
    }

    public int getScore() {
        int score = 0;
        //check the pairs somewhere
        // if using a checkPairs method, conditional can check this to set score accordingly
        return score;
    }

    // potential method: checkPairs()
    // use this to check what kind of pairs are in hand
    // return pair type

    // for the Play: pairs must be sequential
    public boolean checkRule(Hand hand) {
        //use the methods in Hand that can check the pairings
        // methods - getPairs(), getTrips(), getQuads()
        // potentially use another method to check what kind of pair? eg checkPairs
        return true;
    }

    // for the Show: pairs do not have to be sequential, include starter card hand
    public boolean checkRule(Hand starterHand) {
        //use the methods in Hand that can check the pairings
        // methods - getPairs(), getTrips(), getQuads()
        // potentially use another method to check what kind of pair? eg checkPairs
        return true;
    }
}
