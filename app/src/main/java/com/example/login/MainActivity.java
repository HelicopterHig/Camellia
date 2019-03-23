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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button1;

    public static String server_name = "message.dlinkddns.com:8008";
    protected String email, password;

    private static String TAG_USER = "user";
    private static String TAG_EMAIL = "email";
    private static String TAG_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);





        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                view.startAnimation(animAlpha);

                EditText editText = (EditText) findViewById(R.id.editText);
                email = String.valueOf(editText.getText().toString());

                EditText editText2 = (EditText) findViewById(R.id.editText2);
                password = String.valueOf(editText2.getText().toString());

                try{
                    new SendLogin().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
                //openActivity2();

            }
        });

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
                String myURL = "http://"+server_name+"/check_login2.php?password="+password+"&email="+email;

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

                        String password = schedule.getString(TAG_PASSWORD);
                        String email = schedule.getString(TAG_EMAIL);

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

    private String convertStreamToString(InputStream stream) {
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }





    public void openLeft(){
        Intent intent  = new Intent(this , Left.class);
        startActivity(intent);
    }
}
