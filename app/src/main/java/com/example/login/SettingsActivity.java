package com.example.login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.User;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private Button button;

    private Switch myswitch;
    SharedPref sharedpref;

    private SlidrInterface slidr;
    public static String server_name = "message.dlinkddns.com:8008";

    protected String name, second_name, password, email, birthday_date, access, refresh;
    protected int icon_id = 0, user_id;

    //для таблицы user_token
    protected String refresh_id, refresh_user_id, refreshTokensMap;

    private  static String TAG_REFRESH_USER = "user";
    private static String TAG_REFRESH_ID = "id";
    private static String TAG_REFRESH_USER_ID = "user_id";
    private static String TAG_REFRESH_REFRESHTOKENSMAP = "refreshTokensMap";

    private boolean check = false;

    EditText editText_name;
    EditText editText_second_name;
    EditText editText_email;
    EditText editText_password;
    EditText editText_date;

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

     //   getSupportActionBar().hide();
        slidr = Slidr.attach(this);

        myswitch=(Switch)findViewById(R.id.myswitch);
        if (sharedpref.loadNightModeState()==true) {
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedpref.setNightModeState(true);
                    restartApp();
                }
                else {
                    sharedpref.setNightModeState(false);
                    restartApp();
                }
            }
        });
       // final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        db = new DatabaseHandler(this);

        List<User> dataUser = db.getAllContacts();

        for (User userD : dataUser){
            user_id = userD.getID();
            name = userD.getName();
            second_name = userD.getSecName();
            email = userD.getMail();
            birthday_date = userD.getBdate();
            password = userD.getPassword();
            access = userD.getAcToken();
            refresh = userD.getReToken();
        }

        editText_name = (EditText)findViewById(R.id.editText9);
        editText_second_name = (EditText)findViewById(R.id.editText4);
        editText_email = (EditText)findViewById(R.id.editText5);
        editText_password = (EditText)findViewById(R.id.editText6);
        editText_date = (EditText)findViewById(R.id.editText7);

        editText_name.setText(name);
        editText_second_name.setText(second_name);
        editText_email.setText(email);
        editText_password.setText(password);
        editText_date.setText(birthday_date);

        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.startAnimation(animAlpha);

                editText_name = (EditText)findViewById(R.id.editText9);
                name = String.valueOf(editText_name.getText().toString());

                editText_second_name = (EditText)findViewById(R.id.editText4);
                second_name = String.valueOf(editText_second_name.getText().toString());

                editText_email = (EditText)findViewById(R.id.editText5);
                email = String.valueOf(editText_email.getText().toString());

                editText_password = (EditText)findViewById(R.id.editText6);
                password = String.valueOf(editText_password.getText().toString());

                editText_date = (EditText)findViewById(R.id.editText7);
                birthday_date = String.valueOf(editText_date.getText().toString());

                try {
                    new SendRefresh().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (check == true) {
                    try {
                        new ChangeData().execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    openLogin();
                    db.deleteAllContacts();
                    db.deleteAllGroups();
                }
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    class ChangeData extends AsyncTask<Void, Void, Void>{
        String resultString = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String myURL = "http://"+server_name+"/update.php?id=1&name="+name+"&second_name="+second_name+"&password="+password+"&email="+email+"&icon_id="+icon_id+"&birthday_date="+birthday_date;
                String parammetrs = "?id=1&name="+name+"&second_name="+second_name+"&password="+password+"&email="+email+"&user_note_id="+"&icon_id="+icon_id;
                byte[] data = null;
                InputStream is = null;


                try{
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

                    if (responseCode == 200){
                        is = conn.getInputStream();

                        byte[] buffer = new byte[8192];
                        int bytesRead;

                        while ((bytesRead = is.read(buffer)) != -1){
                            baos.write(buffer, 0, bytesRead);
                        }

                        data = baos.toByteArray();
                        resultString = new String(data, "UTF-8");

                    }else{
                        conn.disconnect();
                    }

                    openLogin();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
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

                    if (refreshTokensMap.equals(refresh)){
                        check = true;
                    }else{
                        check = false;
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

    private String convertStreamToString(InputStream stream) {
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public void restartApp () {
        Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(i);
        finish();
    }

}
