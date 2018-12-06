package com.joshuahalvorson.android_xkcd_persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class XkcdDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context){
        if(db == null){
            XkcdDbHelper helper = new XkcdDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static ArrayList<String> readFavorites(){
        String getComic = String.format("SELECT * FROM %s WHERE %s=%d;",
                XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, 1);
        Cursor cursor = db.rawQuery(getComic, null);
        int index;
        ArrayList<String> favoritesList = new ArrayList<>();
        while(cursor.moveToNext()){
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
            int isFavorite = cursor.getInt(index);
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID);
            int comicId = cursor.getInt(index);
            if(isFavorite == 1){
                favoritesList.add(Integer.toString(comicId));
            }
        }
        cursor.close();
        return favoritesList;
    }

    public static void createComic(XkcdComic comic){
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_LAST_READ, comic.getXkcdDbInfo().getTimestamp());
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, comic.getXkcdDbInfo().getFavorite());
            values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID, comic.getXkcdDbInfo().getComicId());
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
            index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID);
            int comicId = cursor.getInt(index);
            xkcdDbInfo = new XkcdDbInfo(timestamp, isFavorite, comicId);
        }else{
            xkcdDbInfo = null;
        }
        cursor.close();
        return xkcdDbInfo;
    }

    public static void updateComic(XkcdComic xkcdComic){
        if (db != null) {
            if (db != null) {
                String whereClause = String.format("%s = '%s'",
                        XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID,
                        xkcdComic.getNum());

                final Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s",
                        XkcdDbContract.ComicEntry.TABLE_NAME,
                        whereClause),
                        null);

                if(cursor.getCount() == 1) {
                    ContentValues values = new ContentValues();
                    values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE, xkcdComic.getXkcdDbInfo().getFavorite());
                    values.put(XkcdDbContract.ComicEntry.COLUMN_NAME_LAST_READ, xkcdComic.getXkcdDbInfo().getTimestamp());

                    final int affectedRows = db.update(XkcdDbContract.ComicEntry.TABLE_NAME, values, whereClause, null);
                }
            }
        }
    }

    public static ArrayList<XkcdDbInfo> readAllComics() {
        if (db != null) {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s;",
                    XkcdDbContract.ComicEntry.TABLE_NAME),
                    null);

            ArrayList<XkcdDbInfo> comicsList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int  index;
                XkcdDbInfo xkcdDbInfo;
                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_FAVORITE);
                int fav = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_COMIC_ID);
                int cId = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(XkcdDbContract.ComicEntry.COLUMN_NAME_LAST_READ);
                Long time = cursor.getLong(index);

                xkcdDbInfo = new XkcdDbInfo(time, fav, cId);
                comicsList.add(xkcdDbInfo);
            }
            cursor.close();
            return comicsList;

        } else {
            return new ArrayList<>();
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
