package se.yrgo.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.utils.Animation;

public class Doge {
    private Rectangle hitboxA;
    private Rectangle hitboxB;
    private int dogeWidth;
    private int dogeHeight;

    private float fallSpeed;
    
    private Vector3 position;
    private Vector3 velocity;
    private Texture dogeImg;



    private Animation animation;
    
    
    public Doge(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        fallSpeed = -14;
        
        dogeImg = new Texture("dogeJump.png");
        animation = new Animation(new TextureRegion(dogeImg), 4, 0.3f);
        dogeWidth = 45;
        dogeHeight = 38;
        hitboxA = new Rectangle(x + (dogeWidth / 2), y, dogeWidth / 2, dogeHeight);
        hitboxB = new Rectangle(x, y, dogeWidth, dogeHeight / 2);
    }
    
    public void update(float delta) {
        
        animation.jumpAnimation(delta);
        
        
        velocity.add(0, fallSpeed, 0);
        
        // gångrar allt i velocity med delta
        // hastigheten blir helt galen om vi inte gångrar med deltatime
        velocity.scl(delta);
        
        //adderar hastigheten i form av antal pixlar till position.
        position.add(0, velocity.y, 0);
        hitboxA.x = position.x + (dogeWidth / 2);
        hitboxA.y = position.y;
        hitboxB.x = position.x;
        hitboxB.y = position.y;
        
        //nollställer för nästa uppdatering
        velocity.scl(1 / delta);
        
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
        dogeImg.dispose();
    }
    
    public TextureRegion getTexture() {
        return animation.getFrame();
    }
    
    public Vector3 getPosition() {
        return position;
    }
    
    //outdated?
    public Rectangle getHitbox() {
        return hitboxA;
    }
    
    public boolean isCollided(Rectangle rect) {
        if (hitboxA.overlaps(rect) || hitboxB.overlaps(rect)) return true;
        return false;
    }
    public Animation getAnimation() {
        return animation;
    }
}
