package se.yrgo.game.utils;

public class Score {
    private int score;
    private int highScore;

    public Score() {
        this.score = 0;
        this.highScore = 0;
    }

    private void score(){
        score += 10000;
    }

    private void newHighScore(){
        if (score > highScore) {
            highScore = score;
        }
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
}
