package com.example.login;

import java.io.Serializable;

public class ItemModel implements Serializable {

    private String title, subTitle;

    public ItemModel(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
