package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.IOException;

//Built with the 'Singleton' design pattern
public class Log {
    //Attributes
    private static Log instance = null;
    private BufferedWriter bw;

    //constructor
    public Log() {
        //create the log file
        try {
            bw = new BufferedWriter(new FileWriter(new File("cribbage.log")));
        } catch (IOException e) {
            System.out.println("Log file creation failed");
            e.printStackTrace();
        }
    }

    //if currently no Log exists, make one, otherwise return the existing one
    //ensures only one Log class ever exists
    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    //the initial logging of a round/game beginning. should be called once.
    public void roundStart(int seed,boolean isHuman1, boolean isHuman2) throws IOException {
        bw.write("seed," + seed);
        bw.newLine();
        if (isHuman1){
            bw.write("cribbage.HumanPlayer,P0");
        } else {
            bw.write("cribbage.RandomPlayer,P0");
        }
        bw.newLine();
        if (isHuman2){
            bw.write("cribbage.HumanPlayer,P1");
        } else{
            bw.write("cribbage.RandomPlayer,P1");
        }
    }

    //print out the log lines for when hands are dealt. should be called one time in Cribbage
    public void dealtHand(ArrayList<Card> p0Hand, ArrayList<Card> p1Hand) throws IOException {
        bw.newLine();
        //player 0
        bw.write("deal,P0,[" + CardShortener.shortenCard(p0Hand.get(0)));
        for (int i=1; i<p0Hand.size(); i++){
            bw.write("," + CardShortener.shortenCard(p0Hand.get(i)));
        }
        bw.write("]");
        //player 1
        bw.newLine();
        bw.write("deal,P1,[" + CardShortener.shortenCard(p1Hand.get(0)));
        for (int i=1; i<p1Hand.size(); i++){
            bw.write("," + CardShortener.shortenCard(p1Hand.get(i)));
        }
        bw.write("]");
    }

    // needs to be called once per player
    public void discarded(int playerNum, ArrayList<Card> discards) throws IOException {
        bw.newLine();
        bw.write("discard,P" + playerNum + ",[" + CardShortener.shortenCard(discards.get(0)) + "," + CardShortener.shortenCard(discards.get(1)) + "]");
    }

    //called each time a player plays a card
    public void played(int playerNum, int roundValue, Card card) throws IOException {
        bw.newLine();
        bw.write("play,P" + playerNum + "," + roundValue + "," + CardShortener.shortenCard(card));
    }

    public void scored(int playerNum, int score, int newPoints, String pointType) throws IOException {
        bw.newLine();
        bw.write("score,P" + playerNum + "," + score + "," + newPoints + "," + pointType);
    }

    public void cardsShown(int playerNum, Card starterCard, ArrayList<Card> hand) throws IOException{
        bw.newLine();
        bw.write("show,P" + playerNum + "," + CardShortener.shortenCard(starterCard) + "+[" + CardShortener.shortenCard(hand.get(0)));
        for (int i=1; i<hand.size(); i++){
            bw.write("," + CardShortener.shortenCard(hand.get(i)));
        }
        bw.write("]");
    }

    //called once for each player, also called once for the crib
    public void handScored(int playerNum, int score, int newPoints, String pointType, ArrayList<Card> cards)throws IOException{
        bw.newLine();
        bw.write("score,P" + playerNum + "," + score + "," + newPoints + "," + pointType + ",[" + CardShortener.shortenCard(cards.get(0)));
        for (int i=1; i<cards.size(); i++){
            bw.write("," + CardShortener.shortenCard(cards.get(i)));
        }
        bw.write("]");
    }

    //close the cribbage.log file. this should be called when the game closes
    public void closeFile() throws IOException {
        bw.close();
    }
}