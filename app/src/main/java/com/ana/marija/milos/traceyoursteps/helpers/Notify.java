package com.ana.marija.milos.traceyoursteps.helpers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ana.marija.milos.traceyoursteps.R;

/**
 * Created by HP on 9.6.2017..
 */

public class Notify {

    private static String CLASS_NAME;

    private static final int MESSAGE_ID = 1;

    private final NotificationManager manager;
    private final Context context;

    public int smallIcon = R.mipmap.ic_launcher;

    public Notify(Activity activity) {
        CLASS_NAME = getClass().getName();

        manager = (NotificationManager) activity
                .getSystemService(Context.NOTIFICATION_SERVICE);
        context = activity;
    }

    private Notification create(String title, String message, long when) {
        Log.d(CLASS_NAME, "create()");

        //Mora da se ubaci ikonica, u suproenom se ne prikazuje obavestenje
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title).setContentText(message).setWhen(when)
                .setSmallIcon(smallIcon).build();

        return notification;
    }

    public void notify(String title, String message, long when) {
        Log.d(CLASS_NAME, "notify()");

        Notification notification = create(title, message, when);

        manager.notify(MESSAGE_ID, notification);
    }

    public void notify(String title, String message) {
        Log.d(CLASS_NAME, "notify()");

        Notification notification = create(title, message,
                System.currentTimeMillis());

        manager.notify(MESSAGE_ID, notification);
    }
}
