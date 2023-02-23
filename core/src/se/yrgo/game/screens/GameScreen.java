package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.objects.BottomPipe;
import se.yrgo.game.objects.Doge;
import se.yrgo.game.objects.Ground;
import se.yrgo.game.objects.TopPipe;

public class GameScreen implements Screen {
    private final JumpyBirb game;
    private Doge doge;

    private Ground ground;
    
    private Music music;
    private OrthographicCamera camera;
    private float deltaTime;
    //SKapa toppipe och bottompipe som private.
    //Skapa sedan objekten i konstruktorn
    private BottomPipe bottomPipe;
    private TopPipe topPipe;
    private GlyphLayout layout;
    

    public GameScreen(final JumpyBirb game) {
        this.game = game;
        //create doge & ground object with x & y position
        doge = new Doge(20,game.CAMY / 2);
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
        
        topPipe = new TopPipe(20,300);
        bottomPipe = new BottomPipe(20,-240);
    }

    @Override
    public void render(float delta) {

        //ScreenUtils.clear(0.2f,0.2f,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.backGround, 0, 0, game.CAMX, game.CAMY);
        game.batch.draw(doge.getTexture(), doge.getPosition().x, doge.getPosition().y, doge.getTexture().getWidth(), doge.getTexture().getHeight());

        game.batch.draw(ground.getTexture(), ground.getPosition().x, ground.getPosition().y, ground.getTexture().getWidth() * 2, ground.getTexture().getHeight());
        
        game.batch.draw(topPipe.getToptubeImg(), topPipe.getPosition().x, topPipe.getPosition().y,topPipe.getToptubeImg().getWidth(), topPipe.getToptubeImg().getHeight());
        game.batch.draw(bottomPipe.getBottomtubeImg(), bottomPipe.getPosition().x, bottomPipe.getPosition().y,bottomPipe.getBottomtubeImg().getWidth(), bottomPipe.getBottomtubeImg().getHeight());
        
        game.batch.end();

        //gör att doge faller nedåt
        doge.fall(deltaTime);

        //ändrar hastigheten på doge y-axis från minus till plus
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            doge.jump(deltaTime);
        }

        if (doge.getPosition().y >= (game.CAMY - 60)) {
            doge.getPosition().y = (game.CAMY - 60);
            doge.resetVelocity();
        }

        // if doge hits bottom of screen, switch to DeathScreen
        if (doge.getHitbox().overlaps(ground.getGroundBox())) {
            game.setScreen(new DeathScreen(game));
            dispose();
        }
        //if doge hits topPipe, switch to deathscreen
        if (doge.getHitbox().overlaps(topPipe.getHitBox())) {
            game.setScreen(new DeathScreen(game));
            dispose();
        }
        // if doge hit bottomPipe, switch to deathscreen
        if (doge.getHitbox().overlaps(bottomPipe.getHitBox())) {
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
        music.dispose();
        doge.dispose();


        ground.dispose();

    }
}
