package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import se.yrgo.game.JumpyBirb;

public class DeathScreen implements Screen {
    private final JumpyBirb game;
    private OrthographicCamera camera;
    private GlyphLayout layout;
    private GlyphLayout layout2;
    public DeathScreen (final JumpyBirb game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
        layout = new GlyphLayout();
        layout2 = new GlyphLayout();

    }
    @Override
    public void show() {
        layout.setText(game.font, "WELCOME TO YOUR DOOM");
        layout2.setText(game.font, "PRESS SPACE TO PLAY AGAIN");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.6f,0.2f,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, layout, game.WIDTH/2 - layout.width/2, (game.HEIGHT/3) * 2 - layout.height/2);
        game.font.draw(game.batch, layout2, game.WIDTH/2 - layout2.width/2, (game.HEIGHT/3) - layout2.height/2);
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

    }
}
