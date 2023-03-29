package se.yrgo.game.sprites;

import se.yrgo.game.JumpyBirb;

import java.util.Iterator;

public interface Movable {
    public void draw(JumpyBirb game);
    public void move();
    public void remove(Iterator<Movable> iter);
}
