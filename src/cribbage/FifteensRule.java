package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class FifteensRule extends ScoreRule {
    public static final String TYPE = "fifteen";
    private ArrayList<Hand> scoringCardsTemp = new ArrayList<Hand>();
        //will be used to store each card combination in current hand that satisfies fifteen rule

    public FifteensRule(){
        setPoints(2);
        setType(TYPE);
    }



    //checks if there are cards that equate to 15
    @Override
    public boolean checkRule(Hand hand) {
        // 1. GET ALL SUBSETS
        //only way to do this is check all possible combinations of cards, then adding up value
        ArrayList<Hand> allSubsets = new ArrayList<Hand>();
        int length = hand.getNumberOfCards();
            //length should always = 5 since fifteens rule is only relevant during the show
        //add all 32 subsets to arraylist. uses a bit-based solution to get all combinations
        for (int i = 0; i < (1<<length); i++){
            //upper bound is 1 shifted to left bitwise by length
            Hand temp = Cribbage.getInstance().makeHand(); //temp hand to put subsets into
                //declaration is within for loop so that it resets each time
            for (int j = 0; j < length; j++) {
                if ((i & (1 << j)) > 0) {
                    temp.insert(hand.get(j), false); //insert jth card into temp
                }
            }
            allSubsets.add(temp);
        }

        boolean resultTracker = false;
        // 2. CALCULATE TOTALS OF EACH SUBSET, FINALISE IF ==15
        for (Hand subset : allSubsets){
            if (Cribbage.total(subset) == 15){
                //success! add to the results list
                subset.sort(Hand.SortType.POINTPRIORITY, false);
                    //sort the subset
                scoringCardsTemp.add(subset); //add subset to scoring sets
                resultTracker = true;
            }
        }
        if (resultTracker){
            scoringCardsTemp.sort(new CanonicalComparator());
            setList(scoringCardsTemp); //sends to the ScoreRule superclass variable
        }
        return resultTracker;
    }
}
