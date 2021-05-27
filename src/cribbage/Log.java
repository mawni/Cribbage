package cribbage;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
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

    //the initial logging of a round/game beginning
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

    public void dealtHand(Card[6] p1Hand, Card[6] p2Hand) throws IOException {
        bw.newLine();

    }

    public void discarded(Card[2] p1Discard, Card[2] p2Discard) throws IOException {
        bw.newLine();

    }

    public void played(int playerNum, int roundValue, Card card) throws IOException {
        bw.newLine();

    }

    public void scored(int playerNum, int score, int newPoints, String pointType) throws IOException {
        bw.newLine();

    }

    public void cardsShown(int playerNum, Card starterCard, Card[5] hand) throws IOException{
        bw.newLine();

    }

    public void handScored(int playerNum, int score, int newPoints, String pointType, ArrayList<Card> cards)throws IOException{
        bw.newLine();

    }

    //close the cribbage.log file. this should be called when the game closes
    public void closeFile() throws IOException {
        bw.close();
    }
}