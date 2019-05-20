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

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<ItemNote> itemNoteArrayList;
    private NoteEventListener Listener;
    private ArrayList<ItemNote> notes;
    public  Context context;
    public DatabaseHandler db;
    public boolean[] checked;
    public int check;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ItemNote currentItem = itemNoteArrayList.get(position);

        holder.dateTextview.setText(currentItem.getDateNote());
        holder.textView.setText(currentItem.getTextNote());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = position;
                checked[position] = !checked[position];
                db = new DatabaseHandler(context);
                ItemNote item = itemNoteArrayList.get(check);
                Note note = db.getNote(item.geNoteIde());

                String log1 = "Id: " + note.get_id() + " , NoteID: " + note.get_noteID() + " , Name: " + note.get_name() + " , Date: " + note.get_date()
                        + ", Description: " + note.get_description() + ", Done: " + note.get_done()
                        + ", UserID: " + note.get_userID() + ", GroupID: " + note.get_groupID();

                System.out.print("ENote: ");
                System.out.println(log1);

                if(note.get_done()) {
                    note.set_done(false);
                }

                else {
                    note.set_done(true);
                    log1 = "Id: " + note.get_id() + " , NoteID: " + note.get_noteID() + " , Name: " + note.get_name() + " , Date: " + note.get_date()
                            + ", Description: " + note.get_description() + ", Done: " + note.get_done()
                            + ", UserID: " + note.get_userID() + ", GroupID: " + note.get_groupID();
                    System.out.print("ENote: ");
                    System.out.println(log1);
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

            }
        });

        holder.checkBox.setChecked(checked[position]);


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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextview = itemView.findViewById(R.id.date_node_group_row);
            textView = itemView.findViewById(R.id.name_node_group_row);
            checkBox = itemView.findViewById(R.id.checkBox_node_group_row);


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

