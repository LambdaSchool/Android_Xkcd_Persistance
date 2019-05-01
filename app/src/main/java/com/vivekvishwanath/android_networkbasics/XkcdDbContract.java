package com.vivekvishwanath.android_networkbasics;

import android.provider.BaseColumns;

public class XkcdDbContract {
    public static class ComicEntry implements BaseColumns {
        public static final String TABLE_NAME = "comics";
        public static final String COLUMN_NAME_TIMESTAMP = "time_stamp";
        public static final String COLUMN_NAME_FAVORITE = "favorite";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " ( "
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME_TIMESTAMP + " INTEGER, "
                + COLUMN_NAME_FAVORITE + " INTEGER);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS "
                + TABLE_NAME + ";"; 
    }
}
