package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.sprites.IdleDoge;
import se.yrgo.utils.Score;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class MainMenuScreen implements Screen {

    public final JumpyBirb game;
    private OrthographicCamera camera;
    private Viewport vp;
    private GlyphLayout layout;
    private Score score;
    private Texture backGround;

    //behöver göra en image av bgTexture för att använda.. tror jag
    private Image backGroundImage;
    
    private IdleDoge idleDoge;
    private Music music;
    private Stage stage;
    Skin skin;
    


    public MainMenuScreen(final JumpyBirb game, Score score) {
        this.game = game;
        this.score = score;

        layout = new GlyphLayout();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);
        vp = new FitViewport(game.CAMX, game.CAMY, camera);
        backGround = new Texture("mainMenuBg.png");
        //lägger in texture i image
        backGroundImage = new Image(backGround);

        idleDoge = new IdleDoge();

        music = Gdx.audio.newMusic(Gdx.files.internal("tmp2.mp3"));


        //skins innehåller massa bös som font och bilder
        skin= new Skin(Gdx.files.internal("skin/uiskin.json"));
    }



    // start the playback of the background music immediately


    @Override
    public void show() {

        layout.setText(game.font, "Press SPACE to start");
        music.setLooping(true);
        music.play();

        //scene2d stuff
        //creating the stage, table and buttons
        stage = new Stage(new FitViewport(game.CAMX, game.CAMY));
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(backGroundImage.getDrawable());

        TextButton startButton = new TextButton("Start", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //button functionality
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, score));
                dispose();
            }
        });

//            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
//
//            }

        //adding the buttons to the table
        table.add(startButton).padBottom(20);
        table.row();
        table.add(exitButton);

        //adding the table to the stage
        stage.addActor(table);


        //set the input processor to the stage.. dunno what this is yet.
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        stage.act(delta);
        stage.draw();

//        vp.apply();
//        camera.update();
//        game.batch.setProjectionMatrix(vp.getCamera().combined);


//        game.batch.begin();
//        game.batch.draw(backGround, 0, 0, game.CAMX, game.CAMY);
//        game.font.draw(game.batch, layout, game.CAMX / 2 - layout.width/2, game.CAMY / 2 + layout.height/2);
//        game.batch.draw(idleDoge.getTexture(),180,70);
//        game.batch.end();

        idleDoge.update(delta);
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
//            game.setScreen(new GameScreen(game, score));
//            dispose();
//        }

    }

    @Override
    public void resize(int width, int height) {
//            vp.update(width, height);
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
        music.dispose();
    }
}
