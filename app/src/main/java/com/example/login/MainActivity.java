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
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button1;

    public static String server_name = "message.dlinkddns.com:8008";

    protected String name, second_name, email, password, birthday_date;
    Date date;

    private static String TAG_USER = "user";
    private static String TAG_NAME = "name";
    private static String TAG_SECOND_NAME = "second_name";
    private static String TAG_EMAIL = "email";
    private static String TAG_PASSWORD = "password";
    private static String TAG_BIRTHDAY_DATE = "birthday_date";

    EditText editText;
    EditText editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // блокируем ориантацию на вертикальную
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




        //инициалицируем анимацию
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                view.startAnimation(animAlpha);

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

                }else{
                    Toast.makeText(MainActivity.this,
                            R.string.error_login_msg,
                            Toast.LENGTH_SHORT).show();
                }


              /*  try{
                    new SendLogin().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }*/

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

                        name = schedule.getString(TAG_NAME);
                        second_name = schedule.getString(TAG_SECOND_NAME);
                        password = schedule.getString(TAG_PASSWORD);
                        birthday_date = schedule.getString(TAG_BIRTHDAY_DATE);
                        email = schedule.getString(TAG_EMAIL);

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




    // метод перехода на меню
    public void openLeft(){
        Intent intent  = new Intent(this , Left.class);

        intent.putExtra("name", name);
        intent.putExtra("second_name", second_name);
        intent.putExtra("email", email);
        intent.putExtra("birthday_date", birthday_date);
        intent.putExtra("password", password);

        startActivity(intent);
    }
}