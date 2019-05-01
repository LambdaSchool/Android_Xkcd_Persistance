package com.jakeesveld.android_xkcd_persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class XkcdDbDAO {
    private static SQLiteDatabase db;
    /*
    public XkcdDbDAO(Context context){
        XkcdDbHelper dbHelper = new XkcdDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }*/

    public static void initializeInstance(Context context) {
        if (db == null) {
            XkcdDbHelper helper = new XkcdDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static ArrayList<XkcdDbInfo> readAllComics() {
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + XkcdDbContract.ComicEntry.TABLE_NAME,
                    new String[]{});
            ArrayList<XkcdDbInfo> favorites = new ArrayList<>();
            int index;
            while (cursor.moveToNext()) {
                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
                int favorite = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
                long timestamp = cursor.getLong(index);

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry._ID);
                int id = cursor.getInt(index);

                cursor.close();
                favorites.add(new XkcdDbInfo(favorite, timestamp, id));
            }
            return favorites;
        } else {
            return new ArrayList<>();
        }
    }

    public static XkcdDbInfo readComic(int id) {
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " +
                            XkcdDbContract.ComicEntry.TABLE_NAME +
                            " WHERE " + XkcdDbContract.ComicEntry._ID + " = ?"
                    , new String[]{Integer.toString(id)});
            int index;
            cursor.moveToFirst();
                try {
                    index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
                    int favorite = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
                    long timestamp = cursor.getLong(index);

                    cursor.close();
                    return new XkcdDbInfo(favorite, timestamp, id);
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    return null;
            }

        } else {
            return null;
        }
    }

    public static void addComic(int id) {
        if (db != null) {
            XkcdDbInfo info = new XkcdDbInfo();
            info.setComicId(id);
            ContentValues values = new ContentValues();
            values.put(XkcdDbContract.ComicEntry._ID, info.getComicId());
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, info.getFavorite());
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, info.getTimestamp());

            long resultId = db.insert(XkcdDbContract.ComicEntry.TABLE_NAME, null, values);
            Log.i("Add Comic", "add Comic result id: " + resultId);
        }

/*        if(db != null){
            XkcdDbInfo info = new XkcdDbInfo();
            info.setComicId(id);
            Cursor cursor = db.rawQuery(String.format("INSERT INTO %s (%s, %s, %s) VALUES (%s, %s, %s);",
                    XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE,
                    XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, XkcdDbContract.ComicEntry._ID, info.getFavorite(), info.getTimestamp(), info.getComicId())
                    , null);
            cursor.close();
            }*/
    }

    public static void updateComic(XkcdDbInfo info) {

        if (db != null) {
            ContentValues updateValues = new ContentValues();
            updateValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, info.getTimestamp());
            updateValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, info.getFavorite());

            String whereClause = XkcdDbContract.ComicEntry._ID + "=?";
            String[] whereArgs = new String[]{Integer.toString(info.getComicId())};

            int update = db.update(XkcdDbContract.ComicEntry.TABLE_NAME, updateValues, whereClause, whereArgs);
            Log.i("Update Comic", "Update comic result id: " + update);
        }

/*
        if(db != null){
            Cursor cursor = db.rawQuery(String.format("UPDATE %s SET %s=%s AND %s=%s WHERE %s=%s;",
                    XkcdDbContract.ComicEntry.TABLE_NAME,
                    XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP,
                    info.getTimestamp(),
                    XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE,
                    info.getFavorite(),
                    XkcdDbContract.ComicEntry._ID,
                    info.getComicId())
            , null);

            cursor.close();
        }*/
    }

    public static void deleteComic(XkcdDbInfo info){
        if(db != null){
            String whereClause = XkcdDbContract.ComicEntry._ID + "=?";
            String[] whereArgs = new String[]{Integer.toString(info.getComicId())};
            int delete = db.delete(XkcdDbContract.ComicEntry.TABLE_NAME, whereClause, whereArgs);
            Log.i("Delete Comic", "Delete comic result id: " + delete);
        }

/*        if(db != null){
            Cursor cursor = db.rawQuery(String.format("DELETE FROM %s WHERE %s=%s",
                    XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID, info.getComicId())
            , null);
            cursor.close();
        }*/
    }
}
