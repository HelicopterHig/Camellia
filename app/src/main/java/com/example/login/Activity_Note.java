package com.example.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.Note;
import com.example.login.LocalDataBase.UNote;

import java.util.ArrayList;
import java.util.List;

public class Activity_Note extends Base_Activity{
    ImageButton floatButton;
    protected String name_note;
    EditText editText;
    int check = 2;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<Note> enote;
    DatabaseHandler db;
    private ArrayList<ItemNote>  itemNoteArrayList;
    boolean checkNote;
    String dateNote, textNote;
    int note_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        db = new DatabaseHandler(this);

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
        enote = db.getAllNotes();

        for (Note ug : enote){
            dateNote = ug.get_date();
            textNote = ug.get_name();
            checkNote = ug.get_done();
            note_id = ug.get_id();



            itemNoteArrayList.add(new ItemNote(dateNote, textNote, checkNote, note_id));

        }


    }

    private void buildRecyclerViewMessage() {

        createListMessage();

        recyclerView = findViewById(R.id.recyclerView_row_note_group);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        noteAdapter = new NoteAdapter(itemNoteArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);

    }


    //Отображает заметки
    private void loadNotes() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        noteAdapter = new NoteAdapter(itemNoteArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);
        showEmptyView();
        swipeToDeleteHelper.attachToRecyclerView(recyclerView);
        noteAdapter.setOnItemClickListener(new NoteAdapter.NoteEventListener() {
            @Override
            public void onItemClick(int position) {

                ItemNote item = itemNoteArrayList.get(position);
                Note note = new Note();
                note.set_id(item.geNoteIde());
                Intent intent = new Intent(Activity_Note.this, NoteEditActivity.class);
                intent.putExtra("finalnoteID", note.get_id());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    public void openEditNoteActivity(){
        Intent intent  = new Intent(this, NoteEditActivity.class);
        intent.putExtra("notename", name_note);
        startActivity(intent);
    }



    private void showEmptyView() {
        if (itemNoteArrayList.size() == 0) {
            this.recyclerView.setVisibility(View.GONE);
            findViewById(R.id.empty_notes_view).setVisibility(View.VISIBLE);

        } else {
            this.recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.empty_notes_view).setVisibility(View.GONE);
        }
    }


    private ItemTouchHelper swipeToDeleteHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                    if (itemNoteArrayList != null) {
                        // get swiped note
                        ItemNote swipedNote = itemNoteArrayList.get(viewHolder.getAdapterPosition());
                        if (swipedNote != null) {
                            swipeToDelete(swipedNote,viewHolder);

                        }

                    }
                }
            });

    private void swipeToDelete( final ItemNote swipedNote, final RecyclerView.ViewHolder viewHolder) {
        new android.support.v7.app.AlertDialog.Builder(Activity_Note.this)
                .setMessage(R.string.delete_note)
                .setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Удаление заметки
                        Note note = new Note();
                        note.set_id(swipedNote.geNoteIde());
                        db.deleteNote(note);
                        itemNoteArrayList.remove(swipedNote);
                        noteAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        showEmptyView();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());


                    }
                })
                .setCancelable(false)
                .create().show();

    }



}