package com.lambdaschool.android_xkcd_persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

public class XkcdSqlDao {
    private static SQLiteDatabase sqLiteDatabase;

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

        long newId = sqLiteDatabase.insert(XkcdDbContract.ComicEntry.TABLE_NAME, null, contentValues);
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

        int success = sqLiteDatabase.update(XkcdDbContract.ComicEntry.TABLE_NAME, contentValues, whereString, null);
        //}
        //cursor.close();
    }

    public static void deleteComic(int xkcdId) {
        int success = sqLiteDatabase.delete(XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry._ID + " = ?;", new String[]{String.valueOf(xkcdId)});
    }
}
