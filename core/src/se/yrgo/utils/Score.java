package se.yrgo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.sql.SQLException;
import java.util.List;

public class Score {
    private int score;
    private int highScore;
    private GlyphLayout layout;
    private int x;
    private int y;

    private Sound scoreSound;
    private DBHandler db;
    private BitmapFont font;



    public Score(int x, int y, BitmapFont font) throws SQLException{
        this.score = 0;
        this.highScore = 0;
        layout = new GlyphLayout();
        this.x = x;
        this.y = y;
        this.font = font;

        //placeholder sound for scoring
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("points.mp3"));
        db = new DBHandler();
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

    public GlyphLayout getHighscore() throws SQLException {
        GlyphLayout top5 = new GlyphLayout();
        StringBuilder sb = new StringBuilder();

        for(String score : db.getTop5Highscore()){
            sb.append(score);
            sb.append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf("\n"));

        top5.setText(font, sb.toString());
        return top5;
    }

    public void putHighscore(int score) throws SQLException {
        db.putHighScore(score);
    }

    public void resetScore(){
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public String scoreToString(){
        return Integer.toString(score);
    }

    public String highScoreToString() {
        return Integer.toString(highScore);
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
