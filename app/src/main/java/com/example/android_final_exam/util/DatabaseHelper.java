package com.example.android_final_exam.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "location_database";

    private static final String TABLE_NAME = "location";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_ADDRESS = "address";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                //COLUMN_ID+ " INTEGER NOT NULL CONSTRAINT location_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LATITUDE+ " DOUBLE NOT NULL, " +
                COLUMN_LONGITUDE+ " DOUBLE NOT NULL, " +
                COLUMN_ADDRESS+ " VARCHAR(50) NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the table if exist
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    // INSERT
    public boolean addLocation(Double latitude, Double longitude, String address){
        // we need writable instance of db
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need content values to write into db
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LATITUDE, latitude);
        contentValues.put(COLUMN_LONGITUDE, longitude);
        contentValues.put(COLUMN_ADDRESS, address);

        return sqLiteDatabase.insert(TABLE_NAME,null, contentValues) != -1;
    }

    public Cursor getAllLocations(){
        // we need readable instance of db
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

}
