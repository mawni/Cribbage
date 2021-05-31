package cribbage;

import ch.aplu.jcardgame.*;

//todo: implement pairs rule
// can have pair2, pair3, pair4
// note that pair logic is different for show vs play
// the Play: pairs must be sequential
// the Show: pairs don't have to be sequential and includes starter card

public class PairsRule extends ScoreRule {
    public static final String TYPE = "pair";

    public PairsRule() {
        setType(TYPE);
    }

    @Override
    public int getPoints() {
        int score = 0;
        //check the pairs somewhere
        // if using a checkPairs method, conditional can check this to set score accordingly
        return score;
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

    // potential method: checkPairs()
    // use this to check what kind of pairs are in hand
    // return pair type

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

    // for the Show: pairs do not have to be sequential, include starter card hand
    public boolean checkPairShow(Hand starterHand) {
        //todo implement
        //use the methods in Hand that can check the pairings
        // methods - getPairs(), getTrips(), getQuads()
        // potentially use another method to check what kind of pair? eg checkPairs
        return true;
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
    public boolean checkPairX(Hand hand, int pairLength, Card lastPlayed){
        //pair length can be 2-4 i.e. pair, triple, quadruple
        Hand[] arrPair = hand.extractSequences(pairLength);
        if (arrPair.length != 0){ //if at least one X-card pair was found
            int handWithCard = checkContainsCard(arrPair, lastPlayed);
            if (handWithCard != -1){
                switch (pairLength){
                    case 2: //pair
                        setPoints(pairLength); //2 points for a pair
                        break;
                    case 3: //triple
                        setPoints(pairLength*2); //6 points for a triple
                        break;
                    case 4: //quadruple
                        setPoints(pairLength*3); //12 points for a quadruple
                        break;
                }
                setCards(arrPair[handWithCard]);
                setType(TYPE+pairLength);
                return true;
            }
        }
        return false;
    }
}
