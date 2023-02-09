package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se.yrgo.game.JumpyBirb;

public class MainMenuScreen implements Screen {

    public final JumpyBirb game;
    private OrthographicCamera camera;
    private Texture bg;
    private GlyphLayout layout;

    public MainMenuScreen(final JumpyBirb game) {
        this.game = game;

        bg = new Texture("bg.png");
        layout = new GlyphLayout();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
    }


    @Override
    public void show() {
        layout.setText(game.font, "Press SPACE to start");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        game.batch.draw(bg, 0, 0, game.WIDTH, game.HEIGHT);
        game.font.draw(game.batch, layout, game.WIDTH/2 - layout.width/2, game.HEIGHT/2 + layout.height/2);
        game.batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
    }
}
