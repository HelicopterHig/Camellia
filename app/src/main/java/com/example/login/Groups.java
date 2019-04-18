package com.example.login;

public class Groups {

    int _id;
    String _name;
    int _adminID;
    int _groupIconID;

    public Groups(){}

    public Groups(int _id, String _name, int _adminID, int _groupIconID){
        this._id = _id;
        this._name = _name;
        this._groupIconID = _groupIconID;
        this._adminID = _adminID;
    }

    public Groups(String _name, int _adminID, int _groupIconID){
        this._name = _name;
        this._groupIconID = _groupIconID;
        this._adminID = _adminID;
    }

    public int get_id() {
        return this._id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_nameGroup() {
        return this._name;
    }
    public void set_nameGroup(String _name) {
        this._name = _name;
    }

    public int get_groupIconID() {
        return this._groupIconID;
    }
    public void set_groupIconID(int _groupIconID) {
        this._groupIconID = _groupIconID;
    }

    public int get_adminID() {
        return this._adminID;
    }
    public void set_adminID(int _adminID) {
        this._adminID = _adminID;
    }
}
