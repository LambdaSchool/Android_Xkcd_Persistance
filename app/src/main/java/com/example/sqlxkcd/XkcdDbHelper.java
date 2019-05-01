package com.example.sqlxkcd;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class XkcdDbHelper extends SQLiteOpenHelper {
	
	private static final int    DATABASE_VERSION = 2;
	private static final String DATABASE_NAME    = "XkcdComic.db";
	
	public XkcdDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public XkcdDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		this(context);
	}
	
	public XkcdDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		this(context);
	}
	
	public XkcdDbHelper(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
		this(context);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(XkcdDbContract.ComicEntry.SQL_CREATE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(XkcdDbContract.ComicEntry.SQL_DELETE_TABLE);
		onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
}