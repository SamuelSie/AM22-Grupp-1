package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import se.yrgo.utils.Animation;

public class IdleDoge extends Actor {
    private Animation animation;
    private Texture dogeRegion;
    
    public IdleDoge(){
        dogeRegion = new Texture("dogeIdle.png");
        animation = new Animation(new TextureRegion(dogeRegion),2,1f);
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
    public void update(float delta){
        animation.update(delta);
    }
    public TextureRegion getTexture(){
        return animation.getFrame();
    }
}
