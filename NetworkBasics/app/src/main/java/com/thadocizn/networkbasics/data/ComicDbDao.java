package com.thadocizn.networkbasics.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thadocizn.networkbasics.Constants;
import com.thadocizn.networkbasics.Xkcd.XkcdComic;

public class ComicDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {

        if (db == null) {
            XkcdDbHelper helper = new XkcdDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static void createComic(XkcdComic comic) {
        ContentValues contentValues = new ContentValues();

        if (db != null) {
            contentValues.put(Constants.COLUMN_NAME_TIMESTAMP, comic.getXkcdDbInfo().getTimestamp());
            contentValues.put(Constants.COLUMN_NAME_BOOL, comic.getXkcdDbInfo().getBool());

            long result = db.insert(Constants.TABLE_NAME, null, contentValues);
        }
    }

    @SuppressLint("DefaultLocale")
    public static XkcdDbInfo readComic(int id){

        XkcdDbInfo info = null;
        int index;
        Cursor cursor = null;
        if (db != null){

            cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = '%d'",
                    Constants.TABLE_NAME,
                    XkcdDbContract.ComicEntry._ID,
                    id), null);


            if (cursor.moveToNext()){

                index = cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_BOOL);
                int boolLastRead = cursor.getInt(index);
                index = cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_TIMESTAMP);
                int timestamp = cursor.getInt(index);

                info = new XkcdDbInfo(boolLastRead, timestamp);
            }else {
                info = null;
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return info;
    }

    public static void updateComic(XkcdComic comicInfo){

        String whereClause = String.format("%s = %s", Constants.COLUMN_NAME_TIMESTAMP);
        String query  = String.format("SELECT * FROM %s WHERE %s",
                Constants.TABLE_NAME, whereClause);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() == 1){
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.COLUMN_NAME_TIMESTAMP, comicInfo.getXkcdDbInfo().getTimestamp());
            contentValues.put(Constants.COLUMN_NAME_BOOL, comicInfo.getXkcdDbInfo().getBool());

            int result = db.update(Constants.TABLE_NAME, contentValues, whereClause, null);
        }

    }

    public static void deleteComic(int id) {
        if (db != null) {
            @SuppressLint("DefaultLocale") String whereClause = String.format("%s = %d",
                    XkcdDbContract.ComicEntry._ID, id);
            int rows = db.delete(Constants.TABLE_NAME, whereClause, null);
        }
    }
}
