package com.example.login;

public class ItemNote {
    private String dateNote;
    private String textNote;
    private boolean checkNote;

    public ItemNote(String _dateNote, String _textNote, boolean _checkNote){
        dateNote = _dateNote;
        textNote = _textNote;
        checkNote = _checkNote;
    }

    public String getDateNote(){
        return dateNote;
    }

    public String getTextNote(){
        return textNote;
    }

    public boolean getCheckNote(){
        return checkNote;
    }
}
