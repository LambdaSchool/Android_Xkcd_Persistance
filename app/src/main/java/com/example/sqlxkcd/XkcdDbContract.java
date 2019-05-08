package com.example.sqlxkcd;


import android.provider.BaseColumns;

public class XkcdDbContract {
	public static class ComicEntry implements BaseColumns {
		public static final String TABLE_NAME                  = "comics";
		public static final String COLUMN_NAME_TIME_STAMP = "time_stamp";
		public static final String COLUMN_NAME_IS_READ  = "is_read";

		
		public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
				+ " ( " +
				_ID + " INTEGER PRIMARY KEY," +
				COLUMN_NAME_TIME_STAMP + " INTEGER," +
				COLUMN_NAME_IS_READ + " INTEGER" +
				"  );";
		
		public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	}
}