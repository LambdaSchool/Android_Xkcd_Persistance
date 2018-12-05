package com.example.jacob.android_networkbasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class XkcdDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {
        if (db == null) {
            XkcdDbHelper helper = new XkcdDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public void createComic(XkcdComic xkcdComic) {
        if (db != null) {
            ContentValues values = new ContentValues();
            XkcdDbInfo info = xkcdComic.getXkcdDbInfo();
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID, xkcdComic.getNum());
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, info.getTimestamp());
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, info.getFavorite());
            //TODO Where is the timestamp supposed to be set using System.currentTimeMillis()

            long resultId = db.insert(XkcdDbContract.ComicEntry.TABLE_NAME, null, values);
        }
    }

    public XkcdDbInfo readComic(int id) {
//        SELECT * FROM comics WHERE comic_id=200;
        if (db != null) {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = '%s'",
                    XkcdDbContract.ComicEntry.TABLE_NAME,
                    XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID,
                    id),
                    null);
            XkcdDbInfo xkcdDbInfo;
            if (cursor.moveToNext() && (cursor.getCount() == 1)) {
                xkcdDbInfo = getXkcdDbInfoFromCursor(cursor);
            } else {
                xkcdDbInfo = null;
            }
            cursor.close();
            return xkcdDbInfo;

        } else {
            return null;
        }
    }


    private static XkcdDbInfo getXkcdDbInfoFromCursor(Cursor cursor) {
        int  index;
        XkcdDbInfo xkcdDbInfo;
//        index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID);
//        int comicId = cursor.getInt(index);

        index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
        int timestamp = cursor.getInt(index);

        index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
        int favorite = cursor.getInt(index);

        xkcdDbInfo = new XkcdDbInfo(timestamp, favorite);
        return xkcdDbInfo;
    }
}
