package cribbage;

import ch.aplu.jcardgame.*;

//todo: implement pairs rule
// can have pair2, pair3, pair4
// note that pair logic is different for show vs play
// the Play: pairs must be sequential
// the Show: pairs don't have to be sequential and includes starter card

public class PairsRule extends ScoreRule {
    public static final String TYPE = "pair";
    
    public PairsRule() {
        setType(TYPE);
    }

    @Override
    public int getPoints() {
        int score = 0;
        //check the pairs somewhere
        // if using a checkPairs method, conditional can check this to set score accordingly
        return score;
    }

    @Override
    public boolean checkRule(Hand hand){
        System.out.println("PairsRule: the wrong checkRule() method has been called");
        return false;
    }

    @Override
    public boolean checkRule(Hand hand, String playOrShow){
        boolean result;
        switch (playOrShow) {
            case ScoreController.strategyPlay: // the play during a round
                result = checkPairPlay(hand);
                break;
            case ScoreController.strategyShow: // the show at end of a round
                result = checkPairShow(hand);
                break;
            default: //something not working
                System.out.println("Error! Invalid playOrShow String input");
                result = false;
                break;
        }
        return result;
    }
    
    // potential method: checkPairs()
    // use this to check what kind of pairs are in hand
    // return pair type

    // for the Play: pairs must be sequential
    public boolean checkPairPlay(Hand hand) {
        //use the methods in Hand that can check the pairings
        // methods - getPairs(), getTrips(), getQuads()
        // potentially use another method to check what kind of pair? eg checkPairs
        return true;
    }

    // for the Show: pairs do not have to be sequential, include starter card hand
    public boolean checkPairShow(Hand starterHand) {
        //use the methods in Hand that can check the pairings
        // methods - getPairs(), getTrips(), getQuads()
        // potentially use another method to check what kind of pair? eg checkPairs
        return true;
    }
}
