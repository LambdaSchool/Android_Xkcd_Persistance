package com.example.android_xkcd_persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotesDbDao {
    private static SQLiteDatabase db;

    private static void initializeInstance(Context context){
        if(db == null){
            NotesDbHelper helper = new NotesDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static Note readNote(String id){
        if(db != null){
            db.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s;",
                    XkcdDbContract.ComicEntry.TABLE_NAME, XkcdDbContract.ComicEntry._ID, id),null);
        }else{
            return null;
        }
    }

    public static ArrayList<Note> readAllNotes() {
        if (db != null) {
            db.rawQuery(String.format("SELECT * FROM %s;", XkcdDbContract.ComicEntry.TABLE_NAME), null);
        } else {
            return new ArrayList<>();
        }

    }
}
