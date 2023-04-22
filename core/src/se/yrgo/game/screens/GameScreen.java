package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.sprites.*;
import se.yrgo.utils.Difficulty;
import se.yrgo.utils.Score;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen implements Screen {
    private final JumpyBirb game;
    private Doge doge;
    private Music music;
    private OrthographicCamera camera;
    private FitViewport vp;
    private Array<Pipe> pipeArray;
    private long pipeSpawnTime;
    private boolean isDead;
    private Score score;
    private GameBackground background;
    private GameBackgroundSky sky;
    private Ground ground;


    public GameScreen(final JumpyBirb game, Score score) {
        this.game = game;
        //create doge & ground object with x & y position
        doge = new Doge(20, JumpyBirb.CAMY / 2);

        // background music
        music = Gdx.audio.newMusic(Gdx.files.internal("music/GameMusic.mp3"));
        music.setLooping(true);

        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, JumpyBirb.CAMX, JumpyBirb.CAMY);
        vp = new FitViewport(JumpyBirb.CAMX, JumpyBirb.CAMY, camera);

        pipeArray = new Array<>();

        spawnPipes();


        sky = new GameBackgroundSky(0, 0);
        background = new GameBackground(0, 0);
        ground = new Ground(0, 0);


        isDead = false;


        this.score = score;
    }


    @Override
    public void render(float delta) {

        vp.apply();
        camera.update();
        game.getBatch().setProjectionMatrix(vp.getCamera().combined);

        game.getBatch().begin();
        sky.draw(game);
        background.draw(game);
        game.getBatch().draw(doge.getTexture(), doge.getPosition().x, doge.getPosition().y, doge.getTexture().getRegionWidth(), doge.getTexture().getRegionHeight());
        drawPipe();
        ground.draw(game);
        game.getFont().draw(game.getBatch(), score.getLayout(), score.getX(), score.getY());
        game.getBatch().end();

        //spawn pipes in the given time
        if (TimeUtils.nanoTime() - pipeSpawnTime > Difficulty.getPipeSpawnRate()) spawnPipes();

        loopOverPipes(delta);


        doge.update(delta);

        checkPlayerInput();
        checkCollisionGround(ground);
        checkIfHitCeiling();

        //kolla igenom kedjan av throws och bestäm var catcha
        try {
            checkIfDead();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        score.getLayout().setText(game.getFont(), score.scoreToString());
    }


    @Override
    public void show() {
        music.play();
        score.resetScore();

    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
    }

    @Override
    public void pause() {
        // Not yet implemented
    }

    @Override
    public void resume() {
        // Not yet implemented
    }

    @Override
    public void hide() {
        // Not yet implemented
    }

    @Override
    public void dispose() {
        music.dispose();
        doge.dispose();
    }

    private void spawnPipes() {
        int isAdding = ThreadLocalRandom.current().nextInt(2);
        int middleSpace = ThreadLocalRandom.current().nextInt(Difficulty.getPipeDistance());
        Pipe pipe = new Pipe(JumpyBirb.CAMX, JumpyBirb.CAMY / 2 - JumpyBirb.CAMY + (isAdding == 1 ? middleSpace / 2 : -middleSpace / 2));

        pipeArray.add(pipe);
        pipeSpawnTime = TimeUtils.nanoTime();
    }

    private void loopOverPipes(float delta) {
        for (Iterator<Pipe> iter = pipeArray.iterator(); iter.hasNext(); ) {
            Pipe pipe = iter.next();
            pipe.move();

            pipe.getSaladAnimation().update(delta);

            checkCollision(pipe);

            updateScore(pipe);

            pipe.remove(iter);
        }
    }


    private void checkCollision(Pipe pipe) {
        if (doge.isCollided(pipe.getHitBoxKettle()) || doge.isCollided(pipe.getHitBoxChain()) || doge.isCollided(pipe.getHitBoxSaladBody()) || doge.isCollided(pipe.getHitBoxSaladHand())) {
            isDead = true;
        }
    }

    private void checkCollisionGround(Ground ground) {
        if (doge.isCollided(ground.getHitBox())) isDead = true;
    }

    private void updateScore(Pipe pipe) {
        if ((pipe.getPositionSalad().x + pipe.getHitBoxSaladBody().x) < doge.getPosition().x && !pipe.isScored()) {
            score.score();
            pipe.setScored(true);
        }
    }

    private void drawPipe() {

        for (Pipe pipe : pipeArray) {
            //minskar koden här rejält, men kräver att vi ritar saker i rätt ordning.
            pipe.draw(game);
        }
    }


    private void checkIfDead() throws SQLException {
        if (isDead) {
            score.putHighscore(score.getCurrentScore());
            game.setScreen(new DeathScreen(game, score));
            dispose();
        }
    }

    private void checkIfHitCeiling() {
        if (doge.getPosition().y >= (game.CAMY - doge.getTexture().getRegionHeight())) {
            doge.getPosition().y = (game.CAMY - doge.getTexture().getRegionHeight());
            doge.resetVelocity();
        }
        if (doge.getPosition().y <= 0) {
            doge.getPosition().y = 0;
        }

    }

    private void checkPlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            doge.jump();
            doge.getAnimation().setStartJumpAnimation(true);
        }
    }
}
