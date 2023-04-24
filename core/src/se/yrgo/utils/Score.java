package se.yrgo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Score {
    private int currentScore;
    private int highScore;
    private GlyphLayout layout;
    private int x;
    private int y;

    private Sound scoreSound;
    private DBHandler db;


    public Score(int x, int y) throws SQLException {
        this.currentScore = 0;
        this.highScore = 0;
        layout = new GlyphLayout();
        this.x = x;
        this.y = y;

        //placeholder sound for scoring
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("music/points.mp3"));
        db = new DBHandler();
    }

    public void score() {
        scoreSound.play();
        currentScore += 1000;
    }

    public void newHighScore() {
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    public String getHighscore() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("Highscore: " + Difficulty.getTable());

        for (String highscore : db.getTop5Highscore()) {
            sb.append("\n");
            sb.append(highscore);
        }

        return sb.toString();
    }

    public void putHighscore(int score) throws SQLException {
        db.putHighScore(score);
    }

    public void resetScore() {
        currentScore = 0;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public String scoreToString() {
        return Integer.toString(currentScore);
    }

    public List<String> getAllHighscore() throws SQLException {
        String[] difficulties = {"easy", "medium", "hard"};
        List<String> highscores = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < difficulties.length; i++) {
            sb.append(difficulties[i]);
            for (String highscore : db.get15Highscore(difficulties[i])) {
                sb.append("\n");
                sb.append(highscore);
            }
            highscores.add(sb.toString());
            sb.setLength(0);
        }

        return highscores;
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
