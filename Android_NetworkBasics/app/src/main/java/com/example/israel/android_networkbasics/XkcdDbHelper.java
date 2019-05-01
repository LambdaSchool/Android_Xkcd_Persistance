package com.example.israel.android_networkbasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class XkcdDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Comic.db";
    private static SQLiteDatabase sqLiteDatabase;

    public XkcdDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(XkcdDbContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(XkcdDbContract.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public static void initializeInstance(Context context) {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = new XkcdDbHelper(context).getWritableDatabase();
        }
    }

    public static void createComic(XkcdComic xkcdComic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(XkcdDbContract.ComicEntry._ID, xkcdComic.getNum());
        contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP, xkcdComic.getXkcdDbInfo().getTimestamp());
        contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, xkcdComic.getXkcdDbInfo().getFavorite());
        sqLiteDatabase.insert(XkcdDbContract.ComicEntry.TABLE_NAME, null, contentValues);
    }

    @Nullable
    public static XkcdDbInfo readComic(int id) {
        String queryStr =
                "SELECT * FROM Comics\n" +
                "WHERE " + XkcdDbContract.ComicEntry._ID + "==" + id;
        Cursor cursor = sqLiteDatabase.rawQuery(queryStr, null);
        if (cursor.getCount() != 1) {
            cursor.close();
            return null;
        }

        if (cursor.moveToNext()) {
            XkcdDbInfo xkcdDbInfo = new XkcdDbInfo();
            int timestampIndex = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP);
            xkcdDbInfo.setTimestamp(cursor.getLong(timestampIndex));
            int favoriteIndex = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
            xkcdDbInfo.setFavorite(cursor.getInt(favoriteIndex));

            cursor.close();
            return xkcdDbInfo;
        }

        return null;
    }

    public static void updateComic(XkcdComic xkcdComic) {
        String whereClause = "WHERE " + XkcdDbContract.ComicEntry._ID + "==" + xkcdComic.getNum();

        String queryStr = "UPDATE " + XkcdDbContract.ComicEntry.TABLE_NAME + "\n" +
                "SET "+ XkcdDbContract.ComicEntry.COLUMN_NAME_TIMESTAMP + "=" + xkcdComic.getXkcdDbInfo().getTimestamp() + ",\n" +
                XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE + "=" + xkcdComic.getXkcdDbInfo().getFavorite() + "\n" +
                whereClause;

        sqLiteDatabase.execSQL(queryStr);

    }

    public static void deleteComic(int id) {
        String whereClause = "WHERE " + XkcdDbContract.ComicEntry._ID + "==" + id;

        String queryStr = "DELETE FROM " + XkcdDbContract.ComicEntry.TABLE_NAME + " " + whereClause;

        sqLiteDatabase.execSQL(queryStr);
    }

    public static ArrayList<Integer> readFavorites() {
        String queryStr = "SELECT " + XkcdDbContract.ComicEntry._ID + " FROM " + XkcdDbContract.ComicEntry.TABLE_NAME + "\n" +
                "WHERE " + XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE + "==1";

        Cursor cursor = sqLiteDatabase.rawQuery(queryStr, null);
        ArrayList<Integer> favoriteIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry._ID);
            favoriteIds.add(cursor.getInt(idIndex));
        }

        return favoriteIds;
    }
}
