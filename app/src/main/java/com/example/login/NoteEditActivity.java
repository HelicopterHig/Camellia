package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NoteEditActivity extends AppCompatActivity {
    Button saveNote;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        saveNote = (Button) findViewById(R.id.button_note_save);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NoteEditActivity.this,
                        R.string.save_note,
                        Toast.LENGTH_SHORT).show();
                        openTskActivity();

            }
        });
    }
    public void openTskActivity(){
        Intent intent  = new Intent(this, TskActivity.class);
        startActivity(intent);
    }
}
