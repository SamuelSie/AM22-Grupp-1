package se.yrgo.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Doge {
    private Texture dogeImg;
    private float fallSpeed;

    private Vector3 position;
    private Vector3 velocity;

    public Doge (int x, int y){
        dogeImg = new Texture("dogeBodyImg.png");

        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        fallSpeed = -10;

    }

    public void fall(float deltaTime){
        velocity.add(0, fallSpeed, 0);

        // gångrar allt i velocity med deltaTime
        // har inte helt fattat varför detta behövs än, men hastigheten blir helt galen utan det.
        velocity.scl(deltaTime);

        //adderar hastigheten i form av antal pixlar till position.
        position.add(0, velocity.y, 0);

        //nollställer för nästa uppdatering
        velocity.scl(1/deltaTime);

    }
    //en kommentar för att visa merge
    public void jump(float deltaTime){
        //sätter hastighet i y-axis till 300
        velocity.y = 300;
    }

    public Texture getTexture() {
        return dogeImg;
    }

    public Vector3 getPosition() {
        return position;
    }
}
