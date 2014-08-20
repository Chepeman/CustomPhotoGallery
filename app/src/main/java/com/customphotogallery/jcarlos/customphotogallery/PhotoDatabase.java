package com.customphotogallery.jcarlos.customphotogallery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JCarlos on 8/20/14.
 */
public class PhotoDatabase {

    DBhelper dbHelper;
    private SQLiteDatabase db;
    public PhotoDatabase(Context context){
        dbHelper = new DBhelper(context);
    }

    public void insertPicture(String name, double rating, String section, boolean favorite){
        Object[] data  = {name, rating, section, favorite};
        executeSQL("INSERT INTO " + DBhelper.TABLE_PICTURE + "(" + DBhelper.COLUMN_NAME + ","
                + DBhelper.COLUMN_RATING + "," + DBhelper.COLUMN_SECTION_NAME + ","
                + DBhelper.COLUMN_FAV + ") VALUES(?,?,?,?)",data);
    }

    public long executeSQL(String sql, Object[] bindArgs)
    {
        long iRet = 0;
        // Opens the database object in "write" mode.
        db = dbHelper.getWritableDatabase();
        db.execSQL(sql, bindArgs);
        CloseDB();
        return(iRet);
    }

    public Cursor querySQL(String sql, String[] selectionArgs)
    {
        Cursor oRet = null;
        // Opens the database object in "write" mode.
        db = dbHelper.getReadableDatabase();
        oRet = db.rawQuery(sql, selectionArgs);
        return(oRet);
    }

    public void CloseDB()
    {
        if(db.isOpen())
        {
            db.close();
        }
    }

    public boolean isOpenDB()
    {
        return(db.isOpen());
    }


    static class DBhelper extends SQLiteOpenHelper{
        private static final String TAG = "PhotoDB";
        private static final String DATABASE_NAME = "pictures.db";
        private static final int  DATABASE_VERSION = 1;

        //Database fields
        private static final String TABLE_PICTURE  = "pictures";
        private static final String ID_PIC = "id";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_RATING = "rating";
        private static final String COLUMN_FAV = "fav";
        private static final String COLUMN_SECTION_NAME = "name";

        DBhelper (Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, "DBHelper.onCreate...");
            db.execSQL("CREATE TABLE " + DBhelper.TABLE_PICTURE + " ("
                    + DBhelper.ID_PIC + " INTEGER PRIMARY KEY,"
                    + DBhelper.COLUMN_NAME + " TEXT,"
                    + DBhelper.COLUMN_RATING + " NUMERIC,"
                    + DBhelper.COLUMN_SECTION_NAME + " TEXT,"
                    + DBhelper.COLUMN_FAV + " INTEGER,"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Recreates the database with a new version
            db.execSQL("DROP TABLE IF EXISTS " + DBhelper.TABLE_PICTURE);
            onCreate(db);
        }
    }
}
