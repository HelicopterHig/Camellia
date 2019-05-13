package com.example.login;

import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button1;

    public static String server_name = "message.dlinkddns.com:8008";

    //для таблицы user
    protected String name, second_name, email, password, birthday_date, access;
    public int user_id;

    private static String TAG_USER = "user";
    private static String TAG_NAME = "name";
    private static String TAG_SECOND_NAME = "second_name";
    private static String TAG_EMAIL = "email";
    private static String TAG_PASSWORD = "password";
    private static String TAG_BIRTHDAY_DATE = "birthday_date";
    private static String TAG_USER_ID = "id";
    private static String TAG_ACCESS = "accessToken";

    //для таблицы user_token
    protected String refresh_id, refresh_user_id, refreshTokensMap;

    private  static String TAG_REFRESH_USER = "user";
    private static String TAG_REFRESH_ID = "id";
    private static String TAG_REFRESH_USER_ID = "user_id";
    private static String TAG_REFRESH_REFRESHTOKENSMAP = "refreshTokensMap";

    EditText editText;
    EditText editText2;

    DatabaseHandler db;

    private static String TAG_GROUP = "groups";
    private static String TAG_ID = "id";
    private static String TAG_NAME_GROUP = "name_group";
    private static String TAG_ADMIN_USER_ID = "admin_user_id";
    private static String TAG_GROUP_ICON_ID = "group_icon_id";

    public String name_group_user;
    public int id, admin_user_id, group_icon_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // блокируем ориантацию на вертикальную
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // инициализируем бд
        db = new DatabaseHandler(this);


        /*db.deleteAllContacts();
        db.deleteAllGroups();
        db.deleteAllMessages();
        db.deleteAllNotes();
        db.deleteAllUNotes();*/

        List<User> user_local = db.getAllContacts();

        // вывод таблицы для проверки
        for (User cn : user_local) {
            if (cn.getAuthorised() == 1){
                openLeft();
            }else{
                db.deleteAllContacts();
                db.deleteAllGroups();
                db.deleteAllMessages();
                db.deleteAllNotes();
                db.deleteAllUNotes();
            }
        }

        //инициалицируем анимацию
      //  final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
               // view.startAnimation(animAlpha);

                editText = (EditText) findViewById(R.id.editText);
                email = String.valueOf(editText.getText().toString());

                editText2 = (EditText) findViewById(R.id.editText2);
                password = String.valueOf(editText2.getText().toString());

                if(!editText.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()){


                    Toast.makeText(MainActivity.this,
                            R.string.success_login_msg,
                            Toast.LENGTH_SHORT).show();
                    try{
                        new SendLogin().execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        new LoadGroup().execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(MainActivity.this,
                            R.string.error_login_msg,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        // переход по кнопке на активити регистрации
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openActivity2();


            }

        });

    }
    public void openActivity2(){
        Intent intent  = new Intent(this , Activity2.class);
        startActivity(intent);

    }

    class SendLogin extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String myURL = "http://"+server_name+"/check_login.php?password="+password+"&email="+email;

                try{
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

                    JSONArray user = jsonObject.getJSONArray(TAG_USER);

                    for (int i = 0; i < 1; i++){
                        JSONObject schedule = user.getJSONObject(i);

                        user_id = Integer.parseInt(schedule.getString(TAG_USER_ID));
                        name = schedule.getString(TAG_NAME);
                        second_name = schedule.getString(TAG_SECOND_NAME);
                        password = schedule.getString(TAG_PASSWORD);
                        birthday_date = schedule.getString(TAG_BIRTHDAY_DATE);
                        email = schedule.getString(TAG_EMAIL);
                        access = schedule.getString(TAG_ACCESS);
                    }

                    try {
                        new SendRefresh().execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    // openLeft(); НЕ ТРОЖЬ!

                }catch (Exception e){
                    e.printStackTrace();
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

    class SendRefresh extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String myURL = "http://"+server_name+"/send_refresh.php?id=" + user_id;

                try{
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

                    JSONArray refreshToken = jsonObject.getJSONArray(TAG_REFRESH_USER);

                    for (int i = 0; i < 1; i++){
                        JSONObject schedule = refreshToken.getJSONObject(i);

                        refresh_id = schedule.getString(TAG_REFRESH_ID);
                        refresh_user_id = schedule.getString(TAG_REFRESH_USER_ID);
                        refreshTokensMap = schedule.getString(TAG_REFRESH_REFRESHTOKENSMAP);


                    }

                    System.out.println("Inserting contacts ..");
                    // добавляем строку в бд
                    db.addContact(new User(user_id, name, second_name, password, email, 2, birthday_date, access, refreshTokensMap, 1));

                    /*System.out.println("Inserting groups ..");
                    db.addGroup(new Groups(1, 1, "Camellia",1, 2));*/

                    System.out.println("Inserting messages ..");
                    db.addMessage(new Message(1, "Hello.", "10.04.2019 14:00", 1, 1));

                    System.out.println("Inserting notes ..");
                    db.addNote(new Note(1, "Do something.", "20.04.2019", "We need to do something.", true, 1, 1));

                    System.out.println("Inserting unotes ..");
                    db.addUNote(new UNote(1, "Do something.", "20.04.2019", "I need to do something.", true, 1));

                    System.out.println("Reading all contacts..");
                    List<User> user_local = db.getAllContacts();

                    // вывод таблицы для проверки
                    for (User cn : user_local) {
                        String log = "Id: "+cn.getID()+" , Name: " + cn.getName() + " , Second name: " + cn.getSecName()
                                + ", Password: " + cn.getPassword() + ", Email: " + cn.getMail() + ", Icon id: "
                                + cn.getIcon() + ", Birthday date: " + cn.getBdate() + ", Access: "
                                + cn.getAcToken() + ", Refresh: " + cn.getReToken() + ", Authorised: " + cn.getAuthorised();

                        System.out.print("User: ");
                        System.out.println(log);
                    }

                    /*System.out.println("Reading all groups..");
                    List<Groups> groups_local = db.getAllGroups();

                    for (Groups cn : groups_local) {
                        String log = "Id: " + cn.get_id() + " , GroupID: " + cn.get_groupID() + " , Secret: " + cn.get_secret() + " , Name: "
                                + cn.get_nameGroup() + " , AdminID: " + cn.get_adminID() + ", IconID: " + cn.get_groupIconID();

                        System.out.print("Group: ");
                        System.out.println(log);
                    }*/

                    System.out.println("Reading all messages..");
                    List<Message> message_local = db.getAllMessages();

                    for (Message cn : message_local) {
                        String log = "Id: " + cn.get_id() + " , MessageID: " + cn.get_messageID() + " , Text: " + cn.get_text() + " , DateTime: " + cn.get_datetime()
                                + ", UserID: " + cn.get_userID() + ", GroupID: " + cn.get_groupID();

                        System.out.print("Message: ");
                        System.out.println(log);
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

                    System.out.println("Reading all unotes..");
                    List<UNote> unote_local = db.getAllUNotes();

                    for (UNote cn : unote_local) {
                        String log = "Id: " + cn.get_id() + " , UNoteID: " + cn.get_unoteID() + " , Name: " + cn.get_name() + " , Date: " + cn.get_date()
                                + ", Description: " + cn.get_description() + ", Done: " + cn.get_done()
                                + ", UserID: " + cn.get_userID();

                        System.out.print("Unote: ");
                        System.out.println(log);
                    }

                    openLeft();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    class LoadGroup extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String myURL = "http://"+server_name+"/load_group.php?id="+user_id;

                try{
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

                    JSONArray group = jsonObject.getJSONArray(TAG_GROUP);

                    /*for (int i = group.length()-1; i > 0; i--){
                        JSONObject schedule = group.getJSONObject(i);

                        id = Integer.parseInt(schedule.getString(TAG_ID));
                        name_group_user = schedule.getString(TAG_NAME_GROUP);
                        admin_user_id = Integer.parseInt(schedule.getString(TAG_ADMIN_USER_ID));
                        group_icon_id = Integer.parseInt(schedule.getString(TAG_GROUP_ICON_ID));

                        db.addGroup(new Groups(2, id, name_group_user, admin_user_id, group_icon_id));
                    }*/

                    for (int i = 0; i < group.length(); i++){
                        JSONObject schedule = group.getJSONObject(i);

                        id = Integer.parseInt(schedule.getString(TAG_ID));
                        name_group_user = schedule.getString(TAG_NAME_GROUP);
                        admin_user_id = Integer.parseInt(schedule.getString(TAG_ADMIN_USER_ID));
                        group_icon_id = Integer.parseInt(schedule.getString(TAG_GROUP_ICON_ID));

                        db.addGroup(new Groups(2, id, name_group_user, admin_user_id, group_icon_id));
                    }

                    System.out.println("Reading all groups..");
                    List<Groups> groups_local = db.getAllGroups();

                    for (Groups cn : groups_local) {
                        String log = "Id: " + cn.get_id() + " , GroupID: " + cn.get_groupID() + " , Secret: " + cn.get_secret() + " , Name: "
                                + cn.get_nameGroup() + " , AdminID: " + cn.get_adminID() + ", IconID: " + cn.get_groupIconID();

                        System.out.print("Group: ");
                        System.out.println(log);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }





    // метод перехода на меню
    public void openLeft(){
        Intent intent  = new Intent(this , Left.class);

        intent.putExtra("user_id", user_id);
        intent.putExtra("name", name);
        intent.putExtra("second_name", second_name);
        intent.putExtra("email", email);
        intent.putExtra("birthday_date", birthday_date);
        intent.putExtra("password", password);

        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
