package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.Note;
import com.example.login.LocalDataBase.UNote;
import com.example.login.LocalDataBase.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteEditActivity extends AppCompatActivity {
    private EditText inputNote;
    public Note temp;
    String text;
    private String notename;
    int noteID;
    DatabaseHandler db;
    String name, surname, datenote, email;
    int id, icon;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        db = new DatabaseHandler(this);
        Toolbar toolbar = findViewById(R.id.edit_note_activity_toolbar);
        setSupportActionBar(toolbar);
        notename = getIntent().getStringExtra("notename");
        noteID = getIntent().getIntExtra("finalnoteID", -1);
        inputNote = findViewById(R.id.input_note);


        if (noteID !=-1) {
            temp = new Note();
            temp = db.getNote(noteID);
            inputNote.setText(temp.get_description());
        }
        else {
            inputNote.setFocusable(true);
        }

    }
    public void openTskActivity(){
        Intent intent  = new Intent(this,Activity_Note.class );
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note_from_menu){
            onSaveNote();
        }

        return super.onOptionsItemSelected(item);
    }


    private void onSaveNote() {

        System.out.println(noteID);
        text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
            datenote = df.format(c);
            List<User> list = db.getAllContacts();
            for (User ug: list){
                id = ug.getID();
                name = ug.getName();
                surname = ug.getSecName();
                email = ug.getMail();
                icon = ug.getIcon();
            }

            if  (temp == null) {
                temp = new Note(1, notename, datenote, text, false, id, 1, name, surname, email, icon); //Добавить параметры
                db.addNote(temp);
                System.out.println("Reading all notes..");
                List<Note> note_local = db.getAllNotes();

                for (Note cn : note_local) {
                    String log = "Id: " + cn.get_id() + " , NoteID: " + cn.get_noteID() + " , Name: " + cn.get_name() + " , Date: " + cn.get_date()
                            + ", Description: " + cn.get_description() + ", Done: " + cn.get_done()
                            + ", UserID: " + cn.get_userID() + ", GroupID: " + cn.get_groupID();

                    System.out.print("Note: ");
                    System.out.println(log);
                }

            } else {
                temp.set_description(text);
                temp.set_date(datenote);
                db.updateNote(temp);
            }

            Toast.makeText(NoteEditActivity.this,
                    R.string.save_note,
                    Toast.LENGTH_SHORT).show();
            openTskActivity();
        }

    }

}
