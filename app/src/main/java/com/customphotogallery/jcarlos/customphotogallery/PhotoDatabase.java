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

    public String[][] getFavorites(){
        String pictures[][] = null;
        int counter = 0;
        Cursor cursor = querySQL("SELECT * FROM " + DBhelper.TABLE_PICTURE + " WHERE " + DBhelper.COLUMN_FAV + "= 1", null);
        if(cursor.getCount()>0){
            pictures = new String[cursor.getCount()][];
            while(cursor.moveToNext()){
                pictures[counter] = new String[5];
                pictures[counter][0] = cursor.getString(cursor.getColumnIndex(DBhelper.ID_PIC));
                pictures[counter][1] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_NAME));
                pictures[counter][2] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_RATING));
                pictures[counter][3] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_FAV));
                pictures[counter][4] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_SECTION_NAME));
                counter++;
            }
        }
        else{
            pictures = new String[0][];
        }
        cursor.close();
        CloseDB();
        return pictures;
    }

    public String[][] getSectionPictures(String section){
        String pictures[][] = null;
        String[] args = {section};
        int counter = 0;
        Cursor cursor = querySQL("SELECT * FROM " + DBhelper.TABLE_PICTURE + " WHERE " + DBhelper.COLUMN_SECTION_NAME + "= ?", args);
        if(cursor.getCount()>0){
            pictures = new String[cursor.getCount()][];
            while(cursor.moveToNext()){
                pictures[counter] = new String[5];
                pictures[counter][0] = cursor.getString(cursor.getColumnIndex(DBhelper.ID_PIC));
                pictures[counter][1] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_NAME));
                pictures[counter][2] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_RATING));
                pictures[counter][3] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_FAV));
                pictures[counter][4] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_SECTION_NAME));
                counter++;
            }
        }
        else{
            pictures = new String[0][];
        }
        cursor.close();
        CloseDB();
        return pictures;
    }

    public String[] getPicture(int ID){
        String[] picture = null;
        Cursor cursor = querySQL("SELECT * FROM " + DBhelper.TABLE_PICTURE + " WHERE " + DBhelper.ID_PIC + "=" + ID, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            picture = new String[5];
            picture[0] = cursor.getString(cursor.getColumnIndex(DBhelper.ID_PIC));
            picture[1] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_NAME));
            picture[2] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_RATING));
            picture[3] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_FAV));
            picture[4] = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_SECTION_NAME));

        }
        else{
            picture = new String[0];
        }
        cursor.close();
        CloseDB();
        return picture;
    }

    public void updatePhoto(int ID, String name, float rating, String section, boolean favorite){
        Object[] aData = {name, rating, favorite, section, ID};
        executeSQL("UPDATE " + DBhelper.TABLE_PICTURE + " SET "
                + DBhelper.COLUMN_NAME + " = ?, "
                + DBhelper.COLUMN_RATING + " = ?, "
                + DBhelper.COLUMN_FAV + " = ?, "
                + DBhelper.COLUMN_SECTION_NAME + " = ? " +
                " WHERE " + DBhelper.ID_PIC + " = ?", aData);
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
        private static final String COLUMN_SECTION_NAME = "section";

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
                    + DBhelper.COLUMN_FAV + " INTEGER"
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
