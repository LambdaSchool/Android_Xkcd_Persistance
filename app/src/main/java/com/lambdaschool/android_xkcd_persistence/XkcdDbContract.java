package com.lambdaschool.android_xkcd_persistence;

import android.provider.BaseColumns;

public class XkcdDbContract {
    public static class ComicEntry implements BaseColumns {
        public static final String TABLE_NAME = "comic";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_FAVORITE = "favorite";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER, " + COLUMN_NAME_TIMESTAMP + " INTEGER, " + COLUMN_NAME_FAVORITE + " INTEGER);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }
}
