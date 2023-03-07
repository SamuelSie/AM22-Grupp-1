package se.yrgo.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ground {

    private Texture groundImg;
    private Rectangle hitBox;
    private Vector3 position;

    public Ground(int x, int y) {
        groundImg = new Texture("ground.png");
        hitBox = new Rectangle(x, y, groundImg.getWidth(), (groundImg.getHeight()));

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

    public void move() {
        getPosition().x -= 100 * Gdx.graphics.getDeltaTime();
        hitBox.setPosition(getPosition().x, getPosition().y);
    }
}
