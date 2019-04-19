package com.example.login;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.MaskFilter;

import java.util.List;

interface IDatabaseHandler {
    void onCreate(SQLiteDatabase db);

    public void addContact(User user);
    public void addGroup(Groups groups);
    //public void addMessage(Message message);
    public User getContact(int id);
    public Groups getGroup(int id);
    //public Message getMessage(int id);
    public List<User> getAllContacts();
    public List<Groups> getAllGroups();
    //public List<Message> getAllMessages();
    public int getContactsCount();
    public int getGroupsCount();
    //public int getMessageCount();
    public int updateContact(User user);
    public int updateGroup(Groups groups);
    //public int updateMessage(Message message);
    public void deleteContact(User user);
    public void deleteGroup(Groups groups);
    //public void deleteMessage(Message message);
    public void deleteAllContacts();
    public void deleteAllGroups();
    //public void deleteAllMessages();
}
