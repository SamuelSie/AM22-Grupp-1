package se.yrgo.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BottomPipes {
    
    private Texture bottomtubeImg;
    private Rectangle hitBox;
    private Vector3 position;
    
    public BottomPipes(int x, int y) {
        position = new Vector3(x, y,0);
        bottomtubeImg = new Texture("bottomtube.png");
        hitBox = new Rectangle(x,y, bottomtubeImg.getWidth(), bottomtubeImg.getHeight());
    }
    
    public void dispose(){bottomtubeImg.dispose();}
    
    public Texture getBottomtubeImg() {
        return bottomtubeImg;
    }
    
    public Rectangle getHitBox() {
        return hitBox;
    }
    
    public Vector3 getPosition() {
        return position;
    }
}
