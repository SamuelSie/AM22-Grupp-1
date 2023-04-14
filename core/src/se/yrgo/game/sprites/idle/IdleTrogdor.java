package se.yrgo.game.sprites.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.yrgo.utils.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class IdleTrogdor extends Actor {
    private Animation animation;
    private Texture texture;

    public IdleTrogdor() {
        texture = new Texture("trogdor.png");
        animation = new Animation(new TextureRegion(texture), 4, 1f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animation.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        animation.getFrame();
    }

    public void dispose() {
        texture.dispose();
    }

}
