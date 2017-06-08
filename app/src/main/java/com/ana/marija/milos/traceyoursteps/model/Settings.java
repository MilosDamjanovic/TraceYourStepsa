package com.ana.marija.milos.traceyoursteps.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;


public class Settings {
    private static String CLASS_NAME;

    private static String VIBRATE = "vibrate";
    private static String STAYAWAKE = "stayawake";

    protected boolean vibrateOn;
    protected boolean stayAwake;


    public Settings() {
        CLASS_NAME = getClass().getName();
    }

    public boolean isVibrateOn(Activity activity) {
        Log.d(CLASS_NAME, "isVibrateOn");


        SharedPreferences preferences = activity
                .getPreferences(Activity.MODE_PRIVATE);

        if (preferences.contains(VIBRATE)) {
            vibrateOn = preferences.getBoolean(VIBRATE, false);
        }

        Log.i(CLASS_NAME, "Vibrate is " + vibrateOn);

        return vibrateOn;
    }


    public void setVibrate(Activity activity, boolean vibrate) {
        Log.d(CLASS_NAME, "setVibrate");

        SharedPreferences preferences = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(VIBRATE, vibrate);
        editor.apply();

        vibrateOn = vibrate;
    }



}

