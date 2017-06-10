package com.ana.marija.milos.traceyoursteps.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ana.marija.milos.traceyoursteps.model.Route;
import com.ana.marija.milos.traceyoursteps.model.Trip;

/**
 * Created by Milos on 9.6.2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static String CLASS_NAME;

    private static final String DATABASE_NAME = "anamarijamilos.db";
    private static final int DATABASE_VERSION = 15;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        CLASS_NAME = getClass().getName();
    }

    public void create(){
        open();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Route.create(sqLiteDatabase);
        Trip.create(sqLiteDatabase);
    }

    public SQLiteDatabase open() {
        Log.d(CLASS_NAME, "open");

        return getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void execAsyncSQL(SQLiteDatabase sqLiteDatabase, String createTable) {
        Log.d(CLASS_NAME, "execAsyncSQL");

        //numAsyncCalls++;
        // new AsyncDatabase(database).execute(sql);
    }

    public void execAsyncSQL(String createTable) {

    }
}
