package com.ana.marija.milos.traceyoursteps.aktivnosti;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.ana.marija.milos.traceyoursteps.R;
import com.ana.marija.milos.traceyoursteps.helpers.Toaster;
import com.ana.marija.milos.traceyoursteps.model.Settings;
import com.ana.marija.milos.traceyoursteps.TraceYourSteps;

public class SettingsActivity extends Activity {
    
    private static String CLASS_NAME;
    Settings settings;

    public SettingsActivity() {
        CLASS_NAME = getClass().getName();
        settings = new Settings();
    }

    private CheckBox vibrate;

    //kod u ovoj metodi se pokrece samo kad je apl pokrenuta na Honeycomb ili kasnijoj verziji
 //   @TargetApi(Build.VERSION_CODES.HONEYCOMB)
   // private void setupActionBar() {
  //      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
  //          ActionBar actionBar = getActionBar();
   //         actionBar.setDisplayHomeAsUpEnabled(true);
  //      }
  //  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        vibrate = (CheckBox) findViewById(R.id.vibrate_checkbox);

        Settings settings = ((TraceYourSteps) getApplication()).getSettings();
        vibrate.setChecked(settings.isVibrateOn(this));
     //   setupActionBar();

    }



    @Override
    protected void onStop() {
        super.onStop();
        Settings settings = ((TraceYourSteps) getApplication()).getSettings();
        settings.setVibrate(this, vibrate.isChecked());
    }

    /*
         * Poziva se kada je CBox za vibraciju cekiran ili kada nije cekiran.
         */
    public void vibrateChanged(View view) {
        Toaster toast = new Toaster(this);

        if (vibrate.isChecked()) {
            toast.make( R.string.vibrate_on);
        } else {
            toast.make(R.string.vibrate_off);
        }
    }
//poziva se pritiskom na dugme Nazad
    public void goBack(View view){
        finish();
    }
//kreiranje namere za pokretanje Timer aktivnosti
    private void gotoHome() {
        Log.d(CLASS_NAME, "gotoHome");

        Intent timer = new Intent(this, TimerActivity.class);
        timer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(timer);
    }
    
    //detektovanje dodira na ikoni aplikacije u liniji akcije
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                gotoHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
