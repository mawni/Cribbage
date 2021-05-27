package cribbage;

public class StarterRule implements ScoreRule {
    //todo: scoring - if starting card is a Jack, dealer gets 2 points immediately

    public StarterRule() {}

    public int getScore() {
        return 2;
    }
}