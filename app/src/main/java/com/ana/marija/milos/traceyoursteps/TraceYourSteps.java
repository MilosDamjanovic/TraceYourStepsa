package com.ana.marija.milos.traceyoursteps;

import android.app.Application;

/**
 * Created by Milos on 24.5.2017.
 */

public class TraceYourSteps extends Application {
    protected  Settings settings;

    public Settings getSettings() {
        if(settings==null){
            settings = new Settings();
        }
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
