package com.ana.marija.milos.traceyoursteps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static String className;

    public MainActivity(){
        className=getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView zdravo = (TextView) findViewById(R.id.hello);
        Log.d(className, "Settting text");

        zdravo.setText("Zdravo svete");
        zdravo.setText("Promena");
        zdravo.setText("Promena");

    }
}
