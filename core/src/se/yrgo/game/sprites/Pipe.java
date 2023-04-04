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
    private int saladWidth;
    private int saladHeight;

    private Rectangle hitBoxSaladBody;
    private Rectangle hitBoxSaladHand;
    private Vector3 positionKettle;
    private Vector3 positionSalad;
    private Texture saladFingersImg;


    private Animation saladAnimation;

    private static final int DISTANCE = 200;

    public Pipe(int x, int y) {
        saladWidth = 55;
        saladHeight = 320;
        saladFingersImg = new Texture("saladFingersAnimation.png");
        saladAnimation = new Animation(new TextureRegion(saladFingersImg), 3, 0.8f);
        hitBoxSaladBody = new Rectangle(x, y, saladWidth - 22, saladHeight);
        hitBoxSaladHand = new Rectangle(x, y, saladWidth, 27);

        positionSalad = new Vector3(x, y, 0);
        positionKettle = new Vector3(x, y + saladHeight + DISTANCE, 0);

        kettleWidth = 40;
        kettleHeight = 250;
        kettleImg = new Texture("rustyKettle.png");
        hitBoxKettle = new Rectangle(x, y, kettleWidth, 40);
        hitBoxChain = new Rectangle(x + (kettleWidth / 2), y, kettleWidth / 7, kettleHeight);


    }

    @Override
    public void draw(JumpyBirb game) {
        game.batch.draw(getKettleImg(), getPositionKettle().x, getPositionKettle().y, kettleWidth, kettleHeight);
        game.batch.draw(getSaladFingersImg(), getPositionSalad().x, getPositionSalad().y, saladFingersImg.getWidth() / 3, saladFingersImg.getHeight());
    }

    @Override
    public void move() {
        getPositionSalad().x -= 100 * Gdx.graphics.getDeltaTime();
        getPositionKettle().x -= 100 * Gdx.graphics.getDeltaTime();

        hitBoxKettle.setPosition(getPositionKettle().x, getPositionKettle().y);

        hitBoxChain.setPosition(getPositionKettle().x + (kettleWidth / 2), getPositionKettle().y);
        //Change magicnumber  to offset variable.
        hitBoxSaladBody.setPosition(getPositionSalad().x + 22, getPositionSalad().y);
        hitBoxSaladHand.setPosition(getPositionSalad().x, getPositionSalad().y + saladHeight - 40);

    }

    @Override
    public void remove(Iterator<Movable> iter) {
        if (getPositionSalad().x + saladWidth < 0) {
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

    public Rectangle getHitBoxSaladBody() {
        return hitBoxSaladBody;
    }

    public Vector3 getPositionKettle() {
        return positionKettle;
    }

    public Vector3 getPositionSalad() {
        return positionSalad;
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

    public Rectangle getHitBoxSaladHand() {
        return hitBoxSaladHand;
    }
}
