package com.lambdaschool.android_xkcd_persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class XkcdSqlDao {
    private static SQLiteDatabase sqLiteDatabase;
    private static final String TAG = "XkcdSqlDao";

    public static void initializeInstance(Context context) {
        if (sqLiteDatabase == null) {
            XkcdDbHelper xkcdDbHelper = new XkcdDbHelper(context);
            sqLiteDatabase = xkcdDbHelper.getWritableDatabase();
        }
    }

    public static void closeInstance() {
        sqLiteDatabase.close();
    }

    public static void createComic(XkcdComic xkcdComic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(XkcdDbContract.ComicEntry._ID, xkcdComic.getNum());
        contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, xkcdComic.getXkcdDbInfo().getTimestamp());
        contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, xkcdComic.getXkcdDbInfo().isFavorite());

        long rowInserted = sqLiteDatabase.insert(XkcdDbContract.ComicEntry.TABLE_NAME, null, contentValues);

        if (rowInserted == -1) {
            Log.e(TAG, "Problem CREATING a comic.");
        }
    }

    public static XkcdDbInfo readComic(int xkcdId) {
        String queryString = "SELECT * FROM " + XkcdDbContract.ComicEntry.TABLE_NAME + " WHERE " + XkcdDbContract.ComicEntry._ID + " = " + xkcdId + ";";

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        XkcdDbInfo xkcdDbInfo = new XkcdDbInfo();

        if (cursor.moveToNext() && cursor.getCount() > 0) {
            int index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
            xkcdDbInfo.setTimestamp(cursor.getLong(index));
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
            xkcdDbInfo.setFavorite(cursor.getInt(index));
        } else {
            Log.e(TAG, String.format("Cannot READ comic #%d.", xkcdId));
            xkcdDbInfo = null;
        }
        cursor.close();

        return xkcdDbInfo;
    }

    public static void updateComic(@NotNull XkcdComic xkcdComic) {
        String queryString = "UPDATE " + XkcdDbContract.ComicEntry.TABLE_NAME + " SET " + XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP + " = " + xkcdComic.getXkcdDbInfo().getTimestamp() + ", " + XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE + " = " + xkcdComic.getXkcdDbInfo().isFavorite() + " WHERE " + XkcdDbContract.ComicEntry._ID + " = " + xkcdComic.getNum() + ";";
        String whereString = XkcdDbContract.ComicEntry._ID + " = " + xkcdComic.getNum() + ";";

        //Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        //if (cursor.getCount() > 0) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(XkcdDbContract.ComicEntry._ID, Integer.parseInt(xkcdComic.getNum()));
        contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, xkcdComic.getXkcdDbInfo().getTimestamp());
        contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, xkcdComic.getXkcdDbInfo().isFavorite());

        int rowsAffected = sqLiteDatabase.update(XkcdDbContract.ComicEntry.TABLE_NAME, contentValues, whereString, null);

        if (rowsAffected < 1) {
            Log.e(TAG, "Error UPDATING the comic.");
        }
        //}
        //cursor.close();
    }

    public static void deleteComic(int xkcdId) {
        int rowsAffected = sqLiteDatabase.delete(XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry._ID + " = ?;", new String[]{String.valueOf(xkcdId)});

        if (rowsAffected < 1) {
            Log.e(TAG, String.format("Comic #%d couldn't be DELETED.", xkcdId));
        }
    }

    public static ArrayList<XkcdDbInfo> readFavoriteComics() {
        String queryString = "SELECT * FROM " + XkcdDbContract.ComicEntry.TABLE_NAME + " WHERE " + XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE + " = 1;";
        ArrayList<XkcdDbInfo> xkcdDbInfoArrayList=new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        while (cursor.moveToNext() && cursor.getCount() > 0) {
            XkcdDbInfo xkcdDbInfo = new XkcdDbInfo();
            int index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
            xkcdDbInfo.setTimestamp(cursor.getLong(index));
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
            xkcdDbInfo.setFavorite(cursor.getInt(index));
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry._ID);
            xkcdDbInfo.setFavoriteId(cursor.getInt(index));

            xkcdDbInfoArrayList.add(xkcdDbInfo);
        }
/*        if (cursor.) {
            Log.e(TAG, "Cannot read any FAVORITES.");
        }*/
        cursor.close();

        return xkcdDbInfoArrayList;
    }
}
