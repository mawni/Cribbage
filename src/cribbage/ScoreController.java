package cribbage;

public class ScoreController {

        ArrayList</*some kind of score parent class*/> scoreList = new ArrayList<>();
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


        //eventualy strategy pattern vibes might be useful for changing of scoreStrat
        public void run(Hand hand, int playerNum, int scoreStrat){
            //could also be a 'case switch' statement
            if (scoreStrat==1){ // the start of a round
                start(hand);
                //playerNum is needed to eventually log the scores scored
            } else if (scoreStrat==2){ // the actual play of a round
                 round(hand);
            } else /*scoreStrat==3*/ { //the show i.e. the end of a round
                show(hand)
            }

            //sort the array list?
            //call the log methods(?) maybe this is done within the start/round/show methods, not sure yet
        }

        public void start(Hand hand){
            //factory to instantiate all relevant rules for the start

            if (StarterRule.checkRule(hand)){
                //use StarterRule.getPoints()
                //use StarterRule.getCards()
                //do the log function call
            }
        }

        public void round(Hand hand){
            ArrayList</*some kind of score parent class*/> scoreList = new ArrayList<>();
                //it'll need point type

            //factory to instantiate all relevant rules for the round
            //rule with player saying go is maybe included here (maybe). maybe go is dedicated something

            //logic to check which scores are relevant.
            //for each relevant one, add the point type to arraylist of scores. done by calling methods of factory-created score classes
            //potentially might not need to take in the cards for log printing. means you just need to store the points and point type
        }

        public void show(Hand hand){
            ArrayList</*some kind of score parent class*/> scoreList = new ArrayList<>();
                //it'll need point type, cards relevant

            //factory to instantiate all relevant rules for the show

            //logic to check which scores are relevant. add to arraylist of scores. done by calling methods of factory-created score classes
        }


        // take n all

}