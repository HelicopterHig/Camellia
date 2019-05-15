package com.example.login;

public class ItemChat {
    public static final int MESS_USER = 0;
    public static final int MESS_COMP = 1;

    private String text_mess;
    private String date_mess;
    private int image_user;
    private int type_mess;
    private String name_user;

    public ItemChat(String text, String date, int image, int type, String name){
        this.text_mess = text;
        this.date_mess = date;
        this.image_user = image;
        this.type_mess = type;
        this.name_user = name;
    }

    public String getText_mess(){
        return text_mess;
    }

    public String getDate_mess(){
        return date_mess;
    }

    public int getImage_user(){
        return image_user;
    }

    public void setText_mess(String text_mess){
        this.text_mess = text_mess;
    }

    public void setDate_mess(String date_mess){
        this.date_mess = date_mess;
    }

    public void setImage_user(int image_user){
        this.image_user = image_user;
    }

    public int getType_mess(){
        return type_mess;
    }

    public void setType_mess(int type_mess){
        this.type_mess = type_mess;
    }

    public String getName_user(){
        return name_user;
    }
}
