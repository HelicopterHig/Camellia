package com.example.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Activity_Note extends Base_Activity {
    ImageButton floatButton;
    protected String name_note;
    EditText editText;

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemNote> itemNoteArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        createListMessage();
        buildRecyclerViewMessage();

        floatButton = (ImageButton) findViewById(R.id.imageButton_insert_note);
        floatButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Activity_Note.this);
                final View mView = getLayoutInflater().inflate(R.layout.create_note, null);
                editText = (EditText) mView.findViewById(R.id.NameNote);
                Button mLogin = (Button) mView.findViewById(R.id.NoteCreate);
                Button nLogin = (Button) mView.findViewById(R.id.CancelNote);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        name_note = String.valueOf(editText.getText().toString());



                        dialog.dismiss();
                        openEditNoteActivity();

//                        insertItem(position, name_group);
                    }
                });
                nLogin.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });


            }

        });
    }

    private void createListMessage() {
        itemNoteArrayList = new ArrayList<>();

        itemNoteArrayList.add(new ItemNote("13 март", "Создать пользовательский интерфейс", false));
        itemNoteArrayList.add(new ItemNote("20 март", "Создать локальную базу данных", false));

    }

    private void buildRecyclerViewMessage() {
        recyclerView = findViewById(R.id.recyclerView_row_note_group);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        noteAdapter = new NoteAdapter(itemNoteArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);
    }

    public void openEditNoteActivity(){
        Intent intent  = new Intent(this, NoteEditActivity.class);
        startActivity(intent);
    }
}