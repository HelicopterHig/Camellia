package com.example.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.Message;
import com.example.login.LocalDataBase.User;

public class activity_chat extends AppCompatActivity {

    public static String server_name = "message.dlinkddns.com:8008";

    private static String TAG_MESSAGE = "message";
    private static String TAG_MESSAGE_ID = "id";
    private static String TAG_TEXT = "user_message";
    private static String TAG_DATETIME = "datetime";
    private static String TAG_USER_ID = "user_id";
    private static String TAG_GROUP_ID = "group_id";

    private RecyclerView recyclerView_mess;
    private MessageAdapter adapter_mess;
    private RecyclerView.LayoutManager layoutManager_mess;
    private ArrayList<ItemChat> itemChatArrayList;

    SwipeRefreshLayout swipeRefreshLayout;

    EditText message;
    ImageButton fab_send;
    String text_mess;

    String datetime;
    DatabaseHandler db;

    String name, second_name;
    int user_id, message_id;

    String text_message;
    int user_id_mess;
    int count = 0;
    int group_id, group_mess_id;

    Date date_now;
    int number = 0;

    TimerTask timerTask;
    Timer timer;
    Button back;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db = new DatabaseHandler(this);

        List<User> dataUser = db.getAllContacts();

        for (User userD : dataUser){
            user_id = userD.getID();
            name = userD.getName();
            second_name = userD.getSecName();
        }

        List<Message> messageList = db.getAllMessages();

        for (Message messD : messageList){
            group_mess_id = messD.get_groupID();
            if (group_mess_id == group_id) {
                user_id_mess = messD.get_userID();
                text_message = messD.get_text();
                message_id = messD.get_messageID();
            }
        }

        date_now = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        datetime = simpleDateFormat.format(date_now);

        group_id = getIntent().getIntExtra("group_id", group_id);

        createListMessage();
        buildRecyclerViewMessage();

        count = itemChatArrayList.size();

        recyclerView_mess.smoothScrollToPosition(count);

        fab_send = (ImageButton)findViewById(R.id.fab_send);
        fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = (EditText)findViewById(R.id.input);
                text_mess = String.valueOf(message.getText().toString());

                date_now = new Date();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                datetime = simpleDateFormat.format(date_now);

                insertItemMessage(count++, text_mess);

                db.addMessage(new Message((message_id+1), text_mess, datetime, user_id, group_id));

                try{
                    new InsertNewMeassage().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

                message.setText("");
                recyclerView_mess.smoothScrollToPosition(count+1);

            }
        });

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try{
                    new CheckMessage().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };



        timer.schedule(timerTask, 1000, 1000);
    }

    public void insertItemMessage(int position, String text_message){
        itemChatArrayList.add(position, new ItemChat(text_message, datetime, R.drawable.ic_android, ItemChat.MESS_COMP));
        adapter_mess.notifyDataSetChanged();
    }

    public void insertItemMessageCOMP(int position, String text_message){
        itemChatArrayList.add(position, new ItemChat(text_message, datetime, R.drawable.ic_android, ItemChat.MESS_USER));
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
                message_id = messD.get_messageID();

                if (user_id_mess == user_id) {
                    itemChatArrayList.add(new ItemChat(messD.get_text(), messD.get_datetime(), R.drawable.ic_android, ItemChat.MESS_COMP));
                } else {
                    itemChatArrayList.add(new ItemChat(messD.get_text(), messD.get_datetime(), R.drawable.ic_android, ItemChat.MESS_USER));
                }
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

    class CheckMessage extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            List<Message> messageList = db.getAllMessages();

            for (Message messD : messageList){
                group_mess_id = messD.get_groupID();
                if (group_mess_id == group_id) {
                    user_id_mess = messD.get_userID();
                    text_message = messD.get_text();
                    message_id = messD.get_messageID();
                }
            }
            try {
                String myURL = "http://" + server_name + "/check_message.php?&group_id=" + group_id + "&message_id=" + message_id;

                try {
                    URL url = new URL(myURL);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    conn.connect();

                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    JSONObject jsonObject = new JSONObject(data);

                    JSONArray message = jsonObject.getJSONArray(TAG_MESSAGE);

                    for (int i = 0; i < message.length(); i++) {
                        JSONObject schedule = message.getJSONObject(i);

                        message_id = Integer.parseInt(schedule.getString(TAG_MESSAGE_ID));
                        text_mess = schedule.getString(TAG_TEXT);
                        datetime = schedule.getString(TAG_DATETIME);
                        user_id_mess = Integer.parseInt(schedule.getString(TAG_USER_ID));
                        group_id = Integer.parseInt(schedule.getString(TAG_GROUP_ID));


                        if (user_id_mess != user_id) {
                            db.addMessage(new Message(message_id, text_mess, datetime, user_id_mess, group_id));
                            insertItemMessageCOMP(count++, text_mess);
                            recyclerView_mess.smoothScrollToPosition(count);
                        }
                    }

                    System.out.println("Reading all messages..");
                    List<Message> message_local = db.getAllMessages();

                    for (Message cn : message_local) {
                        String log = "Id: " + cn.get_id() + " , MessageID: " + cn.get_messageID() + " , Text: " + cn.get_text() + " , DateTime: " + cn.get_datetime()
                                + ", UserID: " + cn.get_userID() + ", GroupID: " + cn.get_groupID();

                        System.out.print("Message: ");
                        System.out.println(log);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    class InsertNewMeassage extends AsyncTask<Void, Void, Void>{
        String resultString = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String myURL = "http://" + server_name + "/insert_new_message.php?user_message=" + text_mess + "&datetime=" + datetime + "&user_id=" + user_id + "&group_id=" + group_id;
                String parammetrs = "?id=null&name_group=" + group_id + "&admin_user_id=" + user_id + "&group_icon_id=1";
                byte[] data = null;
                InputStream is = null;


                try {
                    URL url = new URL(myURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");

                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.getBytes().length));
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    data = parammetrs.getBytes("UTF-8");

                    OutputStream os = conn.getOutputStream();

                    os.write(data);
                    os.flush();
                    os.close();
                    data = null;
                    conn.connect();
                    int responseCode = conn.getResponseCode();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    if (responseCode == 200) {
                        is = conn.getInputStream();

                        byte[] buffer = new byte[8192];
                        int bytesRead;

                        while ((bytesRead = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, bytesRead);
                        }

                        data = baos.toByteArray();
                        resultString = new String(data, "UTF-8");

                    } else {
                        conn.disconnect();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private String convertStreamToString(InputStream stream) {
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public void onBackPressed(){
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.left, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // переход по кнопке (в правом углу ) на  замекти
        if (id == R.id.next){
            Intent intent = new Intent(this, Base_Activity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
