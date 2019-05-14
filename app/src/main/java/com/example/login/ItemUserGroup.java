package com.example.login;

public class ItemUserGroup {
    private int ugImageResource;
    private String ugName;

    public ItemUserGroup(int imageResource, String name){
        ugImageResource = imageResource;
        ugName = name;
    }

    public int getUgImageResource(){
        return ugImageResource;
    }

    public String getUgName(){
        return ugName;
    }
}
