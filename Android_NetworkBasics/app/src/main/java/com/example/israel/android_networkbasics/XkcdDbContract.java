package com.example.israel.android_networkbasics;

import android.provider.BaseColumns;

public class XkcdDbContract {

    private XkcdDbContract() {

    }

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + ComicEntry.TABLE_NAME + " (\n" +
            ComicEntry._ID + " INTEGER PRIMARY KEY,\n" +
            ComicEntry.COLUMN_NAME_TIMESTAMP + " BIGINT,\n" +
            ComicEntry.COLUMN_NAME_FAVORITE + " INTEGER)\n";
    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + ComicEntry.TABLE_NAME;

    public static class ComicEntry implements BaseColumns {

        public static final String TABLE_NAME = "Comics";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_FAVORITE = "favorite";


    }
}
