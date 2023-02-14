package se.yrgo.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ground {

    private Texture groundImg;
    private Rectangle groundBox;
    private Vector3 position;

    public Ground(int x, int y) {
        groundImg = new Texture("ground.png");
        groundBox = new Rectangle(x, y, groundImg.getWidth(), groundImg.getHeight());

        position = new Vector3(x, y, 0);

    }

    public void dispose() {
        groundImg.dispose();
    }
    public Texture getTexture() {
        return groundImg;
    }

    public Rectangle getGroundBox() {
        return groundBox;
    }

    public Vector3 getPosition() {
        return position;
    }
}
