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
    private static final String DATABASE_NAME = "messenger1488";


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
    private static final String KEY_GROUP_GROUP_ID = "groupID";
    private static final String KEY_GROUP_SECRET = "secret";
    private static final String KEY_GROUP_NAME = "group_name";
    private static final String KEY_GROUP_ICON_ID = "group_icon_id";
    private static final String KEY_GROUP_ADMIN_ID = "admin_id";

    //Messages
    private static final String TABLE_MESSAGE = "messages";
    private static final String KEY_MESSAGE_ID = "id";
    private static final String KEY_MESSAGE_MESSAGE_ID = "messageID";
    private static final String KEY_MESSAGE_TEXT = "text";
    private static final String KEY_MESSAGE_DATETIME = "datetime";
    private static final String KEY_MESSAGE_USER_ID = "userID";
    private static final String KEY_MESSAGE_GROUP_ID = "groupID";

    //Note
    private static final String TABLE_NOTE = "note";
    private static final String KEY_NOTE_ID = "id";
    private static final String KEY_NOTE_NOTE_ID = "noteID";
    private static final String KEY_NOTE_NAME = "name";
    private static final String KEY_NOTE_DATE = "date";
    private static final String KEY_NOTE_DESCRIPTION = "description";
    private static final String KEY_NOTE_DONE = "done";
    private static final String KEY_NOTE_USER_ID = "userID";
    private static final String KEY_NOTE_GROUP_ID = "groupID";

    //UNote
    private static final String TABLE_UNOTE = "unote";
    private static final String KEY_UNOTE_ID = "id";
    private static final String KEY_UNOTE_UNOTE_ID = "unoteID";
    private static final String KEY_UNOTE_NAME = "name";
    private static final String KEY_UNOTE_DATE = "date";
    private static final String KEY_UNOTE_DESCRIPTION = "description";
    private static final String KEY_UNOTE_DONE = "done";
    private static final String KEY_UNOTE_USER_ID = "userID";

    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " text," + KEY_SECOND_NAME
            + " text," + KEY_PASSWORD + " text," + KEY_MAIL + " text,"
            + KEY_ICON_ID  + " integer," + KEY_BIRTHDAY_DATE + " numeric,"
            + KEY_ACCESS_TOKEN + " text," + KEY_REFRESH_TOKEN + " text,"
            + KEY_AUTHORISED + " numeric" + ")";

    private static final String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_GROUPS + " ("
            + KEY_GROUP_ID + " INTEGER PRIMARY KEY, " + KEY_GROUP_GROUP_ID + " INTEGER, " + KEY_GROUP_SECRET + " INTEGER, " + KEY_GROUP_NAME
            + " TEXT, " + KEY_GROUP_ADMIN_ID + " INTEGER, " + KEY_GROUP_ICON_ID
            + " INTEGER)";

    private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_MESSAGE + " ("
            + KEY_MESSAGE_ID + " INTEGER PRIMARY KEY, " + KEY_MESSAGE_MESSAGE_ID + " INTEGER, " + KEY_MESSAGE_TEXT + " TEXT, "
            + KEY_MESSAGE_DATETIME + " DATETIME, " + KEY_MESSAGE_USER_ID + " INTEGER, "
            + KEY_MESSAGE_GROUP_ID + " INTEGER)";

    private static final String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTE + " ("
            + KEY_NOTE_ID + " INTEGER PRIMARY KEY, " + KEY_NOTE_NOTE_ID + " INTEGER, " + KEY_NOTE_NAME + " STRING, "
            + KEY_NOTE_DATE + " DATE, " + KEY_NOTE_DESCRIPTION + " text, "
            + KEY_NOTE_DONE + " BINARY, " + KEY_NOTE_USER_ID + " INTEGER, "
            + KEY_NOTE_GROUP_ID + " INTEGER)";

    private static final String CREATE_UNOTE_TABLE = "CREATE TABLE " + TABLE_UNOTE + " ("
            + KEY_UNOTE_ID + " INTEGER PRIMARY KEY, " + KEY_UNOTE_UNOTE_ID + " INTEGER, " + KEY_UNOTE_NAME + " STRING, "
            + KEY_UNOTE_DATE + " DATE, " + KEY_UNOTE_DESCRIPTION + " text, "
            + KEY_UNOTE_DONE + " BINARY, " + KEY_UNOTE_USER_ID + " INTEGER)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_GROUPS_TABLE);
        db.execSQL(CREATE_MESSAGE_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_UNOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNOTE);

        onCreate(db);
    }

    @Override
    public void addContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getID());
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

        values.put(KEY_GROUP_GROUP_ID, groups.get_groupID());
        values.put(KEY_GROUP_SECRET, groups.get_secret());
        values.put(KEY_GROUP_NAME, groups.get_nameGroup());
        values.put(KEY_GROUP_ADMIN_ID, groups.get_adminID());
        values.put(KEY_GROUP_ICON_ID, groups.get_groupIconID());

        db.insert(TABLE_GROUPS, null, values);
        db.close();
    }

    @Override
    public void addMessage(Message message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MESSAGE_MESSAGE_ID, message.get_messageID());
        values.put(KEY_MESSAGE_TEXT, message.get_text());
        values.put(KEY_MESSAGE_DATETIME, message.get_datetime());
        values.put(KEY_MESSAGE_USER_ID, message.get_userID());
        values.put(KEY_MESSAGE_GROUP_ID, message.get_groupID());

        db.insert(TABLE_MESSAGE, null, values);
        db.close();
    }

    @Override
    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NOTE_NOTE_ID, note.get_noteID());
        values.put(KEY_NOTE_NAME, note.get_name());
        values.put(KEY_NOTE_DATE, note.get_date());
        values.put(KEY_NOTE_DESCRIPTION, note.get_description());
        values.put(KEY_NOTE_DONE, note.get_done());
        values.put(KEY_NOTE_USER_ID, note.get_userID());
        values.put(KEY_NOTE_GROUP_ID, note.get_groupID());

        db.insert(TABLE_NOTE, null, values);
        db.close();
    }

    public void addUNote(UNote unote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_UNOTE_UNOTE_ID, unote.get_unoteID());
        values.put(KEY_UNOTE_NAME, unote.get_name());
        values.put(KEY_UNOTE_DATE, unote.get_date());
        values.put(KEY_UNOTE_DESCRIPTION, unote.get_description());
        values.put(KEY_UNOTE_DONE, unote.get_done());
        values.put(KEY_UNOTE_USER_ID, unote.get_userID());

        db.insert(TABLE_UNOTE, null, values);
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

        Cursor cursor = db.query(TABLE_GROUPS, new String[] { KEY_GROUP_ID, KEY_GROUP_GROUP_ID, KEY_GROUP_SECRET,
                        KEY_GROUP_NAME, KEY_GROUP_ICON_ID, KEY_GROUP_ADMIN_ID }, KEY_GROUP_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Groups groups = new Groups(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)));

        return groups;
    }

    @Override
    public Message getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, new String[] { KEY_MESSAGE_ID, KEY_MESSAGE_MESSAGE_ID,
                        KEY_MESSAGE_TEXT, KEY_MESSAGE_DATETIME, KEY_MESSAGE_USER_ID, KEY_MESSAGE_GROUP_ID }, KEY_GROUP_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Message message = new Message(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)));

        return message;
    }

    @Override
    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, new String[] { KEY_NOTE_ID, KEY_NOTE_NOTE_ID, KEY_NOTE_NAME,
                        KEY_NOTE_DATE, KEY_NOTE_DESCRIPTION, KEY_NOTE_DONE, KEY_NOTE_USER_ID, KEY_NOTE_GROUP_ID}, KEY_GROUP_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Note note = new Note(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                Boolean.parseBoolean(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)));

        return note;
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
                groups.set_groupID(Integer.parseInt(cursor.getString(1)));
                groups.set_secret(Integer.parseInt(cursor.getString(2)));
                groups.set_nameGroup(cursor.getString(3));
                groups.set_adminID(Integer.parseInt(cursor.getString(4)));
                groups.set_groupIconID(Integer.parseInt(cursor.getString(5)));

                groupsList.add(groups);
            } while (cursor.moveToNext());
        }

        return groupsList;
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<Message>();
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.set_id(Integer.parseInt(cursor.getString(0)));
                message.set_messageID(Integer.parseInt(cursor.getString(1)));
                message.set_text(cursor.getString(2));
                message.set_datetime(cursor.getString(3));
                message.set_userID(Integer.parseInt(cursor.getString(4)));
                message.set_groupID(Integer.parseInt(cursor.getString(5)));

                messageList.add(message);
            } while (cursor.moveToNext());
        }

        return messageList;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<Note>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.set_id(Integer.parseInt(cursor.getString(0)));
                note.set_noteID(Integer.parseInt(cursor.getString(1)));
                note.set_name(cursor.getString(2));
                note.set_date(cursor.getString(3));
                note.set_description(cursor.getString(4));
                note.set_done(Boolean.parseBoolean(cursor.getString(5)));
                note.set_userID(Integer.parseInt(cursor.getString(6)));
                note.set_groupID(Integer.parseInt(cursor.getString(7)));

                noteList.add(note);
            } while (cursor.moveToNext());
        }

        return noteList;
    }

    @Override
    public List<UNote> getAllUNotes() {
        List<UNote> unoteList = new ArrayList<UNote>();
        String selectQuery = "SELECT  * FROM " + TABLE_UNOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UNote unote = new UNote();
                unote.set_id(Integer.parseInt(cursor.getString(0)));
                unote.set_unoteID(Integer.parseInt(cursor.getString(1)));
                unote.set_name(cursor.getString(2));
                unote.set_date(cursor.getString(3));
                unote.set_description(cursor.getString(4));
                unote.set_done(Boolean.parseBoolean(cursor.getString(5)));
                unote.set_userID(Integer.parseInt(cursor.getString(6)));

                unoteList.add(unote);
            } while (cursor.moveToNext());
        }

        return unoteList;
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
        values.put(KEY_GROUP_GROUP_ID, groups.get_groupID());
        values.put(KEY_GROUP_SECRET, groups.get_secret());
        values.put(KEY_GROUP_NAME, groups.get_nameGroup());
        values.put(KEY_GROUP_ADMIN_ID, groups.get_adminID());
        values.put(KEY_GROUP_ICON_ID, groups.get_groupIconID());
        return db.update(TABLE_GROUPS, values, KEY_GROUP_ID + " = ?",
                new String[] { String.valueOf(groups.get_id()) });
    }

    @Override
    public int updateMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_MESSAGE_ID, message.get_messageID());
        values.put(KEY_MESSAGE_TEXT, message.get_text());
        values.put(KEY_MESSAGE_DATETIME, message.get_datetime());
        values.put(KEY_MESSAGE_USER_ID, message.get_userID());
        values.put(KEY_MESSAGE_GROUP_ID, message.get_groupID());
        return db.update(TABLE_MESSAGE, values, KEY_MESSAGE_ID + " = ?",
                new String[] { String.valueOf(message.get_id()) });
    }

    @Override
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_NOTE_ID, note.get_noteID());
        values.put(KEY_NOTE_NAME, note.get_name());
        values.put(KEY_NOTE_DATE, note.get_date());
        values.put(KEY_NOTE_DESCRIPTION, note.get_description());
        values.put(KEY_NOTE_DONE, note.get_done());
        values.put(KEY_NOTE_USER_ID, note.get_userID());
        values.put(KEY_NOTE_GROUP_ID, note.get_groupID());

        return db.update(TABLE_NOTE, values, KEY_NOTE_ID + " = ?",
                new String[] { String.valueOf(note.get_id()) });
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
    public void deleteMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE, KEY_MESSAGE_ID + " = ?", new String[] { String.valueOf(message.get_id()) });
        db.close();
    }

    @Override
    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, KEY_NOTE_ID + " = ?", new String[] { String.valueOf(note.get_id()) });
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
    public void deleteAllMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE,null, null);
        db.close();
    }

    @Override
    public void deleteAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE,null, null);
        db.close();
    }

    @Override
    public void deleteAllUNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_UNOTE,null, null);
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

    @Override
    public int getMessageCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public int getNoteCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}