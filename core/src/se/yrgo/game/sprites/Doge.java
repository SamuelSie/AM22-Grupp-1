package se.yrgo.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.utils.Animation;
import se.yrgo.utils.Difficulty;

public class Doge {
    private Rectangle hitBoxHead;
    private Rectangle hitBoxBody;
    private int dogeWidth;
    private int dogeHeight;
    private Vector3 position;
    private Vector3 velocity;
    private Texture dogeImg;
    private int offset;
    private Animation animation;
    
    
    public Doge(int x, int y) {
        offset = 5;
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);

        
        dogeImg = new Texture("dogeJump.png");
        animation = new Animation(new TextureRegion(dogeImg), 4, 0.3f);
        dogeWidth = 45;
        dogeHeight = 38;
        hitBoxHead = new Rectangle(x + (dogeWidth / 2f), y, dogeWidth / 2f - (offset * 2f), dogeHeight - (offset * 2f));
        hitBoxBody = new Rectangle(x, y, dogeWidth - (offset * 2f), dogeHeight / 2f - (offset * 2f));
    }
    
    public void update(float delta) {
        
        animation.jumpAnimation(delta);
        
        
        velocity.add(0, Difficulty.getGravity(), 0);
        
        // gångrar allt i velocity med delta
        // hastigheten blir helt galen om vi inte gångrar med deltatime
        velocity.scl(delta);
        
        //adderar hastigheten i form av antal pixlar till position.
        position.add(0, velocity.y, 0);
        hitBoxHead.setPosition(getPosition().x + (dogeWidth / 2f) + offset, getPosition().y + offset);
        hitBoxBody.setPosition(position.x + offset, position.y + offset);

        
        //nollställer för nästa uppdatering
        velocity.scl(1 / delta);
        
    }
    
    //en kommentar för att visa merge
    public void jump() {
        //sätter hastighet i y-axis enligt Difficulty.dogeJumpVelocity
        velocity.y = Difficulty.getDogeJumpVelocity();
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
        return hitBoxHead;
    }
    
    public boolean isCollided(Rectangle rect) {
        return hitBoxHead.overlaps(rect) || hitBoxBody.overlaps(rect);
    }
    public Animation getAnimation() {
        return animation;
    }
}
