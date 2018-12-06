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
            values.put(XkcdDbContract.ComicEntry._ID, xkcdComic.getNum());
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
                    XkcdDbContract.ComicEntry._ID,
                    id),
                    null);
            XkcdDbInfo xkcdDbInfo;
            int index;
            if (cursor.moveToNext() && (cursor.getCount() == 1)) {

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
                int timestamp = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
                int favorite = cursor.getInt(index);

                xkcdDbInfo = new XkcdDbInfo(timestamp, favorite);
            } else {
                xkcdDbInfo = null;
            }
            cursor.close();
            return xkcdDbInfo;

        } else {
            return null;
        }
    }

    public static void updateComic(XkcdDbInfo info) {
        if (db != null) {
//            String whereClause = String.format("%s = %s", XkcdDbContract.ComicEntry._ID, info.getId());


        }
    }
}
