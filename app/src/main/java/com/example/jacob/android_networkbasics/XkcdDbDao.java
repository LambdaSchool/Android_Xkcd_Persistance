package com.example.jacob.android_networkbasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class XkcdDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {
        if (db == null) {
            XkcdDbHelper helper = new XkcdDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public void createComic(XkcdComic xkcdComic) {
        ContentValues values = new ContentValues();
        XkcdDbInfo info = xkcdComic.getXkcdDbInfo();
        values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID, xkcdComic.getNum());
        values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, info.getTimestamp());
        values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, info.getFavorite());
        //TODO Where is the timestamp supposed to be set using System.currentTimeMillis()
    }
}
