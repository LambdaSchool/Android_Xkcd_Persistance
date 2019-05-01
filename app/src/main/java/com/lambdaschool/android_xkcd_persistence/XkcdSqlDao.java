package com.lambdaschool.android_xkcd_persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class XkcdSqlDao {
    private static SQLiteDatabase sqLiteDatabase;

    private static void initializeInstance(Context context) {
        if (sqLiteDatabase == null) {
            XkcdDbHelper xkcdDbHelper = new XkcdDbHelper(context);
            sqLiteDatabase = xkcdDbHelper.getWritableDatabase();
        }
    }
}
