package com.ana.marija.milos.traceyoursteps;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;

/**
 * Created by Milos on 24.5.2017.
 */

public class Settings {
    private static String CLASS_NAME;

    private static String VIBRATE = "vibrate";
    private static String STAYAWAKE = "stayawake";

    protected boolean vibrateOn;
    protected boolean stayAwake;


    public Settings() {
        CLASS_NAME = getClass().getName();
    }

    /**
     * Return the saved vibrate setting
     *
     * @return true if vibrate is on, false if it is not
     */
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

    /**
     * Return the stay awake setting
     *
     * @return true if stay awake is on, false if it is not
     */
    public Boolean isCaffeinated(Activity activity) {
        Log.d(CLASS_NAME, "isCaffeinated");

        SharedPreferences preferences = activity
                .getPreferences(Activity.MODE_PRIVATE);

        if (preferences.contains(STAYAWAKE)) {
            stayAwake = preferences.getBoolean(STAYAWAKE, false);
        }

        Log.i(CLASS_NAME, "Stay awake is " + stayAwake);

        return stayAwake;
    }

    /**
     * Set the stay awake setting
     *
     * @param activity
     * activity to get preferences from
     * @param stayawake
     * true to stay awake on, false to work as normal.
     */
    public void setCaffeinated(Activity activity, boolean stayawake) {
        Log.d(CLASS_NAME, "setCaffeinated");

        Log.i(CLASS_NAME, "Setting stay awake to " + stayawake);

        stayAwake = stayawake;

        SharedPreferences preferences = activity
                .getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(STAYAWAKE, stayAwake);
        editor.apply(); // rather than commit()
    }

}