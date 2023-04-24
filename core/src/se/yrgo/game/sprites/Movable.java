package se.yrgo.game.sprites;

import se.yrgo.game.SuchJump;

import java.util.Iterator;

public interface Movable {
    public void draw(SuchJump game);
    public void move();
    public void remove(Iterator<Movable> iter);
}
