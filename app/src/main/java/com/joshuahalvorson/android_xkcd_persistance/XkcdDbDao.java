package com.joshuahalvorson.android_xkcd_persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class XkcdDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context){
        if(db == null){
            XkcdDbHelper helper = new XkcdDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static void createComic(XkcdComic comic){
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_LAST_READ, 1);
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, 1);
            db.insertOrThrow(XkcdDbContract.ComicEntry.TABLE_NAME, null, values);
        }

    }

    public static XkcdDbInfo readComic(int id){
        String getComic = String.format("SELECT * FROM %s WHERE %s=%d;",
                XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry._ID, id);
        Cursor cursor = db.rawQuery(getComic, null);
        int index;
        XkcdDbInfo xkcdDbInfo;
        if(cursor.moveToNext()){
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_LAST_READ);
            long timestamp = cursor.getLong(index);
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
            int isFavorite = cursor.getInt(index);
            xkcdDbInfo = new XkcdDbInfo(timestamp, isFavorite);
        }else{
            xkcdDbInfo = null;
        }
        cursor.close();
        return xkcdDbInfo;
    }

    public static void updateComic(XkcdDbInfo xkcdDbInfo){
        if (db != null) {
            String whereClause = String.format("%s = %s",
                    XkcdDbContract.ComicEntry.COLUMN_NAME_LAST_READ, xkcdDbInfo.getTimestamp());
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s",
                    XkcdDbContract.ComicEntry.TABLE_NAME,
                    whereClause),
                    null);
            if(cursor.getCount() == 1){
                ContentValues contentValues = new ContentValues();
                contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_LAST_READ,
                        xkcdDbInfo.getTimestamp());
                contentValues.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE,
                        xkcdDbInfo.getFavorite());
                final int affectedRows = db.update(XkcdDbContract.ComicEntry.TABLE_NAME,
                        contentValues, whereClause, null);
            }
        }
    }

    public static void deleteComic(int id) {
        if (db != null) {
            String whereClause = String.format("%s = %d",
                    XkcdDbContract.ComicEntry._ID, id);
            int affectedRows = db.delete(XkcdDbContract.ComicEntry.TABLE_NAME, whereClause, null);
        }
    }
}
