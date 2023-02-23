package se.yrgo.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Pipe {
    private boolean isScored;
    private Texture topPipeImg;
    private Texture bottomPipeImg;
    private Rectangle hitBoxTop;
    private Rectangle hitBoxBottom;
    private Vector3 positionTop;
    private Vector3 positionBottom;

    private static final int DISTANCE = 100;

    public Pipe(int x, int y) {

        topPipeImg = new Texture("toptube.png");
        hitBoxTop = new Rectangle(x, y, topPipeImg.getWidth(), topPipeImg.getHeight());

        bottomPipeImg = new Texture("bottomtube.png");
        hitBoxBottom = new Rectangle(x, y, bottomPipeImg.getWidth(), bottomPipeImg.getHeight());

        positionBottom = new Vector3(x, y, 0);
        positionTop = new Vector3(x, y + bottomPipeImg.getHeight() + DISTANCE, 0);
    }

    public void dispose() {
        topPipeImg.dispose();
        bottomPipeImg.dispose();
    }

    public Texture getTopPipeImg() {
        return topPipeImg;
    }

    public Texture getBottomPipeImg() {
        return bottomPipeImg;
    }

    public Rectangle getHitBoxTop() {
        return hitBoxTop;
    }

    public Rectangle getHitBoxBottom() {
        return hitBoxBottom;
    }

    public Vector3 getPositionTop() {
        return positionTop;
    }

    public Vector3 getPositionBottom() {
        return positionBottom;
    }

    public void move() {
        getPositionBottom().x -= 100 * Gdx.graphics.getDeltaTime();
        getPositionTop().x -= 100 * Gdx.graphics.getDeltaTime();
        hitBoxTop.setPosition(getPositionBottom().x, getPositionBottom().y + bottomPipeImg.getHeight() + DISTANCE);
        hitBoxBottom.setPosition(getPositionBottom().x, getPositionBottom().y);
    }

    public void setScored(boolean scored) {
        isScored = scored;
    }

    public boolean isScored() {
        return isScored;
    }
}
