package se.yrgo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.yrgo.game.screens.MainMenuScreen;
import se.yrgo.utils.Score;

import java.sql.SQLException;

public class JumpyBirb extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;
    public static final int CAMX = WIDTH / 2;
    public static final int CAMY = HEIGHT / 2;
    private String lastDifficulty;
    private Score score;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("myFont.fnt"));
        lastDifficulty = "easy";

        /// ajajaj nu är det rörigt med SQLException!! STÄDA UPP!
        try {
            score = new Score(CAMX -100, CAMY - 20, font);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.setScreen(new MainMenuScreen(this, score));
    }

    public void render() {
        super.render();
    }


    public String getLastDifficulty() {
        return lastDifficulty;
    }
    public void setLastDifficulty(String lastDifficulty) {
        this.lastDifficulty = lastDifficulty;
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }

    public BitmapFont getFont() {
        return this.font;
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
