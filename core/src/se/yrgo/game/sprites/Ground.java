package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.game.JumpyBirb;
import se.yrgo.utils.Difficulty;

import java.util.Iterator;

public class Ground implements Movable {

    private Texture texture;
    private Rectangle hitBox;
    private Vector3 position;
    private int srcX;

    public Ground(int x, int y, JumpyBirb game) {
        texture = new Texture("ground.png");
        hitBox = new Rectangle(x, y, game.CAMX, texture.getHeight() - 42);
        position = new Vector3(x, y, 0);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        srcX = 0;

    }

    public void dispose() {
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Vector3 getPosition() {
        return position;
    }

    @Override
    public void draw(JumpyBirb game) {
        game.batch.draw(texture,0,0, srcX, 0, texture.getWidth(), texture.getHeight());
        srcX += Difficulty.getGroundSpeed();
    }


    @Override
    public void move() {
        getPosition().x -= 100 * Gdx.graphics.getDeltaTime();

    }

    @Override
    public void remove(Iterator<Movable> iter) {
        if (getPosition().x + getHitBox().getWidth() < 0) {
            dispose();
            iter.remove();
        }
    }
}
