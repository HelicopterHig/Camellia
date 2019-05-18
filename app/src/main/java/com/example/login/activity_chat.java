package com.example.login;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

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
import com.example.login.LocalDataBase.Note;
import com.example.login.LocalDataBase.User;
import com.example.login.LocalDataBase.User_group;

public class activity_chat extends AppCompatActivity {

    public static String server_name = "message.dlinkddns.com:8008";

    private static String TAG_MESSAGE = "message";
    private static String TAG_MESSAGE_ID = "id";
    private static String TAG_TEXT = "user_message";
    private static String TAG_DATETIME = "datetime";
    private static String TAG_USER_ID = "user_id";
    private static String TAG_GROUP_ID = "group_id";

    private static String TAG_NOTE = "note";
    private static String TAG_NOTE_ID = "id";
    private static String TAG_NOTE_NAME = "name";
    private static String TAG_NOTE_DATE = "date";
    private static String TAG_NOTE_DIS = "discription";
    private static String TAG_NOTE_DONE = "done";
    private static String TAG_NOTE_USER_ID = "user_id";
    private static String TAG_NOTE_GROUP_ID = "group_id";

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

    int note_id, note_user_id, note_group_id, note_user_icon;
    String note_name, note_date, note_dis, note_user_name, note_user_sec, note_user_email;
    boolean note_done;

    String name, second_name, name_u;
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

    SharedPref sharedpref;

    int icon_id, foto, icon_id_user;
    CheckIcon c_icon;

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        c_icon = new CheckIcon();

        db = new DatabaseHandler(this);

        List<User> dataUser = db.getAllContacts();

        for (User userD : dataUser){
            user_id = userD.getID();
            name = userD.getName();
            second_name = userD.getSecName();
            icon_id_user = userD.getIcon();
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
                if(!message.getText().toString().isEmpty() && text_mess.matches("(?i).*[А-Яа-яA-Za-z0-9!@#$%^&*()_+№;:?=/|.><,`~{}].*")) {
                    date_now = new Date();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    datetime = simpleDateFormat.format(date_now);

                    name_u = name + " " + second_name;
                    insertItemMessage(count++, text_mess, name_u, icon_id_user);

                    db.addMessage(new Message((message_id + 1), text_mess, datetime, user_id, group_id, "dfdf", "dfdf", "fdgg", icon_id_user));

                    try {
                        new InsertNewMeassage().execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    message.setText("");
                    recyclerView_mess.smoothScrollToPosition(count + 1);
                }
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



        timer.schedule(timerTask, 1000, 500);
    }

    public void insertItemMessage(int position, String text_message, String user_name, int ico){
        int iconUser = c_icon.checkIconUser(ico);
        itemChatArrayList.add(position, new ItemChat(text_message, datetime, iconUser, ItemChat.MESS_COMP, user_name));
        adapter_mess.notifyDataSetChanged();
    }

    public void insertItemMessageCOMP(int position, String text_message, String user_name, int ico){
        int iconUser = c_icon.checkIconUser(ico);
        itemChatArrayList.add(position, new ItemChat(text_message, datetime, iconUser, ItemChat.MESS_USER, user_name));
        adapter_mess.notifyDataSetChanged();
    }

    public void createListMessage(){
        itemChatArrayList = new ArrayList<>();

        List<Message> messageList = db.getAllMessages();
        List<User_group> user_groupList = db.getAllUser_groups();

        for (Message messD : messageList){
            group_mess_id = messD.get_groupID();
            if (group_mess_id == group_id) {
                user_id_mess = messD.get_userID();
                text_message = messD.get_text();
                message_id = messD.get_messageID();
                name = messD.get_userName();
                second_name = messD.get_userSurname();

                for (User_group ug : user_groupList){
                    if(ug.get_user_id() == user_id_mess){
                        icon_id = ug.get_icon_id();
                    }
                }

                name_u = name+" "+second_name;

                if (user_id_mess == user_id) {
                    foto = c_icon.checkIconUser(icon_id_user);
                    itemChatArrayList.add(new ItemChat(messD.get_text(), messD.get_datetime(), foto, ItemChat.MESS_COMP, name_u));
                } else {
                    foto = c_icon.checkIconUser(icon_id);
                    itemChatArrayList.add(new ItemChat(messD.get_text(), messD.get_datetime(), foto, ItemChat.MESS_USER, name_u));
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
            List<User_group> user_groupList = db.getAllUser_groups();

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

                        for (User_group ug : user_groupList){
                            if (ug.get_user_id() == user_id_mess){
                                name = ug.get_userName();
                                second_name = ug.get_userSurname();
                                icon_id = ug.get_icon_id();

                                name_u = name+" "+second_name;
                            }
                        }

                        if (user_id_mess != user_id) {
                            db.addMessage(new Message(message_id, text_mess, datetime, user_id_mess, group_id, "fgdg", "fgdfg", "sdfsdf", icon_id));
                            insertItemMessageCOMP(count++, text_mess, name_u, icon_id);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.note_note)
        {
            Toast.makeText(this, "Share menu is Clicked", Toast.LENGTH_SHORT).show();
            try{
                new LoadNote().execute();
            }catch (Exception e){
                e.printStackTrace();
            }

            while(flag == 0){
                System.out.println("load note");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            startActivity(new Intent(this, Activity_Note.class));

        }
        else if(id==R.id.note_user)
        {
            Toast.makeText(this, "Attach menu is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Activity_users.class));


        }
        else if(id==R.id.note_progr)
        {
            Toast.makeText(this, "Attach menu is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Activity_progress.class));


        }
        return super.onOptionsItemSelected(item);
    }

    class LoadNote extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                db.deleteAllNotes();
                flag = 0;
                String myURL = "http://"+server_name+"/load_note.php?&group_id="+group_id;

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

                    JSONArray note = jsonObject.getJSONArray(TAG_NOTE);

                    for (int i = 0; i < note.length(); i++){
                        JSONObject schedule = note.getJSONObject(i);

                        note_id = Integer.parseInt(schedule.getString(TAG_NOTE_ID));
                        note_name = schedule.getString(TAG_NOTE_NAME);
                        note_date = schedule.getString(TAG_NOTE_DATE);
                        note_dis = schedule.getString(TAG_NOTE_DIS);
                        note_done = Boolean.parseBoolean(schedule.getString(TAG_NOTE_DONE));
                        note_user_id = Integer.parseInt(schedule.getString(TAG_NOTE_USER_ID));
                        note_group_id = Integer.parseInt(schedule.getString(TAG_NOTE_GROUP_ID));

                        List<User_group> user_groupList = db.getAllUser_groups();
                        for (User_group ugr : user_groupList){
                            if (ugr.get_user_id() == note_user_id) {
                                note_user_name = ugr.get_userName();
                                note_user_sec = ugr.get_userSurname();
                                note_user_email = ugr.get_userEmail();
                                note_user_icon = ugr.get_icon_id();
                            }
                        }

                        db.addNote(new Note(note_id, note_name, note_date, note_dis, note_done, note_user_id, note_group_id, note_user_name, note_user_sec, note_user_email, note_user_icon));
                    }

                    System.out.println("Reading all notes..");
                    List<Note> note_local = db.getAllNotes();

                    for (Note cn : note_local) {
                        String log = "Id: " + cn.get_id() + " , NoteID: " + cn.get_noteID() + " , Name: " + cn.get_name() + " , Date: " + cn.get_date()
                                + ", Description: " + cn.get_description() + ", Done: " + cn.get_done()
                                + ", UserID: " + cn.get_userID() + ", GroupID: " + cn.get_groupID();

                        System.out.print("Note: ");
                        System.out.println(log);
                    }


                    flag = 1;

                }catch (Exception e){
                    e.printStackTrace();
                    flag = 1;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

}
