package com.example.smproject;

public class BreathChecker {
    long startTime;
    long stopTime;

    void Start(){
        startTime=System.currentTimeMillis();
    }
    void Stop(){
        stopTime=System.currentTimeMillis();
    }
    boolean hasCovid()
    {
        long breathHeldTime=(stopTime-startTime)/1000;
        return breathHeldTime < 10;
    }
}
