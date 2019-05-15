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

import java.util.Date;

public class NoteEditActivity extends AppCompatActivity {
    private EditText inputNote;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        Toolbar toolbar = findViewById(R.id.edit_note_activity_toolbar);
        setSupportActionBar(toolbar);

        inputNote = findViewById(R.id.input_note);

    }
    public void openTskActivity(){
        Intent intent  = new Intent(this,Activity_Note.class );
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

        String text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();
/*            if (temp == null) {
                temp = new NoteModel(text, date);
                dao.insertNote(temp);
            } else {
                temp.setNoteText(text);
                temp.setNoteDate(date);
                dao.updateNote(temp);
            }
*/
            Toast.makeText(NoteEditActivity.this,
                    R.string.save_note,
                    Toast.LENGTH_SHORT).show();
            openTskActivity();
        }

    }

}
