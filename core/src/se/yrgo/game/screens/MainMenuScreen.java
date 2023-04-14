package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.sprites.idle.IdleDoge;
import se.yrgo.utils.Score;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MainMenuScreen implements Screen {

    public final JumpyBirb game;
    private OrthographicCamera camera;
//    private Viewport vp;
    private GlyphLayout layout;
    private Score score;
    private Texture backGround;

    //behöver göra en image av bgTexture för att använda.. tror jag
    private Image backGroundImage;
    
    private IdleDoge idleDoge;
    private Image idleImage;
    private Music music;
    private Stage stage;
    private Skin skin;

    //difficulties
    private ButtonGroup buttonGroup;
    private float buttonWidth;
    private float buttonHeight;




    public MainMenuScreen(final JumpyBirb game, Score score) {
        this.game = game;
        this.score = score;

        layout = new GlyphLayout();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.CAMX, game.CAMY);

        backGround = new Texture("mainMenuBg.png");
        //lägger in texture i image
        backGroundImage = new Image(backGround);

        idleDoge = new IdleDoge();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/tmp2.mp3"));


        //skins innehåller massa bös som font och bilder
        skin= new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));

        //difficulties
        buttonGroup = new ButtonGroup();
        buttonWidth = game.CAMX / 5;
        buttonHeight = buttonWidth * 0.3f;
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

        //difficulties
        TextButton easyButton = new TextButton("Easy", skin);
        TextButton mediumButton = new TextButton("Medium", skin);
        TextButton hardButton = new TextButton("Hard", skin);
        buttonGroup.add(easyButton);
        buttonGroup.add(mediumButton);
        buttonGroup.add(hardButton);

        easyButton.getLabel().setFontScale(.5f);
        mediumButton.getLabel().setFontScale(.5f);
        hardButton.getLabel().setFontScale(.5f);
        exitButton.getLabel().setFontScale(.5f);

        //setting button sizes
//        easyButton.setBounds(0, 0, buttonWidth, buttonHeight);
//        mediumButton.setWidth(buttonWidth);
//        mediumButton.setHeight(buttonHeight);
//        hardButton.setBounds(0,0,buttonWidth,buttonHeight);

        //button functionality
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, score, 100));
                dispose();
            }
        });

//            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
//
//            }

        //adding the buttons to the table
        table.add(startButton).padBottom(20);
        table.row();

        table.add(easyButton).size(buttonWidth, buttonHeight);
        table.row();
        table.add(mediumButton).size(buttonWidth, buttonHeight);;
        table.row();
        table.add(hardButton).size(buttonWidth, buttonHeight).padBottom(20);;
        table.row();
        table.add(exitButton).size(buttonWidth, buttonHeight);


        //adding the table to the stage
        stage.addActor(idleDoge);
        stage.addActor(table);
        idleDoge.toFront();


        //set the input processor to the stage.. dunno what this is yet.
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0,0,0.2f,1);
        stage.act(delta);
        stage.draw();

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
        stage.dispose();
        idleDoge.dispose();
        music.dispose();
    }
}
