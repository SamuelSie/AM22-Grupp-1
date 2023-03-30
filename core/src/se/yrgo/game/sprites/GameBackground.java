package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.game.JumpyBirb;

import java.util.Iterator;

public class GameBackground implements Movable {
    private Texture background;

    private Vector3 position;

    public GameBackground(int x, int y) {
        background = new Texture("gameScreenBg.png");
        position = new Vector3(x, y, 0);
    }

    @Override
    public void draw(JumpyBirb game) {
        game.batch.draw(getBackground(), getPosition().x,getPosition().y, getBackground().getWidth(), getBackground().getHeight());
    }

    @Override
    public void move() {
        getPosition().x -= 50 * Gdx.graphics.getDeltaTime();
    }

    @Override
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
