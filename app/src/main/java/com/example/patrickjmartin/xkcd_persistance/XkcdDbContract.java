package com.example.patrickjmartin.xkcd_persistance;

import android.provider.BaseColumns;

public class XkcdDbContract {

    public static class ComicEntry implements BaseColumns {

        public static final String TABLE_NAME = "comics";
        private static final String COLUMN_NAME_TIMESTAMP = "time_stamp";
        //bool value: 1 for true and 0 for false
        private static final String COLUMN_NAME_FAVORITE ="favorite";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                                                      " ( " + _ID + " INTEGER, " +
                                                      COLUMN_NAME_TIMESTAMP + " INTEGER, " +
                                                      COLUMN_NAME_FAVORITE + " INTEGER);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    }
}
