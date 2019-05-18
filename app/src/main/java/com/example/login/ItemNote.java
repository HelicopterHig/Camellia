package com.example.login;

public class ItemNote {
    private String dateNote;
    private String textNote;
    private int note_id;
    private boolean checkNote;

    public ItemNote(String _dateNote, String _textNote, boolean _checkNote, int _note_id){
        dateNote = _dateNote;
        textNote = _textNote;
        checkNote = _checkNote;
        note_id = _note_id;
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
    public int geNoteIde(){
        return note_id;
    }
}
