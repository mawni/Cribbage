package cribbage;

import ch.aplu.jcardgame.Hand;
import java.util.Comparator;

//this class exists to sort hands based on canonical order.
public class CanonicalComparator implements Comparator<Hand> {
    @Override
    public int compare(Hand o1, Hand o2) {
        String s1 = Cribbage.getInstance().canonical(o1); //get canonical form of hand1
        String s2 = Cribbage.getInstance().canonical(o2); //get canonical form of hand2

        return s1.compareTo(s2);
        // -ve integer  = o1 < o2   --> o1 (i.e. s1) lexicographically precedes o2 (i.e. s2)
        // zero         = o1 == o2  --> the strings are equal
        // +ve integer  = o1 > o2   --> o1 lexicographically follows o2
    }
    /* EXPLANATION
    Collections.sort does ascending order.
    For printing our log parts for scoring, it would iterate through an array list of cards from first item to last.
    This means we want the alphabetical first hand to be first item in arraylist (i.e. the smallest)
     */
}
