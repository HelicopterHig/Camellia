package com.example.login.LocalDataBase;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

interface IDatabaseHandler {
    void onCreate(SQLiteDatabase db);

    // Методы пользователя

    public void addContact(User user);
    public User getContact(int id);
    public List<User> getAllContacts();
    public int getContactsCount();
    public int updateContact(User user);
    public void deleteContact(User user);
    public void deleteAllContacts();

    // Методы групп

    public void addGroup(Groups groups);
    public Groups getGroup(int id);
    public List<Groups> getAllGroups();
    public int getGroupsCount();
    public int updateGroup(Groups groups);
    public void deleteGroup(Groups groups);
    public void deleteAllGroups();

    // Методы сообщений

    public void addMessage(Message message);
    public Message getMessage(int id);
    public List<Message> getAllMessages();
    public int getMessageCount();
    public int updateMessage(Message message);
    public void deleteMessage(Message message);
    public void deleteAllMessages();

    // Методы заметок

    public void addNote(Note note);
    public Note getNote(int id);
    public List<Note> getAllNotes();
    public int getNoteCount();
    public int updateNote(Note note);
    public void deleteNote(Note note);
    public void deleteAllNotes();

    // Методы личных заметок

    public void addUNote(UNote uNote);
    public UNote getUNote(int id);
    public List<UNote> getAllUNotes();
    public int getUNoteCount();
    public int updateUNote(UNote unote);
    public void deleteUNote(UNote uNote);
    public void deleteAllUNotes();

    // Методы User_group

    public void addUser_group(User_group user_group);
    public User_group getUser_group(int group_id);
    public List<User_group> getAllUser_groups();
    public int getUser_groupCount();
    public int updateUser_group(User_group user_group);
    public void deleteUser_group(User_group user_group);
    public void deleteAllUser_groups();

}
