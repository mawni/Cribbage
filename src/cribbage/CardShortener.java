package cribbage;

import ch.aplu.jcardgame.Card;

import java.util.Map;
import static java.util.Map.entry;

public class CardShortener {
    //define some dictionaries/maps in-line for shortening suit and rank to format suitable for log file
    public static final Map<String, Character> suitDict = Map.ofEntries(
        entry("CLUBS", 'C'),
        entry("DIAMONDS", 'D'),
        entry("HEARTS", 'H'),
        entry("SPADES", 'S')
    );
    public static final Map<String, Character> rankDict = Map.ofEntries(
            entry("ACE", 'A'),
            entry("KING", 'K'),
            entry("QUEEN", 'Q'),
            entry("JACK", 'J'),
            entry("TEN", 'T'),
            entry("NINE", '9'),
            entry("EIGHT", '8'),
            entry("SEVEN",'7'),
            entry("SIX", '6'),
            entry("FIVE", '5'),
            entry("FOUR", '4'),
            entry("THREE", '3'),
            entry("TWO", '2')
    );

    public static String shortenCard(Card card){
        Character suitChar = suitDict.get(card.getSuit());
        Character rankChar = rankDict.get(card.getRank());
        return("" + suitChar + rankChar);
    }
}
