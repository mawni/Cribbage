package cribbage;

import ch.aplu.jcardgame.*;

public class FlushRule extends ScoreRule {
    public static final String TYPE = "flush";
    //todo: scoring - all four cards in the hand are of the same suit (flush4) earns 4 points
    // additional point if the starter card is also of that suit (flush5)

    public FlushRule() {
        setType(TYPE);
    }

    //note only one flush can be achieved in a hand
    public boolean checkRule(Hand starterHand) {
        Cribbage.getInstance()
        boolean result;
        int flushLength;
        starterHand.ex
        starterHand.extractCardsWithSuit(Cribbage.Suit.SPADES);
        starterHand.extractCardsWithSuit(Cribbage.Suit.DIAMONDS);
        starterHand.extractCardsWithSuit(Cribbage.Suit.HEARTS);
        starterHand.extractCardsWithSuit(Cribbage.Suit.CLUBS);
    
    }
}
