package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameBackground implements Movable {
    private Texture background;

    private Vector3 position;

    public GameBackground(int x, int y) {
        background = new Texture("gameScreenBg.png");
        position = new Vector3(x, y, 0);
    }

    @Override
    public void move() {
        getPosition().x -= 50 * Gdx.graphics.getDeltaTime();
    }

    public Texture getBackground() {
        return background;
    }


    public Vector3 getPosition() {
        return position;
    }

    public void dispose() {
        background.dispose();
    }
}
