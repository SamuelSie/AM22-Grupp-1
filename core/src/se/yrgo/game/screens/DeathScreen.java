package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.utils.Score;

public class DeathScreen implements Screen {
    private final JumpyBirb game;
    private OrthographicCamera camera;
    private GlyphLayout layout;
    private GlyphLayout layout2;
    Score score;
    public DeathScreen (final JumpyBirb game, Score score) {
        this.game = game;
        this.score = score;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);

//        layout = new GlyphLayout();
//        layout2 = new GlyphLayout();

    }
    @Override
    public void show() {
//        layout.setText(game.font, "WELCOME TO YOUR DOOM");
//        layout2.setText(game.font, "PRESS SPACE TO PLAY AGAIN");

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.6f,0.2f,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
//        game.font.draw(game.batch, layout, game.CAMX/2 - layout.width/2, (game.CAMY/3) * 2 - layout.height/2);
//        game.font.draw(game.batch, layout2, game.CAMX/2 - layout2.width/2, (game.CAMY/2) - layout2.height/2);

        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game, score));
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
