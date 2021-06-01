package cribbage;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.io.IOException;
import java.util.ArrayList;

public class ScoreController {
    public static final String strategyStart = "start";
    public static final String strategyPlay = "play";
    public static final String strategyShow = "show";

    public ScoreController() {}

    //eventually strategy pattern vibes might be useful for changing of 'choice' / strategy
    public static void run(Hand hand, int playerNum, String choice, Card starterCard) throws IOException {
        switch (choice) {
            case strategyStart: // the start of a round
                //if strategy is for start of game, the method parameter 'hand' be null. Only starterCard is needed
                hand = Cribbage.getInstance().makeHand();
                hand.insert(starterCard, false);
                start(playerNum, hand);
                break;
                //playerNum is needed to eventually log the scores scored
            case strategyPlay: // the actual play of a round
                //if strategy is for play of game, the method parameter starterCard isn't necessary
                play(playerNum, hand);
                break;
            case strategyShow: //the show i.e. the end of a round
                show(playerNum, hand, starterCard);
                break;
            default: //something not working
                System.out.println("Error! Invalid score strategy choice");
                break;
        }
    }

    // the start of a round
    private static void start(int playerNum, Hand hand) throws IOException {
        //only relevant rule here is the StarterRule (i.e. if Jack is dealt as starter card)
        //when ScoreController.run() called in Cribbage, only the starter card will be passed (i.e. hand will be 1 card)

        //factory to instantiate all relevant rules (only Starter Rule) for the start
        ScoreRule starterRule = RuleFactory.getInstance().getRule(StarterRule.TYPE);

        //if a jack is the starter card
        if (starterRule.checkRule(hand)) {
            //add points to score (visual update to onscreen is done within addScorePoints() method
            Cribbage.getInstance().addScorePoints(playerNum, starterRule.getPoints());
            //do the log
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), starterRule.getPoints(), starterRule.getType(), starterRule.getCards());
        }
    }

    // the actual play of a round
    private static void play(int playerNum, Hand hand) throws IOException {
        //relevant rules here: RunsRule and PairsRule. Hand passed in will be the segment of cards currently on board

        //factory to instantiate all relevant rules for the round
        ScoreRule runsRule = RuleFactory.getInstance().getRule(RunsRule.TYPE);
        ScoreRule pairsRule = RuleFactory.getInstance().getRule(PairsRule.TYPE);

        //if the card just played on segment in round creates a 'run'
        if (runsRule.checkRule(hand, strategyPlay)) {
            //note for any given move during the round, only one run can may ever be scored. this is different to during the show
            //update score in cribbage
            Cribbage.getInstance().addScorePoints(playerNum, runsRule.getPoints());
            //do the log
            Log.getInstance().scored(playerNum, Cribbage.getInstance().getScore(playerNum), runsRule.getPoints(), runsRule.getType());
        }

        //if the card just played on segment in round creates a pair
        if (pairsRule.checkRule(hand, strategyPlay)) {
            //note for any given move during the round, only one pair can may ever be scored based on recently played cards (in order)
            //update score in cribbage
            Cribbage.getInstance().addScorePoints(playerNum, pairsRule.getPoints());
            //do the log
            Log.getInstance().scored(playerNum, Cribbage.getInstance().getScore(playerNum), pairsRule.getPoints(), pairsRule.getType());
        }
    }

    //the show i.e. the end of a round
    private static void show(int playerNum, Hand hand, Card starterCard) throws IOException {
        //factory to instantiate all relevant rules for the show
        ScoreRule fifteensRule = RuleFactory.getInstance().getRule(FifteensRule.TYPE);
        ScoreRule runsRule = RuleFactory.getInstance().getRule(RunsRule.TYPE);
        ScoreRule pairsRule = RuleFactory.getInstance().getRule(PairsRule.TYPE);
        ScoreRule flushRule = RuleFactory.getInstance().getRule(FlushRule.TYPE);
        ScoreRule jackRule = RuleFactory.getInstance().getRule(JackRule.TYPE);

        //joining up hand and starterCard
        Hand handWithStarter = Cribbage.getInstance().makeHand();
        for (Card card : hand.getCardList()){
            handWithStarter.insert(card, false);
        }
        handWithStarter.insert(starterCard, false);

        //logic to check which scores are relevant. add to arraylist of scores.
        // done by calling methods of factory-created score classes

        if (fifteensRule.checkRule(handWithStarter)){
            //there can be multiple fifteen rule scorings found. but type is always 'fifteen'
            ArrayList<Hand> scoringFifteens = fifteensRule.getList();
            for (Hand item : scoringFifteens){
                //update score
                Cribbage.getInstance().addScorePoints(playerNum, fifteensRule.getPoints());
                //do the log
                Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), fifteensRule.getPoints(), fifteensRule.getType(), item);
            }
        }
        if (runsRule.checkRule(handWithStarter, strategyShow)){
            //there can only ever be one run: either run3,4,5 because a hand (inc. starter card) only has 5 cards
            //update score in cribbage
            Cribbage.getInstance().addScorePoints(playerNum, runsRule.getPoints());
            //do the log
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), runsRule.getPoints(), runsRule.getType(), runsRule.getCards());
        }
        if (pairsRule.checkRule(handWithStarter, strategyShow)){
            //there can be multiple pair rule scorings found in a hand
            ArrayList<Hand> scoringPairs = pairsRule.getList();
            for (Hand item : scoringPairs){
                int numOfCards = item.getNumberOfCards();
                //update score
                Cribbage.getInstance().addScorePoints(playerNum, PairsRule.pointsForXPair(numOfCards));
                //pass in number of cards to get the specific number of points for pair3,pair4,pair5
                //do the log
                Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), PairsRule.pointsForXPair(numOfCards), PairsRule.typeForXPair(numOfCards), item);
                //pass in number of cards to get the specific number of points and type for pair2,pair3,pair4
            }
        }
        if (flushRule.checkRule(hand, starterCard)){
            //there can only be one flush rule scoring found e.g. either all 4 cards in hand same suit OR 4 hand cards + starter card same suit
            //update score
            Cribbage.getInstance().addScorePoints(playerNum, flushRule.getPoints());
            //do the log
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), flushRule.getPoints(), flushRule.getType(), flushRule.getCards());
        }
        if (jackRule.checkRule(hand, starterCard)){
            //there can only be one jack rule scoring found
            //update score
            Cribbage.getInstance().addScorePoints(playerNum, jackRule.getPoints());
            //do the log
            Log.getInstance().handScored(playerNum, Cribbage.getInstance().getScore(playerNum), jackRule.getPoints(), jackRule.getType(), jackRule.getCards());
        }
    }
}