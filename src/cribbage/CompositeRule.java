package cribbage;

import java.util.ArrayList;

public class CompositeRule implements ScoreRule {
    //todo
    //create way to get instance and to get the ScoreRule

    //store rules to apply
    private ArrayList rules = new ArrayList<>();


    public int getScore() {
        int score = 0;
        // for each score rule in arraylist, add the required scores to counter
        //check how the type works
        for (TYPE rule : rules) {
            score += rule.getScore();
            //remove if need to
        }
        return score;
    }
}
