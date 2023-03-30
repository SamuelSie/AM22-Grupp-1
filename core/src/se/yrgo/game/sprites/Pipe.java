package se.yrgo.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.game.JumpyBirb;
import se.yrgo.utils.Animation;

import java.util.Iterator;

public class Pipe implements Movable {
    private boolean isScored;
    private Texture kettleImg;
    private Rectangle hitBoxKettle;
    private Rectangle hitBoxChain;
    private int kettleWidth;
    private int kettleHeight;
    private  int saladWidth;
    private  int saladHeight;

    private Rectangle hitBoxSalad;
    private Vector3 positionKettle;
    private Vector3 positionBottom;
    private Texture saladFingersImg;



    private Animation saladAnimation;

    private static final int DISTANCE = 140;

    public Pipe(int x, int y) {
        saladFingersImg = new Texture("saladFingersAnimation.png");
        saladAnimation = new Animation(new TextureRegion(saladFingersImg), 3, 0.8f);
        hitBoxSalad = new Rectangle(x, y, saladFingersImg.getWidth() / 3, saladFingersImg.getHeight());

        positionBottom = new Vector3(x, y, 0);
        positionKettle = new Vector3(x, y + saladFingersImg.getHeight() + DISTANCE, 0);

        kettleWidth = 40;
        kettleHeight = 250;
        kettleImg = new Texture("rustyKettle.png");
        hitBoxKettle = new Rectangle(x, y, kettleWidth, kettleHeight);
        hitBoxChain = new Rectangle(x + (kettleWidth / 2), y, kettleWidth / 7, kettleHeight);


    }

    @Override
    public void draw(JumpyBirb game) {
        game.batch.draw(getKettleImg(), getPositionKettle().x, getPositionKettle().y, kettleWidth, kettleHeight);
        game.batch.draw(getSaladFingersImg(), getPositionBottom().x, getPositionBottom().y, saladFingersImg.getWidth() / 3, saladFingersImg.getHeight());
    }

    @Override
    public void move() {
        getPositionBottom().x -= 100 * Gdx.graphics.getDeltaTime();
        getPositionKettle().x -= 100 * Gdx.graphics.getDeltaTime();

        hitBoxKettle.setPosition(getPositionKettle().x, getPositionKettle().y);

        hitBoxChain.setPosition(getPositionKettle().x + (kettleWidth / 2), getPositionKettle().y);
        hitBoxSalad.setPosition(getPositionBottom().x, getPositionBottom().y);

    }

    @Override
    public void remove(Iterator<Movable> iter) {
        if (getPositionKettle().x + getHitBoxKettle().getWidth() < 0
                || getPositionBottom().x + getHitBoxSalad().getWidth() < 0) {
            dispose();
            iter.remove();
        }
    }

    public void dispose() {
        kettleImg.dispose();
        saladFingersImg.dispose();
    }

    public Texture getKettleImg() {
        return kettleImg;
    }

    public TextureRegion getSaladFingersImg() {
        return saladAnimation.getFrame();
    }

    public Rectangle getHitBoxKettle() {
        return hitBoxKettle;
    }

    public Rectangle getHitBoxChain() {
        return hitBoxChain;
    }

    public Rectangle getHitBoxSalad() {
        return hitBoxSalad;
    }

    public Vector3 getPositionKettle() {
        return positionKettle;
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
