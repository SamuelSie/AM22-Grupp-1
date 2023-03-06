package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.utils.Score;

public class DeathScreen implements Screen {
    private final JumpyBirb game;
    private OrthographicCamera camera;
    private FitViewport vp;
    private GlyphLayout layout;
    private GlyphLayout layout2;
    private GlyphLayout finalScore;
    Score score;
    
    private boolean canRestart;
    private Timer.Task restartTask;

    public DeathScreen(final JumpyBirb game, Score score) {

        this.game = game;
        this.score = score;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);

//        layout = new GlyphLayout();
//        layout2 = new GlyphLayout();
        finalScore = new GlyphLayout();

        canRestart = false;
        
        restartTask = new Timer.Task(){
            public void run(){
                canRestart = true;
            }
        };
        Timer.schedule(restartTask,2f);
        

        vp = new FitViewport(game.CAMX, game.CAMY, camera);


    }

    @Override
    public void show() {
//        layout.setText(game.font, "WELCOME TO YOUR DOOM");
//        layout2.setText(game.font, "PRESS SPACE TO PLAY AGAIN");
        finalScore.setText(game.font, "Round score: " + score.scoreToString() + "\nHighscore: " +
                score.highScoreToString());

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.6f, 0.2f, 0.2f, 1);

        vp.apply();
        camera.update();
        game.batch.setProjectionMatrix(vp.getCamera().combined);

        game.batch.begin();
//        game.font.draw(game.batch, layout, game.CAMX/2 - layout.width/2, (game.CAMY/3) * 2 - layout.height/2);
//        game.font.draw(game.batch, layout2, game.CAMX/2 - layout2.width/2, (game.CAMY/2) - layout2.height/2);
        game.font.draw(game.batch, finalScore, game.CAMX / 2 - 200, (game.CAMY / 3));
        game.batch.end();

        
        // Add delay before screen transition
        if (canRestart && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched())) {
            restartTask.cancel();
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
