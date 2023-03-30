package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.game.JumpyBirb;

import java.util.Iterator;

public class GameBackgroundSky {
    private Texture background;
    private Vector3 position;

    public GameBackgroundSky(int x, int y) {
        background = new Texture("gameScreenBgSky.png");
        position = new Vector3(x, y, 0);
    }


    public void draw(JumpyBirb game) {
        game.batch.draw(getBackground(), getPosition().x, getPosition().y, getBackground().getWidth(), getBackground().getHeight());
    }


    public void move() {
        getPosition().x -= 10 * Gdx.graphics.getDeltaTime();
    }

    
    public void remove(Iterator<Movable> iter) {
        if (getPosition().x + getBackground().getWidth() < 0) {
            dispose();
            iter.remove();
        }
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
