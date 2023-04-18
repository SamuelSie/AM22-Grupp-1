package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.sprites.idle.IdleDoge;
import se.yrgo.utils.Difficulty;
import se.yrgo.utils.Score;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MainMenuScreen implements Screen {

    public final JumpyBirb game;
    private GlyphLayout layout;
    private Score score;
    private Texture backGround;

    //behöver göra en image av bgTexture för att använda.. tror jag
    private Image backGroundImage;

    private IdleDoge idleDoge;
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

        backGround = new Texture("mainMenuBg.png");
        //lägger in texture i image
        backGroundImage = new Image(backGround);

        idleDoge = new IdleDoge();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/tmp2.mp3"));


        //skins innehåller massa bös som font och bilder
        skin = new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));

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

        //button functionality
        buttonListeners(startButton, exitButton, easyButton, mediumButton, hardButton);
//            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
//
//            }

        //adding the buttons to the table
        table.add(startButton).padBottom(20);
        table.row();

        table.add(easyButton).size(buttonWidth, buttonHeight);
        table.row();
        table.add(mediumButton).size(buttonWidth, buttonHeight);
        table.row();
        table.add(hardButton).size(buttonWidth, buttonHeight).padBottom(20);
        table.row();
        table.add(exitButton).size(buttonWidth, buttonHeight);


        //adding the table to the stage
        stage.addActor(backGroundImage);
        stage.addActor(table);
        stage.addActor(idleDoge);

        //set the input processor to the stage
        Gdx.input.setInputProcessor(stage);

        switch (game.getLastDifficulty()) {
            case "medium":
                simulateClick(mediumButton);
                break;
            case "hard":
                simulateClick(hardButton);
                break;
            default:
                simulateClick(easyButton);
                break;
        }

    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0,0,0.2f,1);
        stage.act(delta);

        Batch stageBatch = stage.getBatch();
        stageBatch.begin();
        stageBatch.draw(backGround, 0, 0);
        stageBatch.end();

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
        backGround.dispose();


    }

    private void buttonListeners(final TextButton startButton, TextButton exitButton, final TextButton easyButton, final TextButton mediumButton, final TextButton hardButton) {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, score));
                dispose();

            }
        });
        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                easyButton.getStyle().checked = easyButton.getStyle().down;
                Difficulty.easy();
            }
        });
        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mediumButton.getStyle().checked = mediumButton.getStyle().down;
                game.setLastDifficulty("medium");
                Difficulty.medium();
            }
        });

        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hardButton.getStyle().checked = hardButton.getStyle().down;
                game.setLastDifficulty("hard");
                Difficulty.hard();
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
                System.exit(-1);
            }
        });
    }

    private static void simulateClick(TextButton button) {
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        button.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        button.fire(event2);
    }
}
