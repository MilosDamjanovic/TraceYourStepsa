package com.ana.marija.milos.traceyoursteps.model;

import android.database.sqlite.SQLiteDatabase;

import com.ana.marija.milos.traceyoursteps.helpers.SQLiteHelper;

/**
 * Created by Milos on 9.6.2017.
 */

public class Route {
    private long _id;
    //posle napraviti settere i gettere!
    public String name;
    public String notes;
    public Trip trips[];

    public static void create(SQLiteDatabase sqLiteDatabase) {
        //treba saznati za this
        String createTable = "create table if not exists routes ("
                + "_id integer primary key autoincrement, "
                + "name text not null, " + "notes text not null );";


        sqLiteDatabase.execSQL(createTable);

    }
}
