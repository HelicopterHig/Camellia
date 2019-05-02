package com.example.login;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.login.ItemChat.MESS_COMP;
import static com.example.login.ItemChat.MESS_USER;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemChat> itemChatList;

    public MessageAdapter (List<ItemChat> list){
        this.itemChatList = list;
    }

    public int getItemViewType(int position){
        if (itemChatList != null){
            ItemChat object = itemChatList.get(position);
            if (object != null){
                return object.getType_mess();
            }
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType){
            case MESS_USER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_message_user, parent, false);
                return new UserMessViewHolder(view);
            case MESS_COMP:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_message_comp, parent, false);
                return new CompMessViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemChat object = itemChatList.get(position);

        if (object != null){
            switch (object.getType_mess()){
                case MESS_USER:
                    ((UserMessViewHolder) holder).text_mess.setText(object.getText_mess());
                    ((UserMessViewHolder) holder).image_mess.setImageResource(object.getImage_user());
                    break;
                case MESS_COMP:
                    ((CompMessViewHolder) holder).text_mess.setText(object.getText_mess());
                    ((CompMessViewHolder) holder).image_mess.setImageResource(object.getImage_user());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (itemChatList == null){
            return 0;
        }
        return itemChatList.size();
    }

    public static class UserMessViewHolder extends RecyclerView.ViewHolder{
        private TextView text_mess;
        private TextView date_mess;
        private ImageView image_mess;

        public UserMessViewHolder(@NonNull View itemView) {
            super(itemView);
            text_mess = (TextView) itemView.findViewById(R.id.titleTextView_user);
            image_mess = (ImageView) itemView.findViewById(R.id.imageView_user);
        }
    }

    public static class CompMessViewHolder extends RecyclerView.ViewHolder{
        private TextView text_mess;
        private TextView date_mess;
        private ImageView image_mess;

        public CompMessViewHolder(@NonNull View itemView) {
            super(itemView);
            text_mess = (TextView) itemView.findViewById(R.id.titleTextView_comp);
            image_mess = (ImageView) itemView.findViewById(R.id.imageView_comp);
        }
    }
}
