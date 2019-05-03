package com.vivekvishwanath.android_networkbasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class XkcdDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {
        XkcdDbHelper dbHelper = new XkcdDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static void createComic(XkcdComic comic) {
        if (db != null) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(XkcdDbContract.ComicEntry._ID, comic.getNum());
            contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP,
                    comic.getDbInfo().getTimestamp());
            contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE,
                    comic.getDbInfo().isFavorite() ? 1 : 0);

            db.insert(XkcdDbContract.ComicEntry.TABLE_NAME, null, contentValues);
        }
    }

    public static XkcdDbInfo readComic (int id) {
        String entryQuery = String.format("SELECT * FROM %s WHERE %s = %s",
                XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry._ID ,id);
        XkcdDbInfo dbInfo;
        int index;
        if (db != null) {
            Cursor cursor = db.rawQuery(entryQuery, null);
            if (cursor.moveToNext() && cursor.getCount() == 1) {
                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry._ID);
                int comicId = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
                int timestamp = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
                int favorite = cursor.getInt(index);

                dbInfo = new XkcdDbInfo(timestamp, favorite == 1);
                cursor.close();
            } else {
                dbInfo = null;
            }
            return dbInfo;
        } else {
            return null;
        }
    }

    public static void updateComic(XkcdComic comic) {
        if (db != null) {
            String whereClause = String.format("%s = %s", XkcdDbContract.ComicEntry._ID,
                    comic.getNum());
            String query = String.format("SELECT * FROM %s WHERE %s",
                    XkcdDbContract.ComicEntry.TABLE_NAME, whereClause);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP,
                        comic.getDbInfo().getTimestamp());
                contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE,
                        comic.getDbInfo().isFavorite() ? 1 : 0);

                db.update(XkcdDbContract.ComicEntry.TABLE_NAME, contentValues,
                        whereClause, null);
            }
            cursor.close();
        }
    }

    public static void deleteComic (int id) {
        if (db != null) {
            String whereClause = String.format("%s = %s", XkcdDbContract.ComicEntry._ID,
                    id);
            String query = String.format("SELECT * FROM %s WHERE %s",
                    XkcdDbContract.ComicEntry.TABLE_NAME, whereClause);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 1) {
                db.delete(XkcdDbContract.ComicEntry.TABLE_NAME, whereClause, null);
            }
            cursor.close();
        }
    }

    public static ArrayList<Integer> readFavorites() {
        ArrayList<Integer> comicIds = new ArrayList<>();
        if (db != null) {
            String query = String.format("SELECT * FROM %s WHERE %s = %s",
                    XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, 1);
            Cursor cursor = db.rawQuery(query, null);
            int index;
            while (cursor.moveToNext()) {
                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry._ID);
                comicIds.add(cursor.getInt(index));
            }
            cursor.close();
        }
        return comicIds;
    }


}
