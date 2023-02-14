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
import se.yrgo.game.objects.Doge;
import se.yrgo.game.objects.Ground;

public class GameScreen implements Screen {
    private final JumpyBirb game;
    private Doge doge;

    private Ground ground;

    private Texture topPipeImg;
    private Music music;
    private  Rectangle topPipe;
    private OrthographicCamera camera;
    private float deltaTime;

    public GameScreen(final JumpyBirb game) {
        this.game = game;
        //create doge & ground object with x & y position
        doge = new Doge(50,350);
        ground = new Ground(0, -50);

        // load images
        topPipeImg = new Texture("toptube.png");

        // background music
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);

        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

        // create the rectangles

        topPipe = new Rectangle();
        topPipe.x = 50;
        topPipe.y = game.HEIGHT - 320;
        topPipe.height = 320;
        topPipe.width = 52;

        // deltatime är tiden mellan frames, mätt i sekunder.
        //behövs för att flytta saker på skärmen, typ falla och hoppa.
        deltaTime = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.2f,0.2f,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.backGround, 0, 0, game.WIDTH, game.HEIGHT);
        game.batch.draw(doge.getTexture(), doge.getPosition().x, doge.getPosition().y, doge.getTexture().getWidth(), doge.getTexture().getHeight());

        game.batch.draw(ground.getTexture(), ground.getPosition().x, ground.getPosition().y, ground.getTexture().getWidth() * 2, ground.getTexture().getHeight());

        game.batch.draw(topPipeImg, topPipe.x, topPipe.y, topPipe.width, topPipe.height );
        game.batch.end();

        //gör att doge faller nedåt
        doge.fall(deltaTime);

        //ändrar hastigheten på doge y-axis från minus till plus
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            doge.jump(deltaTime);
        }

        // if doge hits bottom of screen, switch to DeathScreen
        if (doge.getHitbox().overlaps(ground.getGroundBox())) {
            game.setScreen(new DeathScreen(game));
            dispose();
        }

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
        doge.dispose();
    }
}
