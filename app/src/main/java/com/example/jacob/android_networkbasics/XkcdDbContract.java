package com.example.jacob.android_networkbasics;

import android.provider.BaseColumns;

class XkcdDbContract {
    static class ComicEntry implements BaseColumns {
        static final String TABLE_NAME = "comics";
        static final String COLUMN_NAME_TIMESTAMP = "time_stamp";
        static final String COLUMN_NAME_FAVORITE = "favorite";

        static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                _ID + " INTEGER, " +
                COLUMN_NAME_TIMESTAMP + " INTEGER, " +
                COLUMN_NAME_FAVORITE + " INTEGER);";

        static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }
}
