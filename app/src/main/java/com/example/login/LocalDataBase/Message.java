package com.example.login.LocalDataBase;

public class Message {

    int _id;
    int _messageID;
    String _text;
    String _datetime;
    int _userID;
    int _groupID;
    String _userName;
    String _userSurname;
    String _userEmail;
    int _icon_id;

    public Message(){}

    public Message(int _id, int _messageID, String _text, String _datetime, int _userID, int _groupID, String _userName, String _userSurname, String _userEmail, int _icon_id){
        this._id = _id;
        this._messageID = _messageID;
        this._text = _text;
        this._datetime = _datetime;
        this._userID = _userID;
        this._groupID = _groupID;
        this._userName = _userName;
        this._userSurname = _userSurname;
        this._userEmail = _userEmail;
        this._icon_id = _icon_id;
    }

    public Message(int _messageID, String _text, String _datetime, int _userID, int _groupID, String _userName, String _userSurname, String _userEmail, int _icon_id){
        this._messageID = _messageID;
        this._text = _text;
        this._datetime = _datetime;
        this._userID = _userID;
        this._groupID = _groupID;
        this._userName = _userName;
        this._userSurname = _userSurname;
        this._userEmail = _userEmail;
        this._icon_id = _icon_id;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_messageID() {
        return _messageID;
    }
    public void set_messageID(int _messageID) {
        this._messageID = _messageID;
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

    public String get_userName() {
        return _userName;
    }
    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_userSurname() {
        return _userSurname;
    }
    public void set_userSurname(String _userSurname) {
        this._userSurname = _userSurname;
    }

    public String get_userEmail() {
        return _userEmail;
    }
    public void set_userEmail(String _userEmail) {
        this._userEmail = _userEmail;
    }

    public int get_icon_id() {
        return _icon_id;
    }
    public void set_icon_id(int _icon_id) {
        this._icon_id = _icon_id;
    }
}
