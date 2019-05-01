package com.example.sqlxkcd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SqlXkcdDao {
	private static SQLiteDatabase db;
	
	public SqlXkcdDao(Context context) {
		XkcdDbHelper dbHelper = new XkcdDbHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public static void addComic(XkcdComic comic) {
		ContentValues values = getContentValues(comic);
		
		final long insert = db.insert(XkcdDbContract.ComicEntry.TABLE_NAME, null, values);
	}
	
	public void updateComic(XkcdComic comic) {
		int affectedRows = db.update(
				XkcdDbContract.ComicEntry.TABLE_NAME,
				getContentValues(comic),
				XkcdDbContract.ComicEntry._ID + "=?",
				new String[]{Integer.toString(comic.getId())});
	}
	
	public void deleteComic(XkcdComic comic) {
		int affectedRows = db.delete(XkcdDbContract.ComicEntry.TABLE_NAME,
				XkcdDbContract.ComicEntry._ID + "=?",
				new String[]{Integer.toString(comic.getId())});
	}
	
	private static ContentValues getContentValues(XkcdComic comic) {
		ContentValues values = new ContentValues();
		
		values.put(XkcdDbContract.ComicEntry._ID, comic.getId());
		values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIME_STAMP, comic.getTimeStamp());
		values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_IS_READ, comic.isRead());
		return values;
	}
	
	public List<XkcdComic> getAllComics() {
		final Cursor cursor = db.rawQuery("SELECT * FROM " + XkcdDbContract.ComicEntry.TABLE_NAME,
				new String[]{});
		
		List<XkcdComic> rows = new ArrayList<>();
		while (cursor.moveToNext()) {
			rows.add(getComic(cursor));
		}
		
		cursor.close();
		return rows;
	}
	
	private XkcdComic getComic(Cursor cursor) {
		int index;
		index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry._ID);
		int id = cursor.getInt(index);
		
		index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIME_STAMP);
		String timeStamp = cursor.getString(index);
		
		index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_IS_READ);
		int isRead = cursor.getInt(index);
		
		return new XkcdComic(id, timeStamp, isRead);
	}
}