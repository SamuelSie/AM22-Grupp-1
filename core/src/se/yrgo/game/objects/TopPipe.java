package se.yrgo.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class TopPipe {
    
    private Texture toptubeImg;
    private Rectangle hitBox;
    private Vector3 position;
    
    public TopPipe(int x, int y) {
        position = new Vector3(x, y,0);
        toptubeImg = new Texture("toptube.png");
        hitBox = new Rectangle(x,y, toptubeImg.getWidth(), toptubeImg.getHeight());
    }
    
    public void dispose(){toptubeImg.dispose();}
    
    public Texture getToptubeImg() {
        return toptubeImg;
    }
    
    public Rectangle getHitBox() {
        return hitBox;
    }
    
    public Vector3 getPosition() {
        return position;
    }
}

