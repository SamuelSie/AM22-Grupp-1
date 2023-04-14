package se.yrgo.utils;

public class Difficulty {
    private static int speed = 100;
    private static long pipeSpawnRate = 300000000000L / speed;
    private static int pipeDistance = 140;
    private static int gravity = -14;
    private static int dogeJumpVelocity = 300;

    public static void easy() {
        speed = 100;
        pipeSpawnRate = 300000000000L / speed;
        pipeDistance = 140;
        gravity = -14;
        dogeJumpVelocity = 300;
    }

    public static void medium() {
        speed = 200;
        pipeSpawnRate = 300000000000L / speed;
        pipeDistance = 120;
        gravity = -24;
        dogeJumpVelocity = 400;
    }

    public static void hard() {
        speed = 300;
        pipeSpawnRate = 300000000000L / speed;
        pipeDistance = 100;
        gravity = -34;
        dogeJumpVelocity = 500;
    }

    public static int getSpeed() {
        return speed;
    }

    public static long getPipeSpawnRate() {
        return pipeSpawnRate;
    }

    public static int getPipeDistance() {
        return pipeDistance;
    }

    public static int getGravity() {
        return gravity;
    }

    public static int getDogeJumpVelocity() {
        return dogeJumpVelocity;
    }
}
