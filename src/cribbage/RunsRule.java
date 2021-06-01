package cribbage;

import ch.aplu.jcardgame.*;

public class RunsRule extends ScoreRule {

    public static final String TYPE = "run";

    public RunsRule() {
        setType(TYPE);
    }

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
            case ScoreController.STRATEGY_PLAY: // the play during a round
                result = checkRulePlay(hand);
                break;
            case ScoreController.STRATEGY_SHOW: // the show at end of a round
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
        //note that inherently only one run can be scored on any given move
        //this would be the maximum run. hence why if-else chain starts from run7
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
        //hand size is 5 (including starter card), so it's only possible to score one run
        //if you had a run of 3, you can't have a second unique run.
        //if you did have another run, it would just make the original 3 one longer.
        //so we will search from the highest run and move down

        if (checkRunX(starterHand, 5)){
            //run5 found
            return true;
        } else if (checkRunX(starterHand, 4)) {
            //run4 found
            return true;
        } else {
            return checkRunX(starterHand, 3);
            //if run3 found, it'll return true, all processing done.
            //if run3 not found, it'll return false
        }
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

    //this method is used to check run3,4,5 during the show.
    public boolean checkRunX(Hand hand, int runLength){
        Hand[] arrRun = hand.extractSequences(runLength);
        if (arrRun.length != 0){ //if an X-card run was found
            setCards(arrRun[0]);
            /* Should the extractSequences() method find two runs of 3 for example,
               since a hand (inc. starter card) is only 5 cards, the two run3's found
               would have overlap. This means it would constitute a run4 or run5.
               The checkRuleShow() method is written such that longer runs are caught
               first, so this issue is solved.
               TLDR: theoretically, this method will never be called where arrRun.length>1
                     because the larger runs are always caught first, preventing smaller
                     checkRunXShow()'s being executed
            */
            setPoints(runLength);
            setType(TYPE+runLength);
            return true;
        }
        return false;
    }
}
