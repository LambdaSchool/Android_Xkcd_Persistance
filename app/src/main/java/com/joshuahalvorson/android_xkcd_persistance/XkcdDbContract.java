package com.joshuahalvorson.android_xkcd_persistance;

import android.provider.BaseColumns;

public class XkcdDbContract {
    public static class ComicEntry implements BaseColumns {
        private static final String TABLE_NAME = "comics";

        private static final String COLUMN_NAME_LAST_READ = "last_read";
        private static final String COLUMN_NAME_FAVORITE = "favorite";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_LAST_READ + " TEXT, " +
                COLUMN_NAME_FAVORITE + " INTEGER);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }
}
