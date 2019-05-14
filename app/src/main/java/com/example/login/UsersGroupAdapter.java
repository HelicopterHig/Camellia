package com.example.login;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersGroupAdapter extends RecyclerView.Adapter<UsersGroupAdapter.ViewHolder> {
    private ArrayList<ItemUserGroup> itemUserGroupArrayList;

    public UsersGroupAdapter(ArrayList<ItemUserGroup> itemUserGroups){
        itemUserGroupArrayList = itemUserGroups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_users_group, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemUserGroup currentItem = itemUserGroupArrayList.get(position);

        holder.ugImageView.setImageResource(currentItem.getUgImageResource());
        holder.ugTextView.setText(currentItem.getUgName());
    }

    @Override
    public int getItemCount() {
        return itemUserGroupArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ugImageView;
        public TextView ugTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ugImageView = itemView.findViewById(R.id.imageView_row_users_group);
            ugTextView = itemView.findViewById(R.id.textView_row_users_group);
        }
    }
}
