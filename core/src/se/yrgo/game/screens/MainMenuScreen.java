package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.sprites.IdleDoge;
import se.yrgo.utils.Score;

public class MainMenuScreen implements Screen {

    public final JumpyBirb game;
    private OrthographicCamera camera;
    private Viewport vp;
    private GlyphLayout layout;
    private Score score;
    private Texture backGround;
    
    private IdleDoge idleDoge;
    


    public MainMenuScreen(final JumpyBirb game, Score score) {
        this.game = game;
        this.score = score;

        layout = new GlyphLayout();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);
        vp = new FitViewport(game.CAMX, game.CAMY, camera);
        backGround = new Texture("mainMenuBg.png");
        idleDoge = new IdleDoge();

    }


    @Override
    public void show() {
        layout.setText(game.font, "Press SPACE to start");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        vp.apply();
        camera.update();
        game.batch.setProjectionMatrix(vp.getCamera().combined);


        game.batch.begin();
        game.batch.draw(backGround, 0, 0, game.CAMX, game.CAMY);
        game.font.draw(game.batch, layout, game.CAMX / 2 - layout.width/2, game.CAMY / 2 + layout.height/2);
        game.batch.draw(idleDoge.getTexture(),180,70);
        game.batch.end();

        idleDoge.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game, score));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
            vp.update(width, height);

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
