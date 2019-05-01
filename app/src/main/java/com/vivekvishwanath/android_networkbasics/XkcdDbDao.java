package com.vivekvishwanath.android_networkbasics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class XkcdDbDao {
    private SQLiteDatabase db;

    public void initializeInstance(Context context) {
        XkcdDbHelper dbHelper = new XkcdDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

}
