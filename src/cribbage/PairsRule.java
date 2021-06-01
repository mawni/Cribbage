package cribbage;

import ch.aplu.jcardgame.*;

import java.util.ArrayList;
import java.util.Arrays;

//todo: implement pairs rule
// can have pair2, pair3, pair4
// note that pair logic is different for show vs play
// the Play: pairs must be sequential
// the Show: pairs don't have to be sequential and includes starter card

public class PairsRule extends ScoreRule {
    public static final String TYPE = "pair";
    private ArrayList<Hand> scoringCardsTemp = new ArrayList<Hand>();
        //will be used to store each card combination in current hand that satisfies a pair2,3,4 rule

    public PairsRule() {
        setType(TYPE);
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


    // for the Play: pairs must be sequential
    public boolean checkPairPlay(Hand hand) {
        Card lastPlayed = hand.getLast();

        if (checkPairX(hand, 4, lastPlayed)){
            //if a quadruple is found
            return true;
        } else if (checkPairX(hand, 3, lastPlayed)){
            //if a triple is found
            return true;
        } else {
            return checkPairX(hand, 2, lastPlayed);
            //if pair2 found, it'll return true, all processing done.
            //if not found, it'll return false
        }
    }

    // for the Show: pairs do not have to be sequential during the show. can have multiple
    public boolean checkPairShow(Hand starterHand) {
        // hand includes starter card --> total size = 5

        /* DISCLAIMER
            Currently I am not accounting for the potential overlap with pairs.
            For example if a hand has three unique 2's, this algorithm will basically return three pairs and one triple.
            //todo something to consider in the future.
         */

        boolean resultTracker = false;

        boolean gotQuads = checkPairX(starterHand, 4);
        boolean gotTriples = checkPairX(starterHand, 3);
        boolean gotPairs = checkPairX(starterHand, 2);
        if (gotQuads || gotTriples || gotPairs){ //if either method found anything, then continue
            resultTracker = true;
            scoringCardsTemp.sort(new CanonicalComparator());
            setList(scoringCardsTemp); //sends to the ScoreRule superclass variable
        }
        return resultTracker;
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

    //for use during play in the actual round
    public boolean checkPairX(Hand hand, int pairLength, Card lastPlayed){
        //pair length can be 2-4 i.e. pair, triple, quadruple
        Hand[] arrPair;
        switch (pairLength){
            case 4:
                arrPair = hand.extractQuads();
                break;
            case 3:
                arrPair = hand.extractTrips();
                break;
            default: //default is pairs i.e. pairLength = 2
                arrPair = hand.extractPairs();
        }
        if (arrPair.length != 0){ //if at least one X-card pair was found
            int handWithCard = checkContainsCard(arrPair, lastPlayed);
            if (handWithCard != -1){
                if (hand.get(hand.getNumberOfCards()-2).getRank() == lastPlayed.getRank()){
                    //this is a sanity check that the other pair card was played directly prior to lastPlayed

                    //sanity check for when it's a possible triple
                    if (pairLength>=3 && (hand.get(hand.getNumberOfCards()-3).getRank() != lastPlayed.getRank())){
                        return false;
                    }
                    //sanity check for when it's a possible quadruple
                    if (pairLength==4 && (hand.get(hand.getNumberOfCards()-4).getRank() != lastPlayed.getRank())){
                        return false;
                    }

                    setPoints(pointsForXPair(pairLength)); //this determines the points based on the size of the pair
                    setCards(arrPair[handWithCard]);
                    setType(typeForXPair(pairLength));
                    return true;
                }
            }
        }
        return false;
    }

    public static int pointsForXPair(int pairLength){
        //formula to calculate points from the pair length (pair2,3,4) ----> length * (length - 1) = points;
        // pair2 = 2*1 = 2pts
        // pair3 = 3*2 = 6pts
        // pair4 = 4*3 = 12pts
        return (pairLength * (pairLength-1));
    }
    public static String typeForXPair(int pairLength){
        return (TYPE+pairLength);
        //pair2, pair3, pair4
    }

    //this one is for use during the show
    public boolean checkPairX(Hand hand, int pairLength){
        //pair length can be 2-4 i.e. pair, triple, quadruple
        Hand[] arrPair;
        switch (pairLength){
            case 4:
                arrPair = hand.extractQuads();
                break;
            case 3:
                arrPair = hand.extractTrips();
                break;
            default: //default is pairs i.e. pairLength = 2
                arrPair = hand.extractPairs();
        }
        if (arrPair.length != 0){ //if at least one X-card pair was found
            scoringCardsTemp.addAll(Arrays.asList(arrPair));
            return true;
        }
        return false;
    }
}
