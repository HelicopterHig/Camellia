package com.example.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.Note;
import com.example.login.LocalDataBase.User;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<ItemNote> itemNoteArrayList;
    private NoteEventListener Listener;
    private ArrayList<ItemNote> notes;
    public  Context context;
    public DatabaseHandler db;
    public boolean[] checked;
    public int check, pos, user_id;
    private boolean check_note;
    UpdateCheckedNote updateCheckedNote;

    public NoteAdapter(Context context, ArrayList<ItemNote> itemNotes){
        this.context = context;
        this.itemNoteArrayList = itemNotes;
        checked = new boolean[itemNoteArrayList.size()];
    }




    public interface NoteEventListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(NoteEventListener listener){
        Listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_node_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ItemNote currentItem = itemNoteArrayList.get(position);

        db = new DatabaseHandler(context);
        if (currentItem.getCheckNote() == 1){
            check_note = true;
        }else {
            check_note = false;
        }
        pos = position;
        holder.dateTextview.setText(currentItem.getDateNote());
        holder.textView.setText(currentItem.getTextNote());
        holder.checkBox.setChecked(check_note);
        holder.userName.setText(currentItem.getUserName());

        List<User> userList = db.getAllContacts();
        for (User u : userList){
            user_id = u.getID();
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = position;
                checked[pos] = !checked[pos];
                //db = new DatabaseHandler(context);
                ItemNote item = itemNoteArrayList.get(check);
                Note note = db.getNote(item.geNoteIde());
                if (user_id == note.get_userID()) {

                    updateCheckedNote = new UpdateCheckedNote();

                    if (note.get_done() == 1) {
                        note.set_done(0);
                        updateCheckedNote.UpdateCheckNoteVoid(note.get_noteID(), 0);
                    } else {
                        note.set_done(1);
                        updateCheckedNote.UpdateCheckNoteVoid(note.get_noteID(), 1);
                    }

                    db.updateNote(note);


                    List<Note> note_local = db.getAllNotes();


                    for (Note cn : note_local) {
                        String log = "Id: " + cn.get_id() + " , NoteID: " + cn.get_noteID() + " , Name: " + cn.get_name() + " , Date: " + cn.get_date()
                                + ", Description: " + cn.get_description() + ", Done: " + cn.get_done()
                                + ", UserID: " + cn.get_userID() + ", GroupID: " + cn.get_groupID();

                        System.out.print("Note: ");
                        System.out.println(log);
                    }
                }else {
                    if (currentItem.getCheckNote() == 1){
                        check_note = true;
                    }else {
                        check_note = false;
                    }

                    if (check_note == true) {
                        holder.checkBox.setChecked(true);
                    }else {
                        holder.checkBox.setChecked(false);
                    }
                }
            }
        });
    }

    public boolean[] getChecked(){
        return checked;
    }
    @Override
    public int getItemCount() {
        return itemNoteArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextview;
        public TextView textView;
        public CheckBox checkBox;
        public TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextview = itemView.findViewById(R.id.date_node_group_row);
            textView = itemView.findViewById(R.id.name_node_group_row);
            checkBox = itemView.findViewById(R.id.checkBox_node_group_row);
            userName = itemView.findViewById(R.id.userName_node_group_row);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            Listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}

