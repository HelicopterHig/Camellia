package com.example.login.LocalDataBase;

public class User_group {


    int _group_id;
    int _user_id;
    String _userName;
    String _userSurname;
    String _userEmail;
    int _icon_id;

    public User_group(){}

    public User_group(int _group_id, int _user_id, String _userName, String _userSurname, String _userEmail, int _icon_id){
        this._group_id = _group_id;
        this._user_id = _user_id;
        this._userName = _userName;
        this._userSurname = _userSurname;
        this._userEmail = _userEmail;
        this._icon_id = _icon_id;
    }

    public int get_group_id() {
        return _group_id;
    }
    public void set_group_id(int _group_id) {
        this._group_id = _group_id;
    }

    public int get_user_id() {
        return _user_id;
    }
    public void set_user_id(int _user_id) {
        this._user_id = _user_id;
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
