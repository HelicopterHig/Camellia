package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "messenger22";


    //User
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

    //Groups
    private static final String TABLE_GROUPS = "groups";
    private static final String KEY_GROUP_ID = "id";
    private static final String KEY_GROUP_NAME = "group_name";
    private static final String KEY_GROUP_ICON_ID = "group_icon_id";
    private static final String KEY_GROUP_ADMIN_ID = "admin_id";

    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " text," + KEY_SECOND_NAME
            + " text," + KEY_PASSWORD + " text," + KEY_MAIL + " text,"
            + KEY_ICON_ID  + " integer," + KEY_BIRTHDAY_DATE + " numeric,"
            + KEY_ACCESS_TOKEN + " text," + KEY_REFRESH_TOKEN + " text,"
            + KEY_AUTHORISED + " numeric" + ")";

    private static final String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_GROUPS + " ("
            + KEY_GROUP_ID + " INTEGER PRIMARY KEY, " + KEY_GROUP_NAME
            + " TEXT, " + KEY_GROUP_ADMIN_ID + " INTEGER, " + KEY_GROUP_ICON_ID
            + " INTEGER)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_GROUPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);

        onCreate(db);
    }

    @Override
    public void addContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SECOND_NAME, user.getSecName());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_ICON_ID, user.getIcon());
        values.put(KEY_BIRTHDAY_DATE, user.getBdate());
        values.put(KEY_ACCESS_TOKEN, user.getAcToken());
        values.put(KEY_REFRESH_TOKEN, user.getReToken());
        values.put(KEY_AUTHORISED, user.getAuthorised());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    @Override
    public void addGroup(Groups groups){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_GROUP_NAME, groups.get_nameGroup());
        values.put(KEY_GROUP_ADMIN_ID, groups.get_adminID());
        values.put(KEY_GROUP_ICON_ID, groups.get_groupIconID());

        db.insert(TABLE_GROUPS, null, values);
        db.close();
    }

    @Override
    public User getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_SECOND_NAME, KEY_PASSWORD, KEY_MAIL, KEY_ICON_ID, KEY_BIRTHDAY_DATE,
                        KEY_ACCESS_TOKEN, KEY_REFRESH_TOKEN, KEY_AUTHORISED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getInt(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8), cursor.getInt(9));

        return user;
    }

    @Override
    public Groups getGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, new String[] { KEY_GROUP_ID,
                        KEY_GROUP_NAME, KEY_GROUP_ICON_ID, KEY_GROUP_ADMIN_ID }, KEY_GROUP_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Groups groups = new Groups(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));

        return groups;
    }

    @Override
    public List<User> getAllContacts() {
        List<User> userList = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setSecName(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setMail(cursor.getString(4));
                user.setIcon(cursor.getInt(5));
                user.setBdate(cursor.getString(6));
                user.setAcToken(cursor.getString(7));
                user.setReToken(cursor.getString(8));
                user.setAuthorised(cursor.getInt(9));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    @Override
    public List<Groups> getAllGroups() {
        List<Groups> groupsList = new ArrayList<Groups>();
        String selectQuery = "SELECT  * FROM " + TABLE_GROUPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Groups groups = new Groups();
                groups.set_id(Integer.parseInt(cursor.getString(0)));
                groups.set_nameGroup(cursor.getString(1));
                groups.set_adminID(Integer.parseInt(cursor.getString(2)));
                groups.set_groupIconID(Integer.parseInt(cursor.getString(3)));

                groupsList.add(groups);
            } while (cursor.moveToNext());
        }

        return groupsList;
    }

    @Override
    public int updateContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SECOND_NAME, user.getSecName());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_ICON_ID, user.getIcon());
        values.put(KEY_BIRTHDAY_DATE, user.getBdate());
        values.put(KEY_ACCESS_TOKEN, user.getAcToken());
        values.put(KEY_REFRESH_TOKEN, user.getReToken());
        values.put(KEY_AUTHORISED, user.getAuthorised());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getID()) });
    }

    @Override
    public int updateGroup(Groups groups) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_NAME, groups.get_nameGroup());
        values.put(KEY_GROUP_ADMIN_ID, groups.get_adminID());
        values.put(KEY_GROUP_ICON_ID, groups.get_groupIconID());
        return db.update(TABLE_GROUPS, values, KEY_GROUP_ID + " = ?",
                new String[] { String.valueOf(groups.get_id()) });
    }

    @Override
    public void deleteContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(user.getID()) });
        db.close();
    }

    @Override
    public void deleteGroup(Groups groups) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS, KEY_GROUP_ID + " = ?", new String[] { String.valueOf(groups.get_id()) });
        db.close();
    }

    @Override
    public void deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }

    @Override
    public void deleteAllGroups() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS,null, null);
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

    @Override
    public int getGroupsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_GROUPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}