package se.yrgo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.yrgo.game.JumpyBirb;
import se.yrgo.game.sprites.idle.DeathScreenBg;
import se.yrgo.game.sprites.idle.IdleTrogdor;
import se.yrgo.utils.Difficulty;
import se.yrgo.utils.Score;

import java.sql.SQLException;

public class DeathScreen implements Screen {
    private final JumpyBirb game;
    private DeathScreenBg backGround;
    private IdleTrogdor idleTrogdor;
    private Music music;
    private Score score;
    private Stage stage;
    private Skin skin;
    private boolean canRestart;
    private Timer.Task restartTask;

    public DeathScreen(final JumpyBirb game, Score score) {

        this.game = game;
        this.score = score;

        music = Gdx.audio.newMusic(Gdx.files.internal("music/DeathMusic.mp3"));
        music.setLooping(true);

        canRestart = false;

        restartTask = new Timer.Task() {
            public void run() {
                canRestart = true;
            }
        };
        Timer.schedule(restartTask, 1f);

        backGround = new DeathScreenBg();

        idleTrogdor = new IdleTrogdor();

        skin = new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));
    }

    @Override
    public void show() {
        music.play();

        stage = new Stage(new FitViewport(JumpyBirb.CAMX, JumpyBirb.CAMY));
        Table highscoreTable = new Table();
        highscoreTable.setBounds(JumpyBirb.CAMX / 2f, JumpyBirb.CAMY / 2f, JumpyBirb.CAMX / 2f, JumpyBirb.CAMY / 2f);


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


        Table table = new Table();
        table.setFillParent(true);


        TextButton playAgain = new TextButton("Press to play again", skin);
        TextButton backToMenu = new TextButton("Press to get back to menu", skin);

        playAgain.getLabel().setFontScale(.5f);
        backToMenu.getLabel().setFontScale(.5f);

        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, score));
                dispose();
            }
        });
        backToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game, score));
                dispose();
            }
        });

        table.add(playAgain);
        table.row();
        table.add(backToMenu);

        stage.addActor(backGround);
        stage.addActor(table);
        stage.addActor(highscoreTable);
        stage.addActor(idleTrogdor);

        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        stage.act(delta);

        stage.draw();

        //Add delay before screen transition
        if (canRestart && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
            restartTask.cancel();
            game.setScreen(new GameScreen(game, score));
            dispose();
        }
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
    }
}
