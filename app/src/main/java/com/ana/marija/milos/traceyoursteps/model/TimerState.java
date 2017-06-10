package com.ana.marija.milos.traceyoursteps.model;

import android.util.Log;

/**
 * Created by HP on 8.6.2017..
 */

public class TimerState {

    public static String CLASS_NAME;

    private long startedAt;
    private long lastStopped;
    private boolean timerRunning;
    private long lastTime;

    public TimerState(){
        CLASS_NAME= getClass().getName();
    }

    public void reset(){

        timerRunning = false;
        startedAt = System.currentTimeMillis();
        //  lastStopped = 0;

    }

    public void start(){

        timerRunning = true;
        startedAt = System.currentTimeMillis();

    }

    public void stop(){

        timerRunning = false;

        lastStopped = System.currentTimeMillis();
    }

    public boolean isRunning(){

        return timerRunning;
    }

    public String display(){
        String display;

        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME, "setTimeDisplay");
        diff= elapsedTime();



        // nema negativnog vremena!
        if (diff < 0) {
            diff = 0;
        }

        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        display = String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);

        Log.i(CLASS_NAME, "Vreme je: " + display);

        return display;
    }

    public long elapsedTime(){
        long timeNow;
        if (timerRunning) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }

        lastTime = timeNow - startedAt;

        return lastTime;
    }


    public long seconds() {
        return (lastTime / 1000) % 60;
    }


    public long minutes() {
        return (lastTime / 1000 / 60) % 60;
    }


    public long hours() {
        return (lastTime / 1000 / 60 / 60);
    }


}
