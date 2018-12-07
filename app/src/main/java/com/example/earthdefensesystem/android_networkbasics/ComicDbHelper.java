package com.example.earthdefensesystem.android_networkbasics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ComicDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NotesDatabase.db";

    public NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ComicDBContract.ComicEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL(ComicDBContract.ComicEntry.SQL_DELETE_TABLE);
        this.onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }
}
