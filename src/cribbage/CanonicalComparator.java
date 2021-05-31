package cribbage;

import ch.aplu.jcardgame.Hand;
import java.util.Comparator;

//this class compares two hands
//first it checks which hand has more cards. run3 is logged before run4 for example. pair2 is logged before pair3 before pair4
//if hands are same length, they are compared based on canonical order
public class CanonicalComparator implements Comparator<Hand> {
    @Override
    public int compare(Hand o1, Hand o2) {
        //first save the length of each hand
        int l1 = o1.getNumberOfCards();
        int l2 = o2.getNumberOfCards();

        //save the canonical form of each hand
        String s1 = Cribbage.getInstance().canonical(o1);
        String s2 = Cribbage.getInstance().canonical(o2);

        if (l1 < l2){
            return -1; //in sorting, hand 1 should precede hand 2
        } else if (l1 > l2){
            return 1; //in sorting, hand 2 should precede hand 1
        } else /* l1==l2 */ {
            return s1.compareTo(s2);
            // -ve integer  = o1 < o2   --> o1 (i.e. s1) lexicographically precedes o2 (i.e. s2)
            // zero         = o1 == o2  --> the strings are equal
            // +ve integer  = o1 > o2   --> o1 lexicographically follows o2
        }
    }
    /* EXPLANATION
    Collections.sort does ascending order.
    For printing our log parts for scoring, it would iterate through an array list of cards from first item to last.
    This means we want the smallest hands first (e.g. run2 before run3). if hands are same length, we want alphabetical order (smallest 1st)
     */
}
