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
    private Array<Movable> moveableArray;
    private Array<Ground> groundArray;
    private long pipeSpawnTime;
    private long groundSpawnTime;
    private boolean isDead;
    private Score score;
    private GameBackground background;
    private GameBackgroundSky sky;
    private Ground ground;


    public GameScreen(final JumpyBirb game, Score score) {
        this.game = game;
        //create doge & ground object with x & y position
        doge = new Doge(20, game.CAMY / 2);

        // background music
        music = Gdx.audio.newMusic(Gdx.files.internal("music/GameMusic.mp3"));
        music.setLooping(true);

        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);
        vp = new FitViewport(game.CAMX, game.CAMY, camera);

//        // Array av ground
        groundArray = new Array<Ground>();
        
//        //Array av pipes
//        pipeArray = new Array<Pipe>();

        moveableArray = new Array<Movable>();
//        moveableArray.add(new GameBackgroundSky(0, -75));
//        moveableArray.add(new GameBackground(0, 0));

//        spawnGround();
        spawnPipes();


        sky = new GameBackgroundSky(0, 0);
        background = new GameBackground(0, 0);
        ground = new Ground(0,0);



        isDead = false;


        this.score = score;
    }


    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0, 0, 0, 1f);

        vp.apply();
        camera.update();
        game.batch.setProjectionMatrix(vp.getCamera().combined);

        game.batch.begin();
        sky.draw(game);
        background.draw(game);
        game.batch.draw(doge.getTexture(), doge.getPosition().x, doge.getPosition().y, doge.getTexture().getRegionWidth(), doge.getTexture().getRegionHeight());
        drawMovable();
        ground.draw(game);
        game.font.draw(game.batch, score.getLayout(), score.getX(), score.getY());
        game.batch.end();

        //spawn pipes in the given time
        if (TimeUtils.nanoTime() - pipeSpawnTime > Difficulty.getPipeSpawnRate()) spawnPipes();
//        if (TimeUtils.nanoTime() - groundSpawnTime > 3_350_000_000L) spawnGround();

        loopOverMovable(delta);


        doge.update(delta);

        checkPlayerInput(delta);

        checkIfHitCeiling();

        //kolla igenom kedjan av throws och bestäm var catcha
        try {
            checkIfDead();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        score.getLayout().setText(game.font, score.scoreToString());
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        music.dispose();
        doge.dispose();
    }

    private void spawnPipes() {
        int isAdding = ThreadLocalRandom.current().nextInt(2);
        int middleSpace = ThreadLocalRandom.current().nextInt(Difficulty.getPipeDistance());
        Pipe pipe = new Pipe(game.CAMX, game.CAMY / 2 - game.CAMY + (isAdding == 1 ? middleSpace / 2 : -middleSpace / 2));

        moveableArray.add(pipe);
        pipeSpawnTime = TimeUtils.nanoTime();
    }

    private void spawnGround() {
        Ground ground = new Ground(game.CAMX, 0);
        moveableArray.add(ground);
//        groundSpawnTime = TimeUtils.nanoTime();
    }



    private void loopOverMovable(float delta) {
        for (Iterator<Movable> iter = moveableArray.iterator(); iter.hasNext(); ) {
            Movable obj = iter.next();
            obj.move();


            if (obj.getClass() == Pipe.class) {
                Pipe pipe = (Pipe) obj;
                pipe.getSaladAnimation().update(delta);

                checkCollision(pipe);

                updateScore(pipe);
            }

            if (obj.getClass() == Ground.class) {
                Ground ground = (Ground) obj;

                checkCollisionGround(ground);

//                if (ground.getPosition().x + ground.getTexture().getWidth() <= game.CAMX) {
//                    spawnGround();
//                }
            }

            obj.remove(iter);
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

    private void drawMovable() {

        for (Movable obj : moveableArray) {
            //minskar koden här rejält, men kräver att vi ritar saker i rätt ordning.
            obj.draw(game);


        }}
    

    private void checkIfDead() throws SQLException {
        if (isDead) {
            score.putHighscore(score.getScore());
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

    private void checkPlayerInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            doge.jump(delta);
            doge.getAnimation().setStartJumpAnimation(true);
        }
    }
}
