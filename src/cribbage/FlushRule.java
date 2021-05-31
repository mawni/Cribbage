package cribbage;

import ch.aplu.jcardgame.*;

public class FlushRule extends ScoreRule {
    public static final String TYPE = "flush";

    public FlushRule() {
        setType(TYPE);
    }

    @Override
    public boolean checkRule(Hand hand) {
        System.out.println("This version of checkRule(Hand hand) should not ever be needed for a call!");
        return false;
    }

    //note only one flush can be achieved in a hand
    @Override
    public boolean checkRule(Hand hand, Card starterCard) {
        boolean result = false;
        
        if (checkSuit(hand, starterCard, Cribbage.Suit.CLUBS)){
            return true;            
        } else if (checkSuit(hand, starterCard, Cribbage.Suit.DIAMONDS)){
            return true;            
        } else if (checkSuit(hand, starterCard, Cribbage.Suit.HEARTS)){
            return true;
        } else {
            return checkSuit(hand, starterCard, Cribbage.Suit.SPADES);
        }
    }
    
    //checks whether the hand and the starter card form a flush of given suit. handles all post processing
    public boolean checkSuit(Hand hand, Card starterCard, Cribbage.Suit suit){
        Hand cardsWithSuit = hand.extractCardsWithSuit(suit);
        if (cardsWithSuit.getNumberOfCards()==4){//if all 4 cards in hand are same suit
            if (starterCard.getSuit()==suit){ //if starter same suit
                setPoints(5);
                setType(TYPE+5);

                //joining up hand and starterCard
                Hand handWithStarter = Cribbage.getInstance().makeHand();
                for (Card card : hand.getCardList()){
                    handWithStarter.insert(card, false);
                }
                handWithStarter.insert(starterCard, false);
                
                setCards(handWithStarter);
                
                return true;
            }
            setPoints(4);
            setType(TYPE+4);
            setCards(hand);
            return true;
        }
        return false;
    }
}