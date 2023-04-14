package se.yrgo.utils;

public class Difficulty {
    public static int speed;
    public static long pipeSpawnRate;
    public static int pipeDistance;
    public static int dogeFallSpeed;
    public static int dogeJumpVelocity;

    public Difficulty() {
        speed = 100;
        pipeSpawnRate = 300000000000L / speed;
        pipeDistance = 140;
        dogeFallSpeed = -14;
        dogeJumpVelocity = 300;
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

    public static void setDogeFallSpeed(int dogeFallSpeed) {
        Difficulty.dogeFallSpeed = dogeFallSpeed;
    }

    public static void setDogeJumpVelocity(int dogeJumpVelocity) {
        Difficulty.dogeJumpVelocity = dogeJumpVelocity;
    }
}
