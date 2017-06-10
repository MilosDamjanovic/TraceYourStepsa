package com.ana.marija.milos.traceyoursteps;

import android.app.Application;
import android.util.Log;

import com.ana.marija.milos.traceyoursteps.helpers.SQLiteHelper;
import com.ana.marija.milos.traceyoursteps.model.Settings;

/**
 * Created by Milos on 24.5.2017.
 */

public class TraceYourSteps extends Application {
    protected Settings settings;
    //private SQLiteHelper helper;
    private static String CLASS_NAME;

    public TraceYourSteps() {
        CLASS_NAME = getClass().getName();
    }

    public Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void onCreate() {
        Log.d(CLASS_NAME, "onCreate");

       SQLiteHelper helper = new SQLiteHelper(getApplicationContext());
        super.onCreate();

        helper.create();


    }
}
