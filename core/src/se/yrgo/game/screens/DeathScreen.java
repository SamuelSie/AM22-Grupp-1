package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.SuchJump;
import se.yrgo.game.sprites.idle.DeathScreenBg;
import se.yrgo.game.sprites.idle.IdleTrogdor;
import se.yrgo.utils.Difficulty;
import se.yrgo.utils.Score;

import java.sql.SQLException;

public class DeathScreen implements Screen {
    private final SuchJump game;
    private DeathScreenBg backGround;
    private IdleTrogdor idleTrogdor;
    private Music music;
    private Score score;
    private Stage stage;
    private Skin skin;
    private boolean canRestart;
    private Timer.Task restartTask;
    private TextButton playAgain;
    private Texture burninatingTexture;
    private Texture ouchTexture;
    
    public DeathScreen(final SuchJump game, Score score) {

        this.game = game;
        this.score = score;

        music = Gdx.audio.newMusic(Gdx.files.internal("music/DeathMusic.mp3"));
        music.setLooping(true);

       

        backGround = new DeathScreenBg();

        idleTrogdor = new IdleTrogdor();
        burninatingTexture = new Texture("much_burninating.png");
        ouchTexture = new Texture("ouch.png");

        skin = new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));
        playAgain = new TextButton("Play again", skin);
        playAgain.getLabel().setFontScale(.4f);
        playAgain.setColor(1f,1f,1f,0.8f);
        delayRestart();
    }
    
    private void delayRestart(){
        canRestart = false;
        playAgain.setDisabled(true);
        restartTask = new Timer.Task() {
            public void run() {
                canRestart = true;
                playAgain.setDisabled(false);
            }
        };
        Timer.schedule(restartTask, 1f);
    }
    
    private void restart(){
        if (canRestart && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || playAgain.isChecked())) {
            restartTask.cancel();
            game.setScreen(new GameScreen(game, score));
            dispose();
        }
    }

    @Override
    public void show() {
        music.play();

        stage = new Stage(new FitViewport(SuchJump.CAMX, SuchJump.CAMY));
        Table highscoreTable = new Table();
        highscoreTable.setBounds(SuchJump.CAMX / 2f, SuchJump.CAMY / 2f, SuchJump.CAMX / 2f, SuchJump.CAMY / 2f);


        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = textFieldStyle.font;
        labelStyle.background = textFieldStyle.background;
        labelStyle.fontColor = textFieldStyle.fontColor;

        try {
            Label textField = new Label("Highscore " + Difficulty.getTable(), labelStyle);

            textField.setText(score.getHighscore());

            textField.setSize(10f, 10f);

            highscoreTable.add(textField);
            highscoreTable.padBottom(70);

        } catch (SQLException e) {
            throw new RuntimeException("Something wrong with reading from highscore: " + e.getMessage());
        }
        highscoreTable.toFront();
        highscoreTable.setColor(1f,1f,1f,0.8f);


        Table table = new Table();
        table.setFillParent(true);

        Label showScore = new Label("Wow! You got: " + score.getCurrentScore() + " points!", labelStyle);
        
        TextButton backToMenu = new TextButton("Back to menu", skin);
        backToMenu.getLabel().setFontScale(.4f);
        backToMenu.setColor(1f,1f,1f,0.8f);

        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // delayRestart();
               // game.setScreen(new GameScreen(game,score));
               // dispose();
            }
        });
        backToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game, score));
                dispose();
            }
        });

        //adding doge words

        Image burninating = new Image(burninatingTexture);
        burninating.setPosition(230, 50);
        burninating.setSize(100, 40);


        Image ouch = new Image(ouchTexture);
        ouch.setPosition(115, 80);
        ouch.setSize(60, 30);

        float buttonWidth = SuchJump.CAMX / 5f;
        float buttonHeight = buttonWidth * 0.3f;
        table.add(showScore);
        table.row();
        table.add(playAgain).size(buttonWidth, buttonHeight);
        table.row();
        table.add(backToMenu).size(buttonWidth, buttonHeight);

        stage.addActor(backGround);
        stage.addActor(table);
        stage.addActor(highscoreTable);
        stage.addActor(idleTrogdor);
        stage.addActor(burninating);
        stage.addActor(ouch);

        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        stage.act(delta);

        stage.draw();
        
        restart();
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // No pause implemented yet
    }

    @Override
    public void resume() {
        // No resume implemented yet
    }

    @Override
    public void hide() {
        //Not used
    }

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();
        idleTrogdor.dispose();
        backGround.dispose();
        ouchTexture.dispose();
        burninatingTexture.dispose();
    }
}
