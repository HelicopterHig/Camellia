package com.example.login;

import java.util.List;

interface IDatabaseHandler {
    public void addContact(Contact contact);
    public Contact getContact(int id);
    public List<Contact> getAllContacts();
    public int getContactsCount();
    public int updateContact(Contact contact);
    public void deleteContact(Contact contact);
    public void deleteAll();
}
