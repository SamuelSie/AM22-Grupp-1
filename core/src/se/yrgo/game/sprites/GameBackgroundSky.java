package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.game.JumpyBirb;

import java.util.Iterator;

public class GameBackgroundSky {
    private Texture background;
    private Vector3 position;
    int srcX;

    public GameBackgroundSky(int x, int y) {
        background = new Texture("gameScreenBgSky.png");
        position = new Vector3(x, y, 0);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        srcX = 0;
    }

    public void draw(JumpyBirb game) {
        game.getBatch().draw(background, position.x, position.y, srcX, 0, background.getWidth(), background.getHeight());
        srcX += 0;
    }

    public void dispose() {
        background.dispose();
    }
}
