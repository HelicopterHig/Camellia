package com.example.login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class activity_chat_2 extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    //private FirebaseListAdapter<Message> adapter;
    ConstraintLayout activity_chat_2;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_2);

        activity_chat_2 = (ConstraintLayout)findViewById(R.id.activity_chat_2);
        button = (Button)findViewById(R.id.C_button_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.C_enter_message);
                //Отправка сообщения в бд Firebase
                //FirebaseDatabase.getInstance().getReference().push()
                //        .setValue(new Nessage(input,getText().toString(),
                // FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                //input.setText("");
            }
        });

        displayChat();
    }

    private void displayChat(){

        ListView listMessages = (ListView)findViewById(R.id.C_list_messages);




        /*
        //Создаем адаптер списка используя класс FirebaseListAdapter
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.item, FirebaseDatabase.getInstance().getReferencee()) {
            @Override
            protected void populateView(View v, Message model, int position){
            TextView textMessage, autor, timeMessage;
            textMessage = (TextView)v.findViewById(R.id.tvMessage);
            autor = (TextView)v.findViewById(R.id.tvUser);
            timeMessage = (TextView)v.findViewById(R.id.tvTime);
            //
            textMessage.setText(model.getTextMessage());
            autor.setText(model.getAutor());
            timeMessage.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getTimeMessage()));

            }
        };
        listMessages.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayChat();*/
    }
}
