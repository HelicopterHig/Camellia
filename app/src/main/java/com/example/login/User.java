package com.example.login;

public class User {

    int _id;
    String _name;
    String _second_name;
    String _password;
    String _email;
    int _icon_id;
    String _birthday_date;
    String _access_token;
    String _refresh_token;
    int _authorised;

    public User(){
    }

    public User(int _id, String _name, String _second_name, String _password, String _email,
                int _icon_id, String _birthday_date, String _access_token, String _refresh_token,
                int _authorised){
        this._id = _id;
        this._name = _name;
        this._second_name = _second_name;
        this._password = _password;
        this._email = _email;
        this._icon_id = _icon_id;
        this._birthday_date = _birthday_date;
        this._access_token = _access_token;
        this._refresh_token = _refresh_token;
        this._authorised = _authorised;
    }

    public User(String _name, String _second_name, String _password, String _email, int _icon_id,
                String _birthday_date, String _access_token, String _refresh_token, int _authorised){
        this._name = _name;
        this._second_name = _second_name;
        this._password = _password;
        this._email = _email;
        this._icon_id = _icon_id;
        this._birthday_date = _birthday_date;
        this._access_token = _access_token;
        this._refresh_token = _refresh_token;
        this._authorised = _authorised;
    }

    public int getID(){
        return this._id;
    }
    public void setID(int _id){
        this._id = _id;
    }

    public String getName(){
        return this._name;
    }
    public void setName(String _name){
        this._name = _name;
    }

    public String getSecName(){
        return this._second_name;
    }
    public void setSecName(String _second_name){
        this._second_name = _second_name;
    }

    public String getPassword(){
        return this._password;
    }
    public void setPassword(String _password){
        this._password = _password;
    }

    public String getMail(){
        return this._email;
    }
    public void setMail(String _email){
        this._email = _email;
    }

    public int getIcon(){
        return this._icon_id;
    }
    public void setIcon(int _icon_id){
        this._icon_id = _icon_id;
    }

    public String getBdate(){
        return this._birthday_date;
    }
    public void setBdate(String _birthday_date){
        this._birthday_date = _birthday_date;
    }

    public String getAcToken(){
        return this._access_token;
    }
    public void setAcToken(String _access_token){
        this._access_token = _access_token;
    }

    public String getReToken(){
        return this._refresh_token;
    }
    public void setReToken(String _refresh_token){
        this._refresh_token = _refresh_token;
    }

    public int getAuthorised(){
        return this._authorised;
    }
    public void setAuthorised(int _authorised){
        this._authorised = _authorised;
    }
}
