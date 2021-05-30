package cribbage;

import ch.aplu.jcardgame.*;

public class RunsRule implements ScoreRule {
    //todo: implement runs scoring
    // note different logic during show and play
    // the Play: can be run of 3, 4, 5, 6, 7
    // the Show: can be run of 3, 4, 5, and include starter card
    // runs in the Play would need to be check every time a card is played
    // needs to reset every segment?

    public RunsRule() {}

    public int getScore() {
        int score = 0;
        // for each incrementing/types of runs score++
        // use conditional to check what kind of run, perhaps using getRun() method
        // then set score based on run type
        return score;
    }

    // method to check what kind of run potentially eg getRun()
    // this method would return run type: run3, run4, run5, run6, run7
    // then use this to assign score in getScore()

    // for the Play: run of 3 to 7
    public boolean checkRule(Hand hand) {
        // create sorted arraylist of cards based on card value (cardValue() method?)
        // check order - maybe use sequence() method from Hand class
        // if there is a broken sequence, no points given
        // check what kind of run it is, score is based on run length
        // create new method to check the kind of run?
        // return true if there is a sequence
    }

    // for the Show: run of 3 to 5, incl starter card
    public boolean checkRule(Hand starterHand) {
        // create sorted arraylist of cards based on card value (cardValue() method?)
        // check order - maybe use sequence() method from Hand class
        // if there is a broken sequence, no points given
        // check what kind of run it is, score is based on run length
        // create new method to check the kind of run?
        // return true if there is a sequence
    }
}
