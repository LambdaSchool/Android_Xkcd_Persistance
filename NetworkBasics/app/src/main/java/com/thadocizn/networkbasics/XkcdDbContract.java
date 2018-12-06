package com.thadocizn.networkbasics;

import android.provider.BaseColumns;

import org.w3c.dom.Text;

public class XkcdDbContract {
    public static class ComicEntry implements BaseColumns{
        public static final String TABLE_NAME = "comics";

        public static final String COLUMN_NAME_TIMESTAMP = "comic_timestamp";
        public static final String COLUMN_NAME_BOOL = "comic_bool";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_TIMESTAMP + " INTEGER, " +
                COLUMN_NAME_BOOL + " INTEGER);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " +
                TABLE_NAME + ";";
    }

}
