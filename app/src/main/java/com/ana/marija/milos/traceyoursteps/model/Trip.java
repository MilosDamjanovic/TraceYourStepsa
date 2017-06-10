package com.ana.marija.milos.traceyoursteps.model;

import android.database.sqlite.SQLiteDatabase;

import com.ana.marija.milos.traceyoursteps.helpers.SQLiteHelper;

/**
 * Created by Milos on 9.6.2017.
 */

public class Trip {
    private long _id;
    public long timeStarted;
    public long timeTaken;

    public static void create(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create table if not exists trips ("
                + "_id integer primary key autoincrement, "
                + "route_id integer references routes(_id), "
                + "timeStarted integer not null, "
                + "timeTaken integer not null);";


        sqLiteDatabase.execSQL(createTable);
    }

}
