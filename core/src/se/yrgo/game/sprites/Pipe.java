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
    private Rectangle hitBoxSalad;
    private Vector3 positionTop;
    private Vector3 positionBottom;
    private Texture saladFingersImg;



    private Animation saladAnimation;

    private static final int DISTANCE = 140;

    public Pipe(int x, int y) {
        kettleImg = new Texture("rustyKettle.png");
        hitBoxKettle = new Rectangle(x, y, kettleImg.getWidth(), kettleImg.getHeight());

        saladFingersImg = new Texture("saladFingersAnimation.png");
        saladAnimation = new Animation(new TextureRegion(saladFingersImg), 3, 0.8f);
        hitBoxSalad = new Rectangle(x, y, saladFingersImg.getWidth() / 3, saladFingersImg.getHeight());

        positionBottom = new Vector3(x, y, 0);
        positionTop = new Vector3(x, y + saladFingersImg.getHeight() + DISTANCE, 0);
    }

    @Override
    public void draw(JumpyBirb game) {
        game.batch.draw(getKettleImg(), getPositionTop().x, getPositionTop().y, 40, 250);
        game.batch.draw(getSaladFingersImg(), getPositionBottom().x, getPositionBottom().y, 40, 320);
    }

    @Override
    public void move() {
        getPositionBottom().x -= 100 * Gdx.graphics.getDeltaTime();
        getPositionTop().x -= 100 * Gdx.graphics.getDeltaTime();
        hitBoxKettle.setPosition(getPositionBottom().x, getPositionBottom().y + saladFingersImg.getHeight() + DISTANCE);
        hitBoxSalad.setPosition(getPositionBottom().x, getPositionBottom().y);

    }

    @Override
    public void remove(Iterator<Movable> iter) {
        if (getPositionTop().x + getHitBoxKettle().getWidth() < 0
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
