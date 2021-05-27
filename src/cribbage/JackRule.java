package cribbage;

public class JackRule implements ScoreRule {
    //todo: scoring - if in their hand a player has Jack
    //of the same suit as the Starter card, they score 1 point

    public JackRule() {}

    public int getScore() {
        // if hand has jack of same suit of starter
        return 1;
    }
}

