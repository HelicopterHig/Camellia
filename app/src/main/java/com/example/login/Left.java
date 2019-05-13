package com.example.login;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Button;
import android.content.Intent;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.Groups;
import com.example.login.LocalDataBase.Message;
import com.example.login.LocalDataBase.User;
import com.example.login.features.demo.styled.StyledMessagesActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Left extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //объявляем fab
    ImageButton floatButton;
    //объявляем обновление списка диалогов
    SwipeRefreshLayout swipeRefreshLayout;

    int number = 0;

    protected String user_id_id, name, second_name, email, password, birthday_date;
    protected String name_group, secreteKey;
    int user_id;

    TextView textView_name;
    TextView textView_email;

    EditText editText;

    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private DialogAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Item> mItemArrayList;

    DatabaseHandler db;

    public static String server_name = "message.dlinkddns.com:8008";

    private static String TAG_MESSAGE = "message";
    private static String TAG_MESSAGE_ID = "id";
    private static String TAG_TEXT = "user_message";
    private static String TAG_DATETIME = "datetime";
    private static String TAG_USER_ID = "user_id";
    private static String TAG_GROUP_ID = "group_id";

    int message_id, user_id_mess, group_id;
    String text_mess, datetime;

    int flag = 0, temp, size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left);

        user_id_id = getIntent().getStringExtra("user_id");

        db = new DatabaseHandler(this);

        List<User> dataUser = db.getAllContacts();

        for (User userD : dataUser){
            user_id = userD.getID();
            name = userD.getName();
            second_name = userD.getSecName();
            email = userD.getMail();
        }

        createList();
        buildRecyclerView();

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                number ++;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 4000);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*user_id = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        second_name = getIntent().getStringExtra("second_name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        birthday_date = getIntent().getStringExtra("birthday_date");*/

        // реализация нажатия кнопки fab
        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Left.this);
                final View mView = getLayoutInflater().inflate(R.layout.create_project, null);
                editText = (EditText) mView.findViewById(R.id.etEmail);
                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name_group = String.valueOf(editText.getText().toString());

                        secreteKey = GenerateKey.generateKey();

                        try{
                            new CreateGroup().execute();
                            db.addGroup(new Groups(2, 1, name_group, user_id, 1));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                        size = mItemArrayList.size();
                        insertItem(size, name_group);
                    }
                });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        textView_name = (TextView) headerView.findViewById(R.id.textView_name);
        textView_email = (TextView) headerView.findViewById(R.id.textView_email);

        textView_name.setText(name+" "+second_name);
        textView_email.setText(email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
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
            Intent intent = new Intent(this, TskActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.esc) {
            // Handle the camera action
        } else if (id == R.id.pro) {

        } else if (id == R.id.cal) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
            return false;

        } else if (id == R.id.sett) {

            Intent intent = new Intent(this, SettingsActivity.class);

            intent.putExtra("name", name);
            intent.putExtra("second_name", second_name);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("birthday_date", birthday_date);

            startActivity(intent);
            return false;



        } else if (id == R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            db.deleteAllContacts();
            db.deleteAllGroups();
            db.deleteAllMessages();
            db.deleteAllNotes();
            db.deleteAllUNotes();
            mItemArrayList.clear();
            startActivity(intent);
            return false;
        }else if (id == R.id.about) {
            Intent intent = new Intent(this, About_activity.class);

            startActivity(intent);
            return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class CreateGroup extends AsyncTask<Void, Void, Void> {
        String resultString = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String myURL = "http://" + server_name + "/group.php?id=null&name_group=" + name_group + "&admin_user_id=" + user_id + "&group_icon_id=1" + "&secrete_key=" + secreteKey;
                String parammetrs = "?id=null&name_group=" + name_group + "&admin_user_id=" + user_id + "&group_icon_id=1";
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


    public void insertItem(int position, String name_group){
        mItemArrayList.add(position, new Item(R.drawable.ic_android, name_group));
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position){
        mItemArrayList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text){
        mItemArrayList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createList(){
        mItemArrayList = new ArrayList<>();

        List<Groups> groups_local = db.getAllGroups();

        for (Groups gr : groups_local){
            mItemArrayList.add(new Item(R.drawable.ic_android, gr.get_nameGroup()));
        }
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView_row);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DialogAdapter( mItemArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //changeItem(position, "Clicked");
                group_id = position + 1;

                /*List<Groups> groups_local = db.getAllGroups();
                for(Groups gr : groups_local){
                    temp = gr.get_id();
                    if (temp == (position+1)){
                        group_id = gr.get_groupID();
                    }
                }*/

                try {
                    flag = 0;
                    new LoadMessage().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

                while(flag == 0){
                    System.out.println("load");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                /*try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                Intent intent = new Intent(Left.this, activity_chat.class);
                intent.putExtra("group_id", group_id);
                startActivity(intent);
            }
        });
    }

    class LoadMessage extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                System.out.println("load and load");

                db.deleteAllMessages();
                flag = 0;
                String myURL = "http://"+server_name+"/load_message.php?&group_id="+group_id;

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


                    for (int i = 0; i < message.length(); i++){
                        JSONObject schedule = message.getJSONObject(i);

                        message_id = Integer.parseInt(schedule.getString(TAG_MESSAGE_ID));
                        text_mess = schedule.getString(TAG_TEXT);
                        datetime = schedule.getString(TAG_DATETIME);
                        user_id_mess = Integer.parseInt(schedule.getString(TAG_USER_ID));
                        group_id = Integer.parseInt(schedule.getString(TAG_GROUP_ID));

                        db.addMessage(new Message(message_id, text_mess, datetime, user_id_mess, group_id, "user_name", "user_surname"));
                    }

                    System.out.println("Reading all messages..");
                    List<Message> message_local = db.getAllMessages();

                    for (Message cn : message_local) {
                        String log = "Id: " + cn.get_id() + " , MessageID: " + cn.get_messageID() + " , Text: " + cn.get_text() + " , DateTime: " + cn.get_datetime()
                                + ", UserID: " + cn.get_userID() + ", GroupID: " + cn.get_groupID();

                        System.out.print("Message: ");
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

    private String convertStreamToString(InputStream stream) {
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}