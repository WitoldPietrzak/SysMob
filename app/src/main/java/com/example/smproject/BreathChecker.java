package com.example.smproject;

public class BreathChecker {
    long startTime;
    long stopTime;

    public void Start() {
        startTime = System.currentTimeMillis();
    }

    public void Stop() {
        stopTime = System.currentTimeMillis();
    }

    public boolean hasCovid() {
        long breathHeldTime = (stopTime - startTime) / 1000;
        return breathHeldTime < 10;
    }
}
