package cribbage;

import ch.aplu.jcardgame.*;

import java.util.Arrays;

public class RunsRule extends ScoreRule {
    //todo: implement runs scoring
    // note different logic during show and play
    // the Show: can be run of 3, 4, 5, and include starter card

    public static final String TYPE = "run";

    public RunsRule() {
        setType(TYPE);
    }

    // method to check what kind of run potentially eg getRun()
    // this method would return run type: run3, run4, run5, run6, run7
    // then use this to assign score in getScore()

    @Override
    public boolean checkRule(Hand hand){
        System.out.println("RunsRule: the wrong checkRule() method has been called");
        return false;
    }

    @Override
    public boolean checkRule(Hand hand, String playOrShow){
        boolean result;
        switch (playOrShow) {
            case ScoreController.strategyPlay: // the play during a round
                result = checkRulePlay(hand);
                break;
            case ScoreController.strategyShow: // the show at end of a round
                result = checkRuleShow(hand);
                break;
            default: //something not working
                System.out.println("Error! Invalid playOrShow String input");
                result = false;
                break;
        }
        return result;
    }

    // for the Play: run of 3 to 7
    public boolean checkRulePlay(Hand hand) {
        Card lastPlayed = hand.getLast();

        if (checkRunX(hand, 7, lastPlayed)){
            //if run 7 found
            return true;
        } else if (checkRunX(hand, 6, lastPlayed)){
            //if run 6 found
            return true;
        } else if (checkRunX(hand, 5, lastPlayed)){
            //if run 5 found
            return true;
        } else if (checkRunX(hand, 4, lastPlayed)){
            //if run 4 found 
            return true;
        } else {
            return checkRunX(hand, 3, lastPlayed);
            //if run3 found, it'll return true, all processing done.
            //if run3 not found, it'll return false
        }
    }

    // for the Show: run of 3 to 5, incl starter card
    public boolean checkRuleShow(Hand starterHand) {
        // create sorted arraylist of cards based on card value (cardValue() method?)
        // check order - maybe use sequence() method from Hand class
        // if there is a broken sequence, no points given
        // check what kind of run it is, score is based on run length
        // create new method to check the kind of run?
        // return true if there is a sequence
        //todo implement this
    }

    //this takes in an array of hands, and checks if any item in the array contains a card
    //returns the index of the hand in the hands array that has the card
    public int checkContainsCard(Hand[] hands, Card card){
        int index=0;
        for (Hand hand : hands){
            if (hand.contains(card)){
                return index;
            }
            index++;
        }
        return -1;
        //returns -1 if nothing found
    }

    public boolean checkRunX(Hand hand, int runLength, Card lastPlayed){
        Hand[] arrRun = hand.extractSequences(runLength);
        if (arrRun.length != 0){ //if at least one X-card run was found
            int handWithCard = checkContainsCard(arrRun, lastPlayed);
            if (handWithCard != -1){
                setCards(arrRun[handWithCard]);
                setPoints(runLength);
                setType(TYPE+runLength);
                return true;
            }
        }
        return false;
    }
}
