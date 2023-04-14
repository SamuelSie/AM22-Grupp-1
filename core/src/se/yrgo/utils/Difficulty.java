package se.yrgo.utils;

public class Difficulty {
    public static int speed;
    public static long pipeSpawnRate;
    public static int pipeDistance;
    public static int gravity;
    public static int dogeJumpVelocity;

    public Difficulty() {
        speed = 200;
        pipeSpawnRate = 300000000000L / speed;
        pipeDistance = 140;
        gravity = -24;
        dogeJumpVelocity = 500;
    }

    public static void setSpeed(int speed) {
        Difficulty.speed = speed;
    }

    public static void setPipeSpawnRate(long pipeSpawnRate) {
        Difficulty.pipeSpawnRate = pipeSpawnRate;
    }

    public static void setPipeDistance(int pipeDistance) {
        Difficulty.pipeDistance = pipeDistance;
    }

    public static void setGravity(int gravity) {
        Difficulty.gravity = gravity;
    }

    public static void setDogeJumpVelocity(int dogeJumpVelocity) {
        Difficulty.dogeJumpVelocity = dogeJumpVelocity;
    }
}
