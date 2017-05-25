package com.ana.marija.milos.traceyoursteps;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    public static String CLASS_NAME;
    public static final long UPDATE_EVERY = 200;

    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected long startedAt;
    protected long lastStopped;
    protected long lastSeconds;

    protected Handler handler;
    protected UpdateTimer updateTimer;


    protected boolean timerRunning;
    protected Vibrator vibrate;


    public TimerActivity() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(CLASS_NAME, "OnCreate()");
        /*
        strict mode, sta god
        */

        // Make sure we do nothing naughty!
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyLog().penaltyDeath().build());
        }
        setContentView(R.layout.activity_timer);


        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);

        timerRunning = false;
        startedAt = System.currentTimeMillis();
        lastStopped = 0;

    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d(CLASS_NAME, "onStart");
        if(timerRunning){
            handler = new Handler();
            updateTimer = new UpdateTimer(this);
            handler.postDelayed(updateTimer, UPDATE_EVERY);
        }

        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(vibrate==null){
            Log.w(CLASS_NAME, "No vibration service exists");
        }
    }

    @Override
    protected void onPause() {
        Log.d(CLASS_NAME, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(CLASS_NAME, "onResume");
        super.onResume();
        enabledButtons();
        setTimeDisplay();
    }

    @Override
    protected void onStop() {
        Log.d(CLASS_NAME, "onStop");
        super.onStop();

        if(timerRunning){
            handler.removeCallbacks(updateTimer);
            updateTimer=null;
            handler = null;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(CLASS_NAME, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d(CLASS_NAME, "onRestart");
        super.onRestart();
    }

    public void clickedStart(View view) {
        Log.d(CLASS_NAME, "Kliknuto je dugme start");

        timerRunning = true;
        startedAt = System.currentTimeMillis();

        enabledButtons();

        handler = new Handler();
        //updateTimer = new UpdateTimer(this);
        updateTimer = new UpdateTimer(this);
        handler.postDelayed(updateTimer, UPDATE_EVERY);
    }

    public void clickedStop(View view) {
        Log.d(CLASS_NAME, "Kliknuto je dugme stop");
        timerRunning = false;

        lastStopped = System.currentTimeMillis();
        setTimeDisplay();

        enabledButtons();
        handler.removeCallbacks(updateTimer);
        handler = null;

    }

    public void clickedSettings(View view){
        Log.d(CLASS_NAME, "kliknuto na settings");

        Log.d(CLASS_NAME, "clickedSettings");

        Intent settings = new Intent(getApplicationContext(),
                SettingsActivity.class);

        startActivity(settings);

    }

    protected void enabledButtons() {
        Log.d(CLASS_NAME, "Set buttons enabled, disabled");
        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    protected void setTimeDisplay() {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME, "setTimeDisplay");

        if (timerRunning) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }

        diff = timeNow - startedAt;

        // no negative time
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

        Log.i(CLASS_NAME, "Time is " + display);

        counter.setText(display);
    }

    protected void vibrateCheck() {
        long timeNow = System.currentTimeMillis();
        long diff = timeNow - startedAt;
        long seconds = diff / 1000;
        long minutes = seconds / 60;

        Log.d(CLASS_NAME, "vibrateCheck");

        seconds = seconds % 60;
        minutes = minutes % 60;

        // NOTE done this way to avoid Array/ArrayList issues
        // NOTE hasVibrator() only on API 11+
        // NOTE very easy to get manifest wrong!
        // NOTE seconds != lastSeconds so it dosn't vibrate
        // multiple times a second
        if (vibrate != null && seconds == 0 && seconds != lastSeconds) {
            long[] once = { 0, 100 };
            long[] twice = { 0, 100, 400, 100 };
            long[] thrice = { 0, 100, 400, 100, 400, 100 };

            // every hour
            if (minutes == 0) {
                Log.i(CLASS_NAME, "Vibrate 3 times");
                vibrate.vibrate(thrice, -1);
            }
            // every 15 minutes
            else if (minutes % 15 == 0) {
                Log.i(CLASS_NAME, "Vibrate 2 time");
                vibrate.vibrate(twice, -1);
            }
            // every 5 minutes
            else if (minutes % 5 == 0) {
                Log.i(CLASS_NAME, "Vibrate once");
                vibrate.vibrate(once, -1);
            }
        }

        lastSeconds = seconds;
    }
}
