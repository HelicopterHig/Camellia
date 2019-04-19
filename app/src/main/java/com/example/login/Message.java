package com.example.login;

public class Message {

    int _id;
    String _text;
    String _datetime;
    int _userID;
    int _groupID;

    public Message(){}

    public Message(int _id, String _text, String _datetime, int _userID, int _groupID){
        this._id = _id;
        this._text = _text;
        this._datetime = _datetime;
        this._userID = _userID;
        this._groupID = _groupID;
    }

    public Message(String _text, String _datetime, int _userID, int _groupID){
        this._text = _text;
        this._datetime = _datetime;
        this._userID = _userID;
        this._groupID = _groupID;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_groupID() {
        return _groupID;
    }
    public void set_groupID(int _groupID) {
        this._groupID = _groupID;
    }

    public int get_userID() {
        return _userID;
    }
    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public String get_datetime() {
        return _datetime;
    }
    public void set_datetime(String _datetime) {
        this._datetime = _datetime;
    }

    public String get_text() {
        return _text;
    }
    public void set_text(String _text) {
        this._text = _text;
    }
}
