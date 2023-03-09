package se.yrgo.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Doge {
    private Rectangle hitbox;
    private float fallSpeed;
    
    private Vector3 position;
    private Vector3 velocity;
    private Texture dogeRegion;
    private Animation animation;
    
    
    public Doge(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        fallSpeed = -14;
        
        dogeRegion = new Texture("dogeAnimation.png");
        animation = new Animation(new TextureRegion(dogeRegion), 2, 1f);
        hitbox = new Rectangle(x, y, dogeRegion.getWidth(), (dogeRegion.getHeight()));
    }
    
    public void update(float deltaTime) {
        
        animation.update(deltaTime);
        
        
        velocity.add(0, fallSpeed, 0);
        
        // gångrar allt i velocity med deltaTime
        // hastigheten blir helt galen om vi inte gångrar med deltatime
        velocity.scl(deltaTime);
        
        //adderar hastigheten i form av antal pixlar till position.
        position.add(0, velocity.y, 0);
        hitbox.x = position.x;
        hitbox.y = position.y;
        
        //nollställer för nästa uppdatering
        velocity.scl(1 / deltaTime);
        
    }
    
    //en kommentar för att visa merge
    public void jump(float deltaTime) {
        //sätter hastighet i y-axis till 300
        velocity.y = 300;
    }
    
    public void resetVelocity() {
        velocity.y = -10;
    }
    
    public void dispose() {
        dogeRegion.dispose();
    }
    
    public TextureRegion getTexture() {
        return animation.getFrame();
    }
    
    public Vector3 getPosition() {
        return position;
    }
    
    //outdated?
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    public boolean isCollided(Rectangle rect) {
        if (hitbox.overlaps(rect)) return true;
        return false;
    }
}
