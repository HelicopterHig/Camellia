package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "messenger";
    private static final String TABLE_CONTACTS = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SECOND_NAME = "second_name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_ICON_ID = "icon_id";
    private static final String KEY_BIRTHDAY_DATE = "date";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_AUTHORISED = "authorised";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " text," + KEY_SECOND_NAME
                + " text," + KEY_PASSWORD + " text," + KEY_MAIL + " text,"
                + KEY_ICON_ID  + " integer," + KEY_BIRTHDAY_DATE + " numeric,"
                + KEY_ACCESS_TOKEN + " text," + KEY_REFRESH_TOKEN + " text,"
                + KEY_AUTHORISED + " numeric" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

    @Override
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_SECOND_NAME, contact.getSecName());
        values.put(KEY_PASSWORD, contact.getPassword());
        values.put(KEY_MAIL, contact.getMail());
        values.put(KEY_ICON_ID, contact.getIcon());
        values.put(KEY_BIRTHDAY_DATE, contact.getBdate());
        values.put(KEY_ACCESS_TOKEN, contact.getAcToken());
        values.put(KEY_REFRESH_TOKEN, contact.getReToken());
        values.put(KEY_AUTHORISED, contact.getAuthorised());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    @Override
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_SECOND_NAME, KEY_PASSWORD, KEY_MAIL, KEY_ICON_ID, KEY_BIRTHDAY_DATE,
                        KEY_ACCESS_TOKEN, KEY_REFRESH_TOKEN, KEY_AUTHORISED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getInt(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8), cursor.getInt(9));

        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setSecName(cursor.getString(2));
                contact.setPassword(cursor.getString(3));
                contact.setMail(cursor.getString(4));
                contact.setIcon(cursor.getInt(5));
                contact.setBdate(cursor.getString(6));
                contact.setAcToken(cursor.getString(7));
                contact.setReToken(cursor.getString(8));
                contact.setAuthorised(cursor.getInt(9));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    @Override
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_SECOND_NAME, contact.getSecName());
        values.put(KEY_PASSWORD, contact.getPassword());
        values.put(KEY_MAIL, contact.getMail());
        values.put(KEY_ICON_ID, contact.getIcon());
        values.put(KEY_BIRTHDAY_DATE, contact.getBdate());
        values.put(KEY_ACCESS_TOKEN, contact.getAcToken());
        values.put(KEY_REFRESH_TOKEN, contact.getReToken());
        values.put(KEY_AUTHORISED, contact.getAuthorised());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    @Override
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }

    @Override
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}