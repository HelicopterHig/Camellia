package com.example.login;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.MaskFilter;

import java.util.List;

interface IDatabaseHandler {
    void onCreate(SQLiteDatabase db);

    public void addContact(User user);
    public void addGroup(Groups groups);
    public void addMessage(Message message);
    public void addNote(Note note);
    public void addUNote(UNote uNote);
    public User getContact(int id);
    public Groups getGroup(int id);
    public Message getMessage(int id);
    public Note getNote(int id);
    public List<User> getAllContacts();
    public List<Groups> getAllGroups();
    public List<Message> getAllMessages();
    public List<Note> getAllNotes();
    public List<UNote> getAllUNotes();
    public int getContactsCount();
    public int getGroupsCount();
    public int getMessageCount();
    public int getNoteCount();
    public int updateContact(User user);
    public int updateGroup(Groups groups);
    public int updateMessage(Message message);
    public int updateNote(Note note);
    public void deleteContact(User user);
    public void deleteGroup(Groups groups);
    public void deleteMessage(Message message);
    public void deleteNote(Note note);
    public void deleteAllContacts();
    public void deleteAllGroups();
    public void deleteAllMessages();
    public void deleteAllNotes();
    public void deleteAllUNotes();
}
