package cribbage;

public class LastCardRule implements ScoreRule {
    //todo: scoring - player who placed the last card scores one point (go)
    // should we call this GoRule

    public LastCardRule() {}
    
    public int getScore() {
        return 1;
    }
}

