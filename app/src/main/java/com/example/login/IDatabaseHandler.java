package com.example.login;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

interface IDatabaseHandler {
    void onCreate(SQLiteDatabase db);

    public void addContact(User user);
    public void addGroup(Groups groups);
    public User getContact(int id);
    public Groups getGroup(int id);
    public List<User> getAllContacts();
    public List<Groups> getAllGroups();
    public int getContactsCount();
    public int getGroupsCount();
    public int updateContact(User user);
    public int updateGroup(Groups groups);
    public void deleteContact(User user);
    public void deleteGroup(Groups groups);
    public void deleteAllContacts();
    public void deleteAllGroups();
}
