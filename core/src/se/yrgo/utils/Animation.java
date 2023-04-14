package se.yrgo.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;


    private boolean startJumpAnimation;

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float delta) {
        currentFrameTime += delta;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            frame = 0;
        }
    }

    public void jumpAnimation(float delta) {
        if (startJumpAnimation) {
            currentFrameTime += delta;
            if (currentFrameTime > maxFrameTime) {
                frame++;
                currentFrameTime = 0;
            }
            if (frame >= frameCount) {
                frame = 0;
            this.startJumpAnimation = false;
            }
        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    public TextureRegionDrawable getDrawableFrame() {
        return new TextureRegionDrawable(frames.get(frame));
    }

    public void setStartJumpAnimation(boolean startJumpAnimation) {

        this.startJumpAnimation = startJumpAnimation;
    }
}

