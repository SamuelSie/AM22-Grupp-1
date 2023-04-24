package se.yrgo.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import se.yrgo.game.SuchJump;

public class GameBackground  {
    private Texture background;

    private Vector3 position;
    int srcX;

    public GameBackground(int x, int y) {
        background = new Texture("gameScreenBg.png");
        position = new Vector3(x, y, 0);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        srcX = 0;
    }

    public void draw(SuchJump game) {
        game.getBatch().draw(background, position.x, position.y, srcX, 0, background.getWidth(), background.getHeight());
        srcX += 1;
    }
    public void dispose() {
        background.dispose();
    }
}
