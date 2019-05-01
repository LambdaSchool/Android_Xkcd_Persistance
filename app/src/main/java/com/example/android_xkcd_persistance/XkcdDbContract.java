package com.example.android_xkcd_persistance;

import android.provider.BaseColumns;

public class XkcdDbContract {
    public static class ComicEntry implements BaseColumns{
        public static final String TABLE_NAME                   ="comics";

        public static final String COLUMN_NAME_TITLE            ="timestamp";
        public static final String COLUMN_NAME_BOOLEAN           ="content";
        public static final String COLUMN_NAME_TIMESTAMP        ="timestamp";

        public static final String SQL_CREATE_TABLE                 ="CREATE TABLE " +
                TABLE_NAME + " (" + _ID + " TEXT PRIMARY KEY, " + COLUMN_NAME_BOOLEAN +
                " TEXT, " + COLUMN_NAME_TITLE + " TEXT, " + COLUMN_NAME_TIMESTAMP + " INTEGER);";


        public static final String SQL_DELETE_TABLE             = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }
}
