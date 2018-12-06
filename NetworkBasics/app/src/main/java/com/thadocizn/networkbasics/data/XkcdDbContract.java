package com.thadocizn.networkbasics.data;

import android.provider.BaseColumns;

import com.thadocizn.networkbasics.Constants;

public class XkcdDbContract {
    public static class ComicEntry implements BaseColumns{

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +
                " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                Constants.COLUMN_NAME_TIMESTAMP + " INTEGER, " +
                Constants.COLUMN_NAME_BOOL + " INTEGER);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " +
                Constants.TABLE_NAME + ";";
    }

}
