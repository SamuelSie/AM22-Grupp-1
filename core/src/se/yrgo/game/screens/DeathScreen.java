package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.utils.Animation;
import se.yrgo.utils.Score;

import java.sql.SQLException;

public class DeathScreen implements Screen {
    private final JumpyBirb game;
    private OrthographicCamera camera;
//    private FitViewport vp;
    private GlyphLayout layout;
    private GlyphLayout layout2;
    private GlyphLayout finalScore;
    private Texture backGround;
    private Animation backGroundAnimation;
    private Texture trogdor;
    private Animation trogdorAnimation;
    private Image trogdorAnimationImage;
    private Image bgAnimationImage;
    private Music music;
    private Score score;

    private Stage stage;
    private Skin skin;
    private float buttonWidth;
    private float buttonHeight;

    private Table table;

    private boolean canRestart;
    private Timer.Task restartTask;

    public DeathScreen(final JumpyBirb game, Score score) throws SQLException {

        this.game = game;
        this.score = score;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);

//        layout = new GlyphLayout();
//        layout2 = new GlyphLayout();
        finalScore = score.getHighscore();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/DeathMusic.mp3"));
        music.setLooping(true);

        canRestart = false;
        
        restartTask = new Timer.Task(){
            public void run(){
                canRestart = true;
            }
        };
        Timer.schedule(restartTask,1f);
        

//        vp = new FitViewport(game.CAMX, game.CAMY, camera);

        backGround = new Texture("deathScreenAnimation.png");
        backGroundAnimation = new Animation(new TextureRegion(backGround), 2, 1f);


        trogdor = new Texture("trogdor.png");
        trogdorAnimation = new Animation(new TextureRegion(trogdor), 4, 1f);
        trogdorAnimationImage = new Image(trogdorAnimation.getDrawableFrame());


        skin = new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));

        buttonWidth = game.CAMX / 5;
        buttonHeight = buttonWidth * 0.3f;
    }

    @Override
    public void show() {
//        layout.setText(game.font, "WELCOME TO YOUR DOOM");
//        layout2.setText(game.font, "PRESS SPACE TO PLAY AGAIN");
//        finalScore.setText(game.font, "Round score: " + score.scoreToString() + "\nHighscore: " +
//                score.highScoreToString());
        music.play();

        stage = new Stage(new FitViewport(game.CAMX, game.CAMY));
        table = new Table();
        table.setFillParent(true);


        TextButton playAgain = new TextButton("Press to play again", skin);
        TextButton backToMenu = new TextButton("Press to get back to menu",skin);

        playAgain.getLabel().setFontScale(.5f);
        backToMenu.getLabel().setFontScale(.5f);

        table.add(playAgain);
        table.row();
        table.add(backToMenu);


        stage.addActor(table);
//        stage.addActor(trogdorAnimationImage);


    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0.6f, 0.2f, 0.2f, 1);

        backGroundAnimation.update(delta);
        bgAnimationImage = new Image(backGroundAnimation.getDrawableFrame());
        table.setBackground(bgAnimationImage.getDrawable());

        trogdorAnimation.update(delta);






        stage.act(delta);
        stage.draw();
//        vp.apply();
//        camera.update();
//        game.batch.setProjectionMatrix(vp.getCamera().combined);
//
        game.batch.begin();
//        game.batch.draw(backGroundAnimation.getFrame(), 0, 0, game.CAMX, game.CAMY);
        game.batch.draw(trogdorAnimation.getFrame(), 180, 55, 120, 105);
////        game.font.draw(game.batch, layout, game.CAMX/2 - layout.width/2, (game.CAMY/3) * 2 - layout.height/2);
////        game.font.draw(game.batch, layout2, game.CAMX/2 - layout2.width/2, (game.CAMY/2) - layout2.height/2);
//        game.font.draw(game.batch, finalScore, game.CAMX / 2 - 200, (game.CAMY - 100));
        game.batch.end();
//
//

//        trogdorAnimation.update(delta);
        
        // Add delay before screen transition
        if (canRestart && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched())) {
            restartTask.cancel();
            game.setScreen(new GameScreen(game, score));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

//        vp.update(width, height);
    stage.getViewport().update(width, height, true);
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
        stage.dispose();
        music.dispose();
    }
}
