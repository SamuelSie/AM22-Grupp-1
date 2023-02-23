package se.yrgo.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Score {
    private int score;
    private int highScore;
    private GlyphLayout layout;
    private int x;
    private int y;

    private Sound scoreSound;

    public Score(int x, int y) {
        this.score = 0;
        this.highScore = 0;
        layout = new GlyphLayout();
        this.x = x;
        this.y = y;

        //placeholder sound for scoring
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("scoreSound.wav"));
    }

    public void score() {
        scoreSound.play();
        score += 1000;
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
