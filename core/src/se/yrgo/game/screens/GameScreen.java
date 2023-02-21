package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.objects.BottomPipe;
import se.yrgo.game.objects.Doge;
import se.yrgo.game.objects.Ground;
import se.yrgo.game.objects.TopPipe;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    private final JumpyBirb game;
    private Doge doge;
    
    private Ground ground;
    
    private Music music;
    private OrthographicCamera camera;
    private float deltaTime;
    //SKapa toppipe och bottompipe som private.
    //Skapa sedan objekten i konstruktorn
    private Array<BottomPipe> bottomPipeArray;
    private Array<TopPipe> topPipeArray;
    private long lastSpawnTime;
    private boolean isDead;
    
    
    public GameScreen(final JumpyBirb game) {
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
        topPipeArray = new Array<TopPipe>();
        
        //Array av bottomPipes
        bottomPipeArray = new Array<BottomPipe>();
        
        spawnPipes();
        isDead = false;
    }
    
    private void spawnPipes() {
        TopPipe topPipe = new TopPipe(game.CAMX, game.CAMY /5 * 4);
        BottomPipe bottomPipe = new BottomPipe(game.CAMX, game.CAMY / 3 - 320);// Ändra detta senare
        topPipeArray.add(topPipe);
        bottomPipeArray.add(bottomPipe);
        lastSpawnTime = TimeUtils.nanoTime();
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
        
        for (TopPipe topPipe : topPipeArray) {
            game.batch.draw(topPipe.getToptubeImg(), topPipe.getPosition().x, topPipe.getPosition().y);
        }
        
        for(BottomPipe bottomPipe : bottomPipeArray){
            game.batch.draw(bottomPipe.getBottomtubeImg(),bottomPipe.getPosition().x, bottomPipe.getPosition().y);
        }
        game.batch.end();
        
        if(TimeUtils.nanoTime() - lastSpawnTime > 3000000000L) spawnPipes();
        
        for(Iterator<TopPipe> iter = topPipeArray.iterator(); iter.hasNext();){
            TopPipe topPipe = iter.next();
            topPipe.move();
            if(topPipe.getPosition().x + topPipe.getHitBox().getWidth() < 0) iter.remove();
            if(doge.isCollided(topPipe.getHitBox())) isDead = true;
        }
    
        for(Iterator<BottomPipe> iter = bottomPipeArray.iterator(); iter.hasNext();){
            BottomPipe bottomPipe = iter.next();
            bottomPipe.move();
            if(bottomPipe.getPosition().x + bottomPipe.getHitBox().getWidth() < 0) iter.remove();
            if(doge.isCollided(bottomPipe.getHitBox())) isDead = true;
        }
        
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

        if(doge.isCollided(ground.getGroundBox())) isDead = true;
        // if doge is dead, switch to DeathScreen
        if (isDead) {
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
