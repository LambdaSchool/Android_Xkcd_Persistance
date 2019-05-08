package com.vivekvishwanath.android_networkbasics;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class XkcdDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "XkcdComicDatabase.db";

    public XkcdDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public XkcdDbHelper(@Nullable Context context, @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        this(context);
    }

    public XkcdDbHelper(@Nullable Context context, @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory, int version,
                        @Nullable DatabaseErrorHandler errorHandler) {
        this(context);
    }

    public XkcdDbHelper(@Nullable Context context, @Nullable String name, int version,
                        @NonNull SQLiteDatabase.OpenParams openParams) {
        this(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(XkcdDbContract.ComicEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(XkcdDbContract.ComicEntry.SQL_DELETE_TABLE);
    }
}
