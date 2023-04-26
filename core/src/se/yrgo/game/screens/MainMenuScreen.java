package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.SuchJump;
import se.yrgo.game.sprites.idle.IdleDoge;
import se.yrgo.utils.Difficulty;
import se.yrgo.utils.Score;

import java.sql.SQLException;
import java.util.List;


public class MainMenuScreen implements Screen {

    public final SuchJump game;
    private GlyphLayout layout;
    private Score score;
    private Texture backGround;
    private Image backGroundImage;
    private IdleDoge idleDoge;
    private Music music;
    private Stage stage;
    private Skin skin;

    private Table mainTable;

    //difficulties
    private ButtonGroup<TextButton> buttonGroup;
    private float buttonWidth;
    private float buttonHeight;
    private static String playerName;
    private Texture veryAdventureTexture;
    private Texture suchBeautifulTexture;


    public MainMenuScreen(final SuchJump game, Score score) {
        this.game = game;
        this.score = score;

        layout = new GlyphLayout();

        backGround = new Texture("mainMenuBg.png");
        //lägger in texture i image
        backGroundImage = new Image(backGround);
        veryAdventureTexture = new Texture("very_adventure.png");
        suchBeautifulTexture = new Texture("such_beautiful.png");

        idleDoge = new IdleDoge();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/tmp2.mp3"));


        //skins innehåller massa bös som font och bilder
        skin = new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));

        //difficulties
        buttonGroup = new ButtonGroup<TextButton>();
        buttonWidth = SuchJump.CAMX / 5f;
        buttonHeight = buttonWidth * 0.3f;
    }


    // start the playback of the background music immediately


    @Override
    public void show() {

//        layout.setText(game.getFont(), "Press SPACE to start");
        music.setLooping(true);
        music.play();

        //scene2d stuff
        //creating the stage, table and buttons
        stage = new Stage(new FitViewport(SuchJump.CAMX, SuchJump.CAMY));
        mainTable = new Table();
        mainTable.setFillParent(true);

        TextButton playButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //difficulties
        CheckBox easyButton = new CheckBox("Easy", skin);
        CheckBox mediumButton = new CheckBox("Medium", skin);
        CheckBox hardButton = new CheckBox("Hard", skin);
        buttonGroup.add(easyButton);
        buttonGroup.add(mediumButton);
        buttonGroup.add(hardButton);

        easyButton.getLabel().setFontScale(.5f);
        mediumButton.getLabel().setFontScale(.5f);
        hardButton.getLabel().setFontScale(.5f);
        exitButton.getLabel().setFontScale(.5f);

        TextButton highscoreButton = new TextButton("Highscore", skin);
        highscoreButton.getLabel().setFontScale(0.5f);

        //button functionality
        buttonListeners(playButton, exitButton, easyButton, mediumButton, hardButton, highscoreButton);

        //setting button color
        playButton.setColor(1f,1f,1f,0.8f);
        easyButton.setColor(1f,1f,1f,0.8f);
        mediumButton.setColor(1f,1f,1f,0.8f);
        hardButton.setColor(1f,1f,1f,0.8f);
        highscoreButton.setColor(1f,1f,1f,0.8f);
        exitButton.setColor(1f,1f,1f,0.8f);
    
        //Setting button-text opacity
        Color textColor = easyButton.getLabel().getColor();
        textColor.a =1.5f;
    
        textColor = mediumButton.getLabel().getColor();
        textColor.a =1.5f;
    
        textColor = hardButton.getLabel().getColor();
        textColor.a =1.5f;
        
        

        //adding the buttons to the table
        mainTable.add(playButton).padBottom(20);
        mainTable.row();

        mainTable.add(easyButton).size(buttonWidth, buttonHeight);
        mainTable.row();
        mainTable.add(mediumButton).size(buttonWidth, buttonHeight);
        mainTable.row();
        mainTable.add(hardButton).size(buttonWidth, buttonHeight).padBottom(20);
        mainTable.row();
        mainTable.add(highscoreButton).size(buttonWidth, buttonHeight);
        mainTable.row();
        mainTable.add(exitButton).size(buttonWidth, buttonHeight);


        //adding dogeWords

        Image suchBeautiful = new Image(suchBeautifulTexture);
        suchBeautiful.setPosition(250, 30);
        suchBeautiful.setSize(90, 40);


        Image veryAdventure = new Image(veryAdventureTexture);
        veryAdventure.setPosition(50, 80);
        veryAdventure.setSize(90, 40);



        //adding the table to the stage
        stage.addActor(backGroundImage);
        stage.addActor(mainTable);
        stage.addActor(suchBeautiful);
        stage.addActor(veryAdventure);
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Not used
    }

    @Override
    public void resume() {
        // Not used
    }

    @Override
    public void hide() {
        // Not used
    }

    @Override
    public void dispose() {
        stage.dispose();

        idleDoge.dispose();
        music.dispose();
        backGround.dispose();
        veryAdventureTexture.dispose();
        suchBeautifulTexture.dispose();

    }

    private void buttonListeners(final TextButton playButton, TextButton exitButton, final TextButton easyButton,
                                 final TextButton mediumButton, final TextButton hardButton,
                                 final TextButton highscoreButton) {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createWindow();
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
        highscoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showHighscore();
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

    private void createWindow() {
        mainTable.setVisible(false);

        BitmapFont fontStyle = skin.getFont("button");
        fontStyle.getData().setScale(0.7f,0.7f);

        Color fontColor = Color.BLACK;
        Drawable background = skin.newDrawable("white", new Color(0, 0, 0, 0f));
        Window.WindowStyle style = new Window.WindowStyle(fontStyle, fontColor, background);

        // Creating pop up-window
        final Window window = new Window("Enter your name (3 letters)", style);
        window.padTop(20f);

        // Input player name

        final TextField textField = new TextField("",skin);
        textField.getStyle().font.getData().setScale(0.7f,0.7f);
        textField.setMaxLength(3);
        textField.setColor(1f,1f,1f,0.8f);
        window.add(textField).padBottom(20f);

        // Start game
        window.row();
        final TextButton startButton = new TextButton("start", skin);
        startButton.setColor(1f,1f,1f,0.8f);
        window.add(startButton).size(buttonWidth * 1.5f, buttonHeight * 1.5f).padBottom(20f);
        // Back to main menu
        window.row();
        TextButton backButton = new TextButton("back", skin);
        backButton.getLabel().setFontScale(0.5f);
        backButton.setColor(1f,1f,1f,0.8f);
        window.add(backButton).size(buttonWidth, buttonHeight);

        window.pack();

        // Option to press ENTER to play
        textField.addListener(new InputListener() {
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    simulateClick(startButton);
                }
                return false;
            }
        });

        window.setPosition(SuchJump.CAMX / 2f - window.getWidth() / 2, SuchJump.CAMY / 2f - window.getHeight() / 3);
        stage.addActor(window);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, score));
                //fetching the playerName when start is pressed
                createPlayerName(textField);
                dispose();
            }

        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.setVisible(true);
                window.remove();
            }

        });
    }

    private void showHighscore() {
        mainTable.setVisible(false);

        // Window style
        BitmapFont fontStyle = skin.getFont("font");
        Color fontColor = Color.BLACK;
        Drawable background = skin.newDrawable("window", new Color(0, 0, 0, 0f));
        Window.WindowStyle style = new Window.WindowStyle(fontStyle, fontColor, background);

        // Creating pop up-window
        final Window window = new Window("Highscore", style);
        window.add().colspan(3);
        window.padTop(20f);
        window.setFillParent(true);
        window.center();

        // showHighscore style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("font");
        labelStyle.background = skin.newDrawable("window", new Color(0, 0, 0, 0.5f));
        labelStyle.fontColor = Color.WHITE;

        // create three highscores in a table
        try {
            List<String> threeHighscores = score.getAllHighscore();
            String[] difficulties = {"easy", "medium", "hard"};
            window.getCells().removeIndex(0);

            for (int i = 0; i < threeHighscores.size(); i++) {
                Label highscore = new Label(difficulties[i], labelStyle);
                highscore.setAlignment(Align.top);
                highscore.setText(threeHighscores.get(i));
                window.add(highscore).fillY().padRight(5f).padLeft(5f);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something wrong with reading from highscore: " + e.getMessage());
        }

        // Back to main menu

        window.row();
        TextButton backButton = new TextButton("back", skin);
        backButton.getLabel().setFontScale(0.5f);
        backButton.setColor(1f,1f,1f,0.8f);
        window.add(backButton).size(buttonWidth, buttonHeight).colspan(3);

//        window.pack();

//        window.setPosition(0, JumpyBirb.CAMY / 2f - window.getHeight() / 3);
        stage.addActor(window);


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.setVisible(true);
                window.remove();
            }

        });
    }

    private static void createPlayerName(final TextField textField) {
        String name = textField.getText().toLowerCase();
        if (name.length() < 3) {
            name = "n/a";
        }
        playerName = name;
    }

    private static void simulateClick(TextButton button) {
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        button.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        button.fire(event2);
    }

    public static String getPlayerName() {
        return playerName;
    }
}
