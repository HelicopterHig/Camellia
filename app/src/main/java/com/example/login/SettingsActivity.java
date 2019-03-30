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
import android.widget.EditText;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {
    private Button button;

    private SlidrInterface slidr;
    public static String server_name = "message.dlinkddns.com:8008";

    protected String name, second_name, password, email, birthday_date;
    protected int user_note_id = 0, icon_id = 0;

    EditText editText_name;
    EditText editText_second_name;
    EditText editText_email;
    EditText editText_password;
    EditText editText_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        slidr = Slidr.attach(this);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        name = getIntent().getStringExtra("name");
        second_name = getIntent().getStringExtra("second_name");
        email = getIntent().getStringExtra("email");
        birthday_date = getIntent().getStringExtra("birthday_date");
        password = getIntent().getStringExtra("password");


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
                view.startAnimation(animAlpha);

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
                    new ChangeData().execute();
                }catch (Exception e){
                    e.printStackTrace();
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
                String parammetrs = "?id=1&name="+name+"&second_name="+second_name+"&password="+password+"&email="+email+"&user_note_id="+user_note_id+"&icon_id="+icon_id;
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
}