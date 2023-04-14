package se.yrgo.game.sprites.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import se.yrgo.utils.Animation;

public class IdleDoge extends Actor {
    private Animation animation;
    private Texture texture;
    
    public IdleDoge(){
        texture = new Texture("dogeIdle.png");
        animation = new Animation(new TextureRegion(texture),2,1f);
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        animation.update(delta);
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getFrame(), 180, 70);
    }

    public void dispose() {
        texture.dispose();
    }
}
