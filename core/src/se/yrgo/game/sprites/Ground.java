package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.game.JumpyBirb;

import java.util.Iterator;

public class Ground implements Movable {

    private Texture groundImg;
    private Rectangle hitBox;
    private Vector3 position;

    public Ground(int x, int y) {
        groundImg = new Texture("ground.png");
        hitBox = new Rectangle(x, y, groundImg.getWidth() * 2, (groundImg.getHeight()));

        position = new Vector3(x, y, 0);

    }

    public void dispose() {
        groundImg.dispose();
    }

    public Texture getTexture() {
        return groundImg;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Vector3 getPosition() {
        return position;
    }

    @Override
    public void draw(JumpyBirb game) {
        game.batch.draw(getTexture(), getPosition().x, getPosition().y, getTexture().getWidth() * 2, getTexture().getHeight());
    }

    @Override
    public void move() {
        getPosition().x -= 100 * Gdx.graphics.getDeltaTime();
        hitBox.setPosition(getPosition().x, getPosition().y);
    }

    @Override
    public void remove(Iterator<Movable> iter) {
        if (getPosition().x + getHitBox().getWidth() * 2 < 0) {
            dispose();
            iter.remove();
        }
    }
}
