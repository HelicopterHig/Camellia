package com.example.login.LocalDataBase;

public class User_group {

    int _group_id;
    int _user_id;

    public User_group(){}

    public User_group(int _group_id, int _user_id){
        this._group_id = _group_id;
        this._user_id = _user_id;
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
}
