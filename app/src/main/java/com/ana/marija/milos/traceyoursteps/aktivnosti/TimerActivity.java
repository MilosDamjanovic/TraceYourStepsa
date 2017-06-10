package com.ana.marija.milos.traceyoursteps.aktivnosti;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ana.marija.milos.traceyoursteps.BuildConfig;
import com.ana.marija.milos.traceyoursteps.R;
import com.ana.marija.milos.traceyoursteps.helpers.Notify;
import com.ana.marija.milos.traceyoursteps.model.Settings;
import com.ana.marija.milos.traceyoursteps.TraceYourSteps;
import com.ana.marija.milos.traceyoursteps.model.TimerState;

public class TimerActivity extends Activity {

    public static String CLASS_NAME;
    public static final long UPDATE_EVERY = 200;

    protected TextView counter;
    protected Button start;
    protected Button stop;
    //  protected long startedAt;
    // protected long lastStopped;
    protected long lastSeconds;

    protected Handler handler;
    protected UpdateTimer updateTimer;
    private Notify notify;


    // protected boolean timerRunning;
    protected Vibrator vibrate;
    private TimerState timer;

    public TimerActivity() {

        CLASS_NAME = getClass().getName();
        timer = new TimerState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(CLASS_NAME, "OnCreate()");
        /*
        strict mode, sta god
        */
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
        //vibrator
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (vibrate == null) {
            Log.w(CLASS_NAME, "No vibration service exists.");
        }
        notify = new Notify(this);

    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d(CLASS_NAME, "onStart");
        if (timer.isRunning()) {
            handler = new Handler();
            updateTimer = new UpdateTimer(this);
            handler.postDelayed(updateTimer, UPDATE_EVERY);
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
        counter.setText(timer.display());
    }

    @Override
    protected void onStop() {
        Log.d(CLASS_NAME, "onStop");
        super.onStop();

        // Settings settings = ((TraceYourSteps) getApplication()).getSettings();

        if (timer.isRunning()) {
            handler.removeCallbacks(updateTimer);
            updateTimer = null;
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

    class UpdateTimer implements Runnable {

        Activity activity;

        public UpdateTimer(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            // Log.d(CLASS_NAME, "run");
            Settings settings = ((TraceYourSteps) getApplication()).getSettings();

            counter.setText(timer.display());
            notifyCheck();

            if (timer.isRunning()) {
                if (settings.isVibrateOn(activity)) {
                    vibrateCheck();
                }
                // notifyCheck();
            }

            if (handler != null) {
                handler.postDelayed(this, UPDATE_EVERY);
            }

            // stayAwakeOrNot();
        }

        protected void notifyCheck() {
            long seconds;
            long minutes;
            long hours;

            Log.d(CLASS_NAME, "notifyCheck");

            timer.elapsedTime();
            seconds = timer.seconds();
            minutes = timer.minutes();
            hours = timer.hours();

            if (minutes % 5 == 0 && seconds == 0 && seconds != lastSeconds) {
                String title = getResources().getString(R.string.time_title);
                String message = getResources().getString(
                        R.string.time_running_message);

                if (hours == 0 && minutes == 0) {
                    message = getResources().getString(
                            R.string.time_start_message);
                }

                message = String.format(message, hours, minutes);

                notify.notify(title, message);
            }

            lastSeconds = seconds;
        }

        protected void vibrateCheck() {
            long timeNow = System.currentTimeMillis();
            long diff = timer.elapsedTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;

            Log.d(CLASS_NAME, "vibrateCheck");

            seconds = seconds % 60;
            minutes = minutes % 60;

            if (vibrate != null && seconds == 0 && seconds != lastSeconds) {
                long[] once = {0, 100};
                long[] twice = {0, 100, 400, 100};
                long[] thrice = {0, 100, 400, 100, 400, 100};

                //svaki sat
                if (minutes == 0) {
                    Log.i(CLASS_NAME, "Vibrate 3 times");
                    vibrate.vibrate(thrice, -1);
                }
                // svakih 15 min
                else if (minutes % 15 == 0) {
                    Log.i(CLASS_NAME, "Vibrate 2 time");
                    vibrate.vibrate(twice, -1);
                }
                // svakih 5 minuta
                else if (minutes % 5 == 0) {
                    Log.i(CLASS_NAME, "Vibrate once");
                    vibrate.vibrate(once, -1);
                }
            }

            lastSeconds = seconds;
        }
    }


    public void clickedStart(View view) {
        Log.d(CLASS_NAME, "Kliknuto je dugme start");
        //   counter.setText(timer.display());
        timer.start();

        enabledButtons();

        handler = new Handler();
        updateTimer = new UpdateTimer(this);
        handler.postDelayed(updateTimer, UPDATE_EVERY);
    }

    public void clickedStop(View view) {
        Log.d(CLASS_NAME, "Kliknuto je dugme stop");
        timer.stop();

        counter.setText(timer.display());

        enabledButtons();
        handler.removeCallbacks(updateTimer);
        updateTimer = null;
        handler = null;

    }

    public void clickedSettings(View view) {
        Log.d(CLASS_NAME, "kliknuto na settings");

        Log.d(CLASS_NAME, "clickedSettings");

        Intent settings = new Intent(getApplicationContext(),
                SettingsActivity.class);

        startActivity(settings);

    }

    protected void enabledButtons() {
        Log.d(CLASS_NAME, "Set buttons enabled, disabled");
        start.setEnabled(!timer.isRunning());
        stop.setEnabled(timer.isRunning());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(CLASS_NAME, "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.activity_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                clickedSettings(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
