package com.ana.marija.milos.traceyoursteps;

import android.app.Activity;
import android.os.Handler;

import static com.ana.marija.milos.traceyoursteps.TimerActivity.UPDATE_EVERY;

/**
 * Created by Milos on 25.5.2017.
 */

public class UpdateTimer implements Runnable {

   // Activity activity;
    TimerActivity timerActivity;

    public UpdateTimer(TimerActivity timerActivity){
        //this.activity=activity;
        this.timerActivity = timerActivity;
    }

    protected Handler handler;
    protected UpdateTimer updateTimer;

    @Override
    public void run() {
        // Log.d(CLASS_NAME, "run");
        timerActivity.setTimeDisplay();
        if (handler != null) {
            handler.postDelayed(this, UPDATE_EVERY);
        }
        if(timerActivity.timerRunning){
            timerActivity.vibrateCheck();
        }
    }
}
