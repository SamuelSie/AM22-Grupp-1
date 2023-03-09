package se.yrgo.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IdleDoge {
    private Animation animation;
    private Texture dogeRegion;
    
    public IdleDoge(){
        dogeRegion = new Texture("dogeIdle.png");
        animation = new Animation(new TextureRegion(dogeRegion),2,1f);
    }
    public void update(float delta){
        animation.update(delta);
    }
    public TextureRegion getTexture(){
        return animation.getFrame();
    }
}
