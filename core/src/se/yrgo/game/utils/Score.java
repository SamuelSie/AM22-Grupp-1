package se.yrgo.game.utils;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Score {
    private int score;
    private int highScore;
    private GlyphLayout layout;
    private int x;
    private int y;

    public Score(int x, int y) {
        this.score = 0;
        this.highScore = 0;
        layout = new GlyphLayout();
        this.x = x;
        this.y = y;
    }

    public void score(){
        score += 100;
    }

    public void newHighScore(){
        if (score > highScore) {
            highScore = score;
        }
    }

    public int getScore() {
        return score;
    }

    public String getString(){
        return Integer.toString(score);
    }

    public int getHighScore() {
        return highScore;
    }

    public GlyphLayout getLayout() {
        return layout;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
