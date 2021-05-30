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
    public void roundStart(int seed, String player0, String player1) throws IOException {
        bw.write("seed," + seed);
        //player 0
        bw.newLine();
        bw.write(player0 + ",P0");
        //player 1
        bw.newLine();
        bw.write(player1 + ",P1");
    }

    //print out the log lines for when hands are dealt. should be called one time in Cribbage
    public void dealtHand(Hand p0Hand, Hand p1Hand) throws IOException {
        bw.newLine();
        //player 0
        bw.write("deal,P0," + Cribbage.getInstance().canonical(p0Hand));
        //player 1
        bw.newLine();
        bw.write("deal,P1," + Cribbage.getInstance().canonical(p1Hand));
    }

    // needs to be called once per player
    public void discarded(int playerNum, Hand discards) throws IOException {
        bw.newLine();
        bw.write("discard,P" + playerNum + "," + Cribbage.getInstance().canonical(discards));
    }

    /**
     * played() is called each time a player plays a card during the round
     * @param playerNum player 0 or player 1 i.e. P0 or P1
     * @param totalRoundValue this refers to the value on the board i.e. when the players are trying to reach 31
     *                        More specifically, this refers to the value of the segment of the round
     * @param card the card the player plays
     * @throws IOException it's writing to an opened file, hence potential for input/output error
     */
    public void played(int playerNum, int totalRoundValue, Card card) throws IOException {
        bw.newLine();
        bw.write("play,P" + playerNum + "," + totalRoundValue + "," + Cribbage.getInstance().canonical(card));
    }

    //this is for during the actual round. the cards aren't logged, only points and point type
    public void scored(int playerNum, int newScore, int newPoints, String pointType) throws IOException {
        bw.newLine();
        bw.write("score,P" + playerNum + "," + newScore + "," + newPoints + "," + pointType);
    }

    public void cardsShown(int playerNum, Card starterCard, Hand hand) throws IOException{
        bw.newLine();
        bw.write("show,P" + playerNum + "," + Cribbage.getInstance().canonical(starterCard) + "+" + Cribbage.getInstance().canonical(hand));
    }

    //Called during the show. more specifically, called each time a player scores from their hand during the show
    //Also usable for scoring (in start of game) if dealer turns up Jack as starter
    public void handScored(int playerNum, int newScore, int newPoints, String pointType, Hand scoringCards)throws IOException{
        bw.newLine();
        bw.write("score,P" + playerNum + "," + newScore + "," + newPoints + "," + pointType + "," + Cribbage.getInstance().canonical(scoringCards));
    }

    //close the cribbage.log file. this should be called when the game closes
    public void closeFile() throws IOException {
        bw.close();
    }
}