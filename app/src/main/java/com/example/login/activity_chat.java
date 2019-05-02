package com.example.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class activity_chat extends AppCompatActivity {

    private RecyclerView recyclerView_mess;
    private MessageAdapter adapter_mess;
    private RecyclerView.LayoutManager layoutManager_mess;
    private ArrayList<ItemChat> itemChatArrayList;

    EditText message;
    ImageButton fab_send;
    String text_mess;

    DatabaseHandler db;

    String name, second_name;
    int user_id;

    String text_message;
    int user_id_mess;
    int count = 0;
    int group_id, group_mess_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = new DatabaseHandler(this);

        List<User> dataUser = db.getAllContacts();

        for (User userD : dataUser){
            user_id = userD.getID();
            name = userD.getName();
            second_name = userD.getSecName();
        }

        group_id = getIntent().getIntExtra("group_id", group_id) + 1;

        createListMessage();
        buildRecyclerViewMessage();

        fab_send = (ImageButton)findViewById(R.id.fab_send);
        fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = (EditText)findViewById(R.id.input);
                text_mess = String.valueOf(message.getText().toString());
                insertItemMessage(count, text_mess);
                db.addMessage(new Message(2, text_mess, "10.05.2018 10:00", user_id, group_id));
                message.setText("");
                count++;
            }
        });
    }

    public void insertItemMessage(int position, String text_message){
        itemChatArrayList.add(position, new ItemChat(text_message, "2019", R.drawable.ic_android, ItemChat.MESS_COMP));
        adapter_mess.notifyDataSetChanged();
    }

    public void createListMessage(){
        itemChatArrayList = new ArrayList<>();

        List<Message> messageList = db.getAllMessages();

        for (Message messD : messageList){
            group_mess_id = messD.get_groupID();
            if (group_mess_id == group_id) {
                user_id_mess = messD.get_userID();
                text_message = messD.get_text();

                if (user_id_mess == user_id) {
                    itemChatArrayList.add(new ItemChat(text_message, "2019", R.drawable.ic_android, ItemChat.MESS_COMP));
                } else {
                    itemChatArrayList.add(new ItemChat(text_message, "2019", R.drawable.ic_android, ItemChat.MESS_USER));
                }
                count++;
            }
        }
    }

    public void buildRecyclerViewMessage(){
        recyclerView_mess = findViewById(R.id.recyclerView_row_chat);
        recyclerView_mess.setHasFixedSize(true);
        layoutManager_mess = new LinearLayoutManager(this);
        adapter_mess = new MessageAdapter(itemChatArrayList);

        recyclerView_mess.setLayoutManager(layoutManager_mess);
        recyclerView_mess.setAdapter(adapter_mess);
    }
}
