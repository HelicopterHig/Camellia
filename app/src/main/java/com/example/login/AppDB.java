package com.example.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*

Создание бд приложения

 */

public class AppDB extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mess";
    public static final String TABLE_CONTACTS = "user";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SECOND_NAME = "second_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_ICON_ID = "icon_id";
    public static final String KEY_BIRTHDAY_DATE = "date";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_AUTHORISED = "authorised";

    public AppDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_SECOND_NAME
                + " text," + KEY_PASSWORD + " text," + KEY_MAIL + " text,"
                + KEY_ICON_ID  + " integer," + KEY_BIRTHDAY_DATE + " numeric,"
                + KEY_ACCESS_TOKEN + " text," + KEY_REFRESH_TOKEN + " text,"
                + KEY_AUTHORISED + " numeric" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);

        onCreate(db);

    }
}