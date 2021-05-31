package cribbage;

import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public abstract class ScoreRule {
    private int points; //amount of points that a score rule grants
    private Hand cards; //the specific set of cards that result in the scoring of a rule
    private String type; //the string name of the rule
    private ArrayList<Hand> scoringSets = new ArrayList<Hand>();

    public abstract boolean checkRule(Hand hand); //subclasses must implement this
    public boolean checkRule(Hand hand, String playOrShow){ //classes may override this if needed
        return false;
    };

    //getters
    public int getPoints(){return points;}
    public Hand getCards(){return cards;}
    public ArrayList<Hand> getList(){return scoringSets;}
    public String getType(){return type;}

    //setters
    public void setPoints(int x) {
        this.points = x;
    }
    public void setCards(Hand newCards) {
        this.cards = newCards;
    }
    public void setType(String newType) {
        this.type = newType;
    }
    public void setList(ArrayList<Hand> list){ this.scoringSets = list; }
}