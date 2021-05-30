package cribbage;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class ScoreController {

    public ScoreController() {}

    //private ArrayList<ScoreRule> scoreList = new ArrayList<>();
    //whenever the scoreStrat methods find a score, add it to this array list.
    //will need to be sorted at the end based on some rules mention in the logging

    //maybe controller shouldnt be doing this logic, maybe do within the start/round/show methods

    /*each score rule class can be based on an abstract class
    public abstract class ScoreRule {
        private final int points; //declared within constructor of subclass or in-line, says how much points whatever rule provides
        private Hand cards; //initialised to null. used to return cards relevant for score if score is detected

        public abstract boolean checkRule(Hand hand){}; //subclass logic to check if rule is detected
        public int getPoints(){};
        public Hand getCards(){};
    }
    */

    //eventually strategy pattern vibes might be useful for changing of scoreStrat
    public void run(Hand hand, int playerNum, int scoreStrat) {
        switch (scoreStrat) { // the start of a round
            case 1: // the start of a round
                start(playerNum, hand);
                break;
                //playerNum is needed to eventually log the scores scored
            case 2: // the actual play of a round
                round(playerNum, hand);
                break;
            case 3: //the show i.e. the end of a round
                show(playerNum, hand);
                break;
            default: //something not working
                System.out.println("Error! Invalid score strategy choice");
                break;
        }
    }

    // the start of a round
    public void start(int playerNum, Hand hand) {
        //only relevant rule here is the StarterRule (i.e. if Jack is dealt as starter card)
        //when ScoreController.run() called in Cribbage, only the starter card will be passed (i.e. hand will be 1 card)

        //factory to instantiate all relevant rules for the start
        ScoreRule starterRule = RuleFactory.getInstance().getRule("starter");

        //if a jack is the starter card
        if (starterRule.checkRule(hand)) {
            //int playerNum, int newScore, int newPoints, String pointType, Hand scoringCards
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum)+starterRule.getPoints(), starterRule.getPoints(), "starter", starterRule.getCards());
        }
    }

    // the actual play of a round
    public void round(int playerNum, Hand hand){
        ArrayList</*some kind of score parent class*/> scoreList = new ArrayList<>();
            //it'll need point type

        //factory to instantiate all relevant rules for the round
        //rule with player saying go is maybe included here (maybe). maybe go is dedicated something

        //logic to check which scores are relevant.
        //for each relevant one, add the point type to arraylist of scores. done by calling methods of factory-created score classes
        //potentially might not need to take in the cards for log printing. means you just need to store the points and point type
    }

    //the show i.e. the end of a round
    public void show(int playerNum, Hand hand){
        ArrayList</*some kind of score parent class*/> scoreList = new ArrayList<>();
            //it'll need point type, cards relevant

        //factory to instantiate all relevant rules for the show
        // need to think about multiple instantiations etc.?

        //logic to check which scores are relevant. add to arraylist of scores.
        // done by calling methods of factory-created score classes

    }


    // take n all

}