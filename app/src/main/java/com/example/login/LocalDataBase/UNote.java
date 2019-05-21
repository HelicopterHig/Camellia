package com.example.login.LocalDataBase;

public class UNote {

    int _id;
    int _unoteID;
    String _name;
    String _date;
    String _description;
    int _done;
    int _userID;

    public UNote(){}

    public UNote(int _id, int _unoteID, String _name, String _date, String _description, int _done, int _userID){
        this._id = _id;
        this._unoteID = _unoteID;
        this._name = _name;
        this._date = _date;
        this._description = _description;
        this._done = _done;
        this._userID = _userID;
    }

    public UNote(int _unoteID, String _name, String _date, String _description, int _done, int _userID){
        this._unoteID = _unoteID;
        this._name = _name;
        this._date = _date;
        this._description = _description;
        this._done = _done;
        this._userID = _userID;
    }

    public int get_userID() {
        return _userID;
    }
    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_unoteID() {
        return _unoteID;
    }
    public void set_unoteID(int _unoteID) {
        this._unoteID = _unoteID;
    }

    public int get_done() {
        return _done;
    }
    public void set_done(int _done) {
        this._done = _done;
    }

    public String get_date() {
        return _date;
    }
    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_description() {
        return _description;
    }
    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_name() {
        return _name;
    }
    public void set_name(String _name) {
        this._name = _name;
    }
}
