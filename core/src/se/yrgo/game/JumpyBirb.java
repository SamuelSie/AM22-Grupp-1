package se.yrgo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.yrgo.game.screens.MainMenuScreen;
import se.yrgo.utils.Difficulty;
import se.yrgo.utils.Score;

import java.sql.SQLException;

public class JumpyBirb extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;
    public static final int CAMX = WIDTH / 2;
    public static final int CAMY = HEIGHT / 2;
    private Score score;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("myFont.fnt"));

        /// ajajaj nu är det rörigt med SQLException!! STÄDA UPP!
        try {
            score = new Score(CAMX -100, CAMY - 20, font);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.setScreen(new MainMenuScreen(this, score));
        new Difficulty();

    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();

    }
}
