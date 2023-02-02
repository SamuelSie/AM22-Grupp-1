package se.yrgo.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Doge {
    private Texture doge;
    private Vector3 position;
    private Vector3 velocity;

    public Doge (int x, int y){
        doge = new Texture("dogeBodyImg.png");
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);

    }

    public Texture getDoge() {
        return doge;
    }

    public Vector3 getPosition() {
        return position;
    }
}
