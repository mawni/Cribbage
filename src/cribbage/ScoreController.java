package cribbage;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class ScoreController {

    public ScoreController() {}

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
            //add points to score (visual update to onscreen is done within addScorePoints() method
            Cribbage.getInstance().addScorePoints(playerNum, starterRule.getPoints());
            //do the log
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), starterRule.getPoints(), "starter", starterRule.getCards());
        }
    }

    // the actual play of a round
    public void round(int playerNum, Hand hand){
        //relevant rules here: RunsRule and PairsRule. Hand passed in will be the segment of cards currently on board

        //factory to instantiate all relevant rules for the round
        ScoreRule runsRule = RuleFactory.getInstance().getRule("run");
        ScoreRule pairsRule = RuleFactory.getInstance().getRule("pair");

        //if the card just played on segment in round creates a 'run'
        if (runsRule.checkRule(hand, /*some vairable to indicate this is the run calculation during play */)) {
            //note for any given move during the round, only one run can may ever be scored. this is different to during the show
            //update score in cribbage
            Cribbage.getInstance().addScorePoints(playerNum, runsRule.getPoints());
            //do the log
            Log.getInstance().scored(playerNum, Cribbage.getInstance().getScore(playerNum), runsRule.getPoints(), runsRule.getType());
        }

        //if the card just played on segment in round creates a pair
        if (pairsRule.checkRule(hand, /*some vairable to indicate this is the run calculation during play */)) {
            //note for any given move during the round, only one pair can may ever be scored based on recently played cards (in order)
            //update score in cribbage
            Cribbage.getInstance().addScorePoints(playerNum, pairsRule.getPoints());
            //do the log
            Log.getInstance().scored(playerNum, Cribbage.getInstance().getScore(playerNum), pairsRule.getPoints(), pairsRule.getType());
        }
    }

    //the show i.e. the end of a round
    public void show(int playerNum, Hand hand){
        //factory to instantiate all relevant rules for the show
        ScoreRule fifteensRule = RuleFactory.getInstance().getRule("fifteen");
        ScoreRule runsRule = RuleFactory.getInstance().getRule("run");
        ScoreRule pairsRule = RuleFactory.getInstance().getRule("pair");
        ScoreRule flushRule = RuleFactory.getInstance().getRule("flush");
        ScoreRule jackRule = RuleFactory.getInstance().getRule("jack");

        //logic to check which scores are relevant. add to arraylist of scores.
        // done by calling methods of factory-created score classes

        if (fifteensRule.checkRule(hand)){
            //there can be multiple fifteen rule scorings found. but type is always 'fifteen'
            ArrayList<Hand> scoringFifteens = fifteensRule.getCards();
            //todo check getCards() method to ensure it returns list sorted in hand order? (alphabetic order of the canonical representation)
            for (Hand item : scoringFifteens){
                //update score
                Cribbage.getInstance().addScorePoints(playerNum, fifteensRule.getPoints());
                //do the log
                Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), fifteensRule.getPoints(), fifteensRule.getType(), item);
            }
        }
        if (runsRule.checkRule(hand, /*some vairable to indicate this is the run calculation during play */)){
            //there can be mulitple run rule scorings found of different type run3,run4,run5
            //todo checkRule() here should somewhere create an array list of scoring cards sets
            ArrayList<Hand> scoringRuns = runsRule.getCards();
            for (Hand item : scoringRuns){
                //update score
                Cribbage.getInstance().addScorePoints(playerNum, runsRule.getPoints(item.getNumberOfCards()));
                //pass in number of cards to get the specific number of points for run3,run4,run5
                //do the log
                Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), runsRule.getPoints(item.getNumberOfCards()), runsRule.getType(item.getNumberOfCards()), item);
                //pass in number of cards to get the specific number of points for run3,run4,run5
            }
        }
        if (pairsRule.checkRule(hand, /*some vairable to indicate this is the run calculation during play */)){
            //there can be mulitple pair rule scorings found
            //todo checkRule() here should somewhere create an array list of scoring cards sets
            ArrayList<Hand> scoringpairs = pairsRule.getCards();
            for (Hand item : scoringPairs){
                //update score
                Cribbage.getInstance().addScorePoints(playerNum, pairsRule.getPoints(item.getNumberOfCards()));
                //pass in number of cards to get the specific number of points for run3,run4,run5
                //do the log
                Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), pairsRule.getPoints(item.getNumberOfCards()), pairsRule.getType(item.getNumberOfCards()), item);
                //pass in number of cards to get the specific number of points for run3,run4,run5
            }
        }
        if (flushRule.checkRule(hand)){
            //there can only be one flush rule scoring found e.g. either all 4 cards in hand same suit OR 4 hand cards + starter card same suit
            //update score
            Cribbage.getInstance().addScorePoints(playerNum, flushRule.getPoints()); //todo check that flush changes it's points attribute if flush is flush of 4 vs flush of 5
            //do the log
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), flushRule.getPoints(), flushRule.getType(), item);
            //todo verify that flushRule changes its string attribute 'type' based on whether it's a flush4 of flush5
        }
        if (jackRule.checkRule(hand)){
            //there can only be one jack rule scoring found
            //update score
            Cribbage.getInstance().addScorePoints(playerNum, jackRule.getPoints());
            //do the log
            //int playerNum, int newScore, int newPoints, String pointType, Hand scoringCards
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), jackRule.getPoints(), jackRule.getType(), jackRule.getCards());
        }
    }
}