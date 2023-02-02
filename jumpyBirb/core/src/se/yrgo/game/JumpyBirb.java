package se.yrgo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.yrgo.game.screens.MainMenuScreen;

public class JumpyBirb extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public final int WIDTH = 480;
    public final int HEIGHT = 800;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        this.setScreen(new MainMenuScreen(this));


    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
