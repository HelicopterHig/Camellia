package com.example.login;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<ItemNote> itemNoteArrayList;
    private NoteEventListener Listener;
    private ArrayList<ItemNote> notes;
    public NoteAdapter(ArrayList<ItemNote> itemNotes){
        itemNoteArrayList = itemNotes;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemNote currentItem = itemNoteArrayList.get(position);

        holder.dateTextview.setText(currentItem.getDateNote());
        holder.textView.setText(currentItem.getTextNote());
        holder.checkBox.setChecked(currentItem.getCheckNote());

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

