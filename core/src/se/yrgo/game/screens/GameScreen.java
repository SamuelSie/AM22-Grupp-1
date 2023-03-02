package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.objects.*;
import se.yrgo.game.utils.Score;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen implements Screen {
    private final JumpyBirb game;
    private Doge doge;

    private Ground ground;

    private Music music;
    private OrthographicCamera camera;
    private float deltaTime;
    //SKapa toppipe och bottompipe som private.
    //Skapa sedan objekten i konstruktorn

    private Array<Pipe> pipeArray;
    private long lastSpawnTime;
    private boolean isDead;
    private Score score;

    public GameScreen(final JumpyBirb game, Score score) {
        this.game = game;
        //create doge & ground object with x & y position
        doge = new Doge(20, game.CAMY / 2);
        ground = new Ground(0, -50);

        // background music
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);

        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);

        // deltatime är tiden mellan frames, mätt i sekunder.
        //behövs för att flytta saker på skärmen, typ falla och hoppa.
        deltaTime = Gdx.graphics.getDeltaTime();

        //Array av topPipes
        pipeArray = new Array<Pipe>();

        spawnPipes();
        isDead = false;

        this.score = score;
    }


    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.backGround, 0, 0, game.CAMX, game.CAMY);

        game.batch.draw(doge.getTexture(), doge.getPosition().x, doge.getPosition().y, doge.getTexture().getWidth(), doge.getTexture().getHeight());
        game.batch.draw(ground.getTexture(), ground.getPosition().x, ground.getPosition().y, ground.getTexture().getWidth() * 2, ground.getTexture().getHeight());
        game.font.draw(game.batch, score.getLayout(), score.getX(), score.getY());
        drawPipes();
        game.batch.end();

        //spawn pipes in the given time
        if (TimeUtils.nanoTime() - lastSpawnTime > 3000000000L) spawnPipes();

        loopOverPipes();

        doge.fall(deltaTime);

        checkPlayerInput();

        hasDogeHitCieling();

        checkIfDead();

        score.getLayout().setText(game.font, score.scoreToString());
    }


    @Override
    public void show() {
        //music.play();
        score.resetScore();

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
        music.dispose();
        doge.dispose();
        ground.dispose();


    }

    private void spawnPipes() {
        int isAdding = ThreadLocalRandom.current().nextInt(2);
        int middleSpace = ThreadLocalRandom.current().nextInt(Pipe.getDISTANCE());
        Pipe pipe = new Pipe(game.CAMX, game.CAMY / 2 - game.CAMY + (isAdding == 1 ? middleSpace / 2 : -middleSpace / 2));
        pipeArray.add(pipe);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    private void loopOverPipes() {
        for (Iterator<Pipe> iter = pipeArray.iterator(); iter.hasNext(); ) {
            Pipe pipe = iter.next();

            pipe.move();

            removePipe(iter, pipe);

            checkCollision(pipe, ground);

            updateScore(pipe);
        }
    }

    private void checkCollision(Pipe pipe, Ground ground) {
        if (doge.isCollided(pipe.getHitBoxTop()) || doge.isCollided(pipe.getHitBoxBottom()) ||
                doge.isCollided(ground.getGroundBox())) {
            isDead = true;
        }
    }


    private static void removePipe(Iterator<Pipe> iter, Pipe pipe) {
        if (pipe.getPositionTop().x + pipe.getHitBoxTop().getWidth() < 0
                || pipe.getPositionBottom().x + pipe.getHitBoxBottom().getWidth() < 0) {
            pipe.dispose();
            iter.remove();
        }
    }

    private void updateScore(Pipe pipe) {
        if ((pipe.getPositionBottom().x + pipe.getHitBoxBottom().x) < doge.getPosition().x && !pipe.isScored()) {
            score.score();
            pipe.setScored(true);
        }
    }

    private void drawPipes() {
        for (Pipe pipe : pipeArray) {
            game.batch.draw(pipe.getTopPipeImg(), pipe.getPositionTop().x, pipe.getPositionTop().y);
            game.batch.draw(pipe.getBottomPipeImg(), pipe.getPositionBottom().x, pipe.getPositionBottom().y);
        }
    }

    private void checkIfDead() {
        if (isDead) {
            score.newHighScore();
            game.setScreen(new DeathScreen(game, score));
            dispose();
        }
    }

    private void hasDogeHitCieling() {
        if (doge.getPosition().y >= (game.CAMY - 60)) {
            doge.getPosition().y = (game.CAMY - 60);
            doge.resetVelocity();
        }
    }

    private void checkPlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
            doge.jump(deltaTime);
        }
    }
}
