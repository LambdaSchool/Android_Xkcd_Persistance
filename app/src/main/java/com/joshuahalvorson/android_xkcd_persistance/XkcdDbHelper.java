package com.joshuahalvorson.android_xkcd_persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class XkcdDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Xkcdcomics.db";

     public XkcdDbHelper(Context context){
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(XkcdDbContract.ComicEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(XkcdDbContract.ComicEntry.SQL_DELETE_TABLE);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }
}
