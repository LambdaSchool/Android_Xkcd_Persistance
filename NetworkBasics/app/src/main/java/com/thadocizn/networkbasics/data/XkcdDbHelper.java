package com.thadocizn.networkbasics.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thadocizn.networkbasics.Constants;

public class XkcdDbHelper extends SQLiteOpenHelper {

    public XkcdDbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
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
}
