package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.utils.Animation;

public class Pipe implements Movable {
    private boolean isScored;
    private Texture topPipeImg;
    private Rectangle hitBoxTop;
    private Rectangle hitBoxSalad;
    private Vector3 positionTop;
    private Vector3 positionBottom;
    private Texture saladFingersImg;



    private Animation saladAnimation;

    private static final int DISTANCE = 140;

    public Pipe(int x, int y) {
        topPipeImg = new Texture("toptube.png");
        hitBoxTop = new Rectangle(x, y, topPipeImg.getWidth(), topPipeImg.getHeight());

        saladFingersImg = new Texture("saladFingersAnimation.png");
        saladAnimation = new Animation(new TextureRegion(saladFingersImg), 3, 0.8f);
        hitBoxSalad = new Rectangle(x, y, saladFingersImg.getWidth() / 3, saladFingersImg.getHeight());

        positionBottom = new Vector3(x, y, 0);
        positionTop = new Vector3(x, y + saladFingersImg.getHeight() + DISTANCE, 0);
    }

    @Override
    public void move() {
        getPositionBottom().x -= 100 * Gdx.graphics.getDeltaTime();
        getPositionTop().x -= 100 * Gdx.graphics.getDeltaTime();
        hitBoxTop.setPosition(getPositionBottom().x, getPositionBottom().y + saladFingersImg.getHeight() + DISTANCE);
        hitBoxSalad.setPosition(getPositionBottom().x, getPositionBottom().y);

    }

    public void dispose() {
        topPipeImg.dispose();
        saladFingersImg.dispose();
    }

    public Texture getTopPipeImg() {
        return topPipeImg;
    }

    public TextureRegion getSaladFingersImg() {
        return saladAnimation.getFrame();
    }

    public Rectangle getHitBoxTop() {
        return hitBoxTop;
    }

    public Rectangle getHitBoxSalad() {
        return hitBoxSalad;
    }

    public Vector3 getPositionTop() {
        return positionTop;
    }

    public Vector3 getPositionBottom() {
        return positionBottom;
    }


    public void setScored(boolean scored) {
        isScored = scored;
    }

    public boolean isScored() {
        return isScored;
    }

    public static int getDISTANCE() {
        return DISTANCE;
    }

    public Animation getSaladAnimation() {
        return saladAnimation;
    }
}
