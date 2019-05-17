package com.example.login.LocalDataBase;

import java.lang.ref.SoftReference;

public class Note {

    int _id;
    int _noteID;
    String _name;
    String _date;
    String _description;
    Boolean _done;
    int _userID;
    int _groupID;
    String _userEmail;

    public Note(){}

    public Note(int _id, int _noteID, String _name, String _date, String _description, boolean _done, int _userID, int _groupID){
        this._id = _id;
        this._noteID = _noteID;
        this._name = _name;
        this._date = _date;
        this._description = _description;
        this._done = _done;
        this._userID = _userID;
        this._groupID = _groupID;
        //this._userEmail = _userEmail;
    }

    public Note(int _noteID, String _name, String _date, String _description, boolean _done, int _userID, int _groupID){
        this._noteID = _noteID;
        this._name = _name;
        this._date = _date;
        this._description = _description;
        this._done = _done;
        this._userID = _userID;
        this._groupID = _groupID;
        //this._userEmail = _userEmail;

    }

    public int get_userID() {
        return _userID;
    }
    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public int get_groupID() {
        return _groupID;
    }
    public void set_groupID(int _groupID) {
        this._groupID = _groupID;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_noteID() {
        return _noteID;
    }
    public void set_noteID(int _noteID) {
        this._noteID = _noteID;
    }

    public Boolean get_done() {
        return _done;
    }
    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_date() {
        return _date;
    }
    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_description() {
        return _description;
    }
    public void set_done(Boolean _done) {
        this._done = _done;
    }

    public String get_name() {
        return _name;
    }
    public void set_name(String _name) {
        this._name = _name;
    }

}


