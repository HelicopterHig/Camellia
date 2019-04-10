package com.example.login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*


Version 0.4.2


 */

public class Activity2 extends AppCompatActivity {
    private Button button;
    public  static String server_name = "message.dlinkddns.com:8008";
    protected String name, second_name, password, email, bithday_date;
    protected int icon_id = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        // блокируем ориентацию на вертикальную
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // инициализируем анимацию
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                //openLeft();

                EditText editText = (EditText)findViewById(R.id.editText);
                name = String.valueOf(editText.getText().toString());

                EditText editText1 = (EditText)findViewById(R.id.editText1);
                second_name = String.valueOf(editText1.getText().toString());

                EditText editText2 = (EditText)findViewById(R.id.editText2);
                password = String.valueOf(editText2.getText().toString());

                EditText editText3 = (EditText)findViewById(R.id.editText3);
                email = String.valueOf(editText3.getText().toString());

                EditText editText4 = (EditText)findViewById(R.id.editText4);
                bithday_date = String.valueOf(editText4.getText().toString());

                EmailValidator eVal = new EmailValidator();
                if(eVal.validate(email) == true && !editText.getText().toString().isEmpty()
                        && !editText1.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()
                        && !editText3.getText().toString().isEmpty() && !editText4.getText().toString().isEmpty()){

                    Toast.makeText(Activity2.this,
                            R.string.success_reg_msg,
                            Toast.LENGTH_SHORT).show();
                    try {
                        new SendData().execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(Activity2.this,
                            R.string.error_reg_msg,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
   /* public void openLeft() {
        Intent intent = new Intent(this, Left.class);
        startActivity(intent);
    }*/

    class SendData extends AsyncTask<Void, Void, Void>{
        String resultString = null;


        @Override
        protected Void doInBackground(Void... params) {
            try{
                String myURL = "http://"+server_name+"/registration.php?id=null&name="+name+"&second_name="+second_name+"&password="+password+"&email="+email+"&icon_id="+icon_id+"&birthday_date="+bithday_date;
                String parammetrs = "?action=insert&id=4&name="+name+"&second_name="+second_name+"&password="+password+"&email="+email+"&icon_id="+icon_id;
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