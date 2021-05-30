package cribbage;

import java.util.ArrayList;

public class CompositeRule implements ScoreRule {
    //todo call whenever many rules can be applied in 1 instance (show, crib etc.)

    //to store rules that apply
    private ArrayList<ScoreRule> rules = new ArrayList<>();

    Cribbage.getInstance().

    //gets the score of all the applicable rules
    public int getScore() {
        int score = 0;

        //for each score rule in ArrayList, add the required scores to counter
        for (ScoreRule rule : rules) {
            score += rule.getScore();
            //remove from Arraylist to clean up
            rules.remove(rule);
        }
        return score;
    }
}
