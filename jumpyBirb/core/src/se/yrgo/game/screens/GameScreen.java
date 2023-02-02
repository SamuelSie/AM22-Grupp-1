package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import se.yrgo.game.JumpyBirb;

import java.awt.*;

public class GameScreen implements Screen {
    private final JumpyBirb game;
    private Texture birbImg;
    private Texture topPipeImg;
    private Music music;
    private Rectangle birb;
    private  Rectangle topPipe;
    private OrthographicCamera camera;
    public GameScreen(final JumpyBirb game) {
        this.game = game;

        // load images
        birbImg = new Texture("bird.png");
        topPipeImg = new Texture("toptube.png");

        // background music
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);

        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

        // create the rectangles
        birb = new Rectangle();
        birb.x = 50;
        birb.y= game.HEIGHT/2 - 24/2;

        birb.width = 34;
        birb.height = 24;

        topPipe = new Rectangle();
        topPipe.x = game.WIDTH / 2 - topPipe.width / 2;
        topPipe.y =game.HEIGHT;
        topPipe.height = 320;
        topPipe.width = 52;
    }



    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.2f,0.2f,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(birbImg, birb.x, birb.y, birb.width, birb.height);
        game.batch.draw(topPipeImg, topPipe.x, topPipe.y, topPipe.width, topPipe.height );
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            game.setScreen(new DeathScreen(game));
            dispose();
        }

    }

    @Override
    public void show() {
    music.play();
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

        topPipeImg.dispose();
        music.dispose();
        birbImg.dispose();

    }
}
