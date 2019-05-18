package com.example.login;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {

    private ArrayList<ItemProgress> itemProgressArrayList;

    public ProgressAdapter(ArrayList<ItemProgress> itemProgress){
        itemProgressArrayList = itemProgress;
    }

    @NonNull
    @Override
    public ProgressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
        ProgressAdapter.ViewHolder viewHolder = new ProgressAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressAdapter.ViewHolder holder, int position) {
        ItemProgress currentItem = itemProgressArrayList.get(position);

        holder.ugImageView.setImageResource(currentItem.getUpImageResource());
        holder.ugTextView.setText(currentItem.getUpName());
        String prog = String.valueOf(100*currentItem.getProgressDone()/currentItem.getProgressAll());
        prog = prog +"%";
        holder.ugProgressView.setText (prog); //Изменить
    }

    @Override
    public int getItemCount() {
        return itemProgressArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ugImageView;
        public TextView ugTextView;
        public TextView ugProgressView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ugImageView = itemView.findViewById(R.id.imageView_prog);
            ugTextView = itemView.findViewById(R.id.user_name);
            ugProgressView = itemView.findViewById(R.id.textView_prog);
        }
    }
}
