package com.example.task71lostandfoundapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lost_found.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "adverts";

    public static final String COL_ID = "id";
    public static final String COL_POST_TYPE = "post_type";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_DATE = "date_text";
    public static final String COL_LOCATION = "location";
    public static final String COL_CATEGORY = "category";
    public static final String COL_IMAGE_URI = "image_uri";
    public static final String COL_CREATED_AT = "created_at";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creates the SQLite table when the database is first created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_POST_TYPE + " TEXT NOT NULL, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_PHONE + " TEXT NOT NULL, " +
                COL_DESCRIPTION + " TEXT NOT NULL, " +
                COL_DATE + " TEXT NOT NULL, " +
                COL_LOCATION + " TEXT NOT NULL, " +
                COL_CATEGORY + " TEXT NOT NULL, " +
                COL_IMAGE_URI + " TEXT NOT NULL, " +
                COL_CREATED_AT + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    // Recreates database if version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Inserts one lost/found advert into SQLite
    public boolean insertAdvert(Advert advert) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_POST_TYPE, advert.getPostType());
        values.put(COL_NAME, advert.getName());
        values.put(COL_PHONE, advert.getPhone());
        values.put(COL_DESCRIPTION, advert.getDescription());
        values.put(COL_DATE, advert.getDateText());
        values.put(COL_LOCATION, advert.getLocation());
        values.put(COL_CATEGORY, advert.getCategory());
        values.put(COL_IMAGE_URI, advert.getImageUri());
        values.put(COL_CREATED_AT, advert.getCreatedAt());

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Returns all adverts from newest to oldest
    public ArrayList<Advert> getAllAdverts() {
        ArrayList<Advert> adverts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " DESC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                adverts.add(cursorToAdvert(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return adverts;
    }

    // Returns adverts matching the selected category
    public ArrayList<Advert> getAdvertsByCategory(String category) {
        ArrayList<Advert> adverts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + COL_CATEGORY + " = ?" +
                        " ORDER BY " + COL_ID + " DESC",
                new String[]{category}
        );

        if (cursor.moveToFirst()) {
            do {
                adverts.add(cursorToAdvert(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return adverts;
    }

    // Returns one advert by ID for the detail screen
    public Advert getAdvertById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        Advert advert = null;

        if (cursor.moveToFirst()) {
            advert = cursorToAdvert(cursor);
        }

        cursor.close();
        return advert;
    }

    // Deletes advert after item is returned to owner
    public boolean deleteAdvert(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_NAME,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    // Converts database cursor row into Advert object
    private Advert cursorToAdvert(Cursor cursor) {
        return new Advert(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_POST_TYPE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_URI)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_CREATED_AT))
        );
    }
}
