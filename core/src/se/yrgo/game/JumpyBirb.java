package se.yrgo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.yrgo.game.screens.MainMenuScreen;

public class JumpyBirb extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;
    public static final int CAMX = WIDTH / 2;
    public static final int CAMY = HEIGHT / 2;
    public Texture backGround;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("myFont.fnt"));
        backGround = new Texture("bg.png");

        this.setScreen(new MainMenuScreen(this));


    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        backGround.dispose();
    }
}
