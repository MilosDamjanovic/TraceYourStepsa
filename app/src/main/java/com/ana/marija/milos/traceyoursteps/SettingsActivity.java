package com.ana.marija.milos.traceyoursteps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;

public class SettingsActivity extends Activity {

    private static String CLASS_NAME;
    Settings settings;

    public SettingsActivity() {
        CLASS_NAME = getClass().getName();
        settings = new Settings();
    }

    private CheckBox vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        vibrate = (CheckBox) findViewById(R.id.vibrate_checkbox);

        Settings settings = ((TraceYourSteps) getApplication()).getSettings();
        vibrate.setChecked(settings.isVibrateOn(this));

    }

    @Override
    protected void onStop() {
        super.onStop();
        Settings settings = ((TraceYourSteps) getApplication()).getSettings();
        settings.setVibrate(this, vibrate.isChecked());
    }
}
