package com.example.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.User;
import com.example.login.LocalDataBase.User_group;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Activity_users extends Base_Activity {
    private RecyclerView recyclerView_usersGroup;
    private UsersGroupAdapter usersGroupAdapter;
    private RecyclerView.LayoutManager layoutManager_usersGroup;
    private ArrayList<ItemUserGroup> itemUserGroupArrayList;

    public String email, name, second_name, name_user;
    int group_id;

    ImageButton imageButton;

    EditText editText;

    SharedPref sharedpref;

    DatabaseHandler db;

    List<User_group> user_groups;

    public static String server_name = "message.dlinkddns.com:8008";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        db = new DatabaseHandler(this);

        createListUsersGroup();
        buildRecyclerViewUsersGroup();

        imageButton = (ImageButton) findViewById(R.id.imageButton_insert_user);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Activity_users.this);
                final View mView = getLayoutInflater().inflate(R.layout.add_user, null);
                editText = (EditText) mView.findViewById(R.id.AddIdUser);
                Button mLogin = (Button) mView.findViewById(R.id.AddUser);
                Button nLogin = (Button) mView.findViewById(R.id.CancelAddUser);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email = String.valueOf(editText.getText().toString());
                        try {
                            new InsertUser().execute();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                nLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
            }
        });
    }

    private void createListUsersGroup() {
        itemUserGroupArrayList = new ArrayList<>();

        user_groups = db.getAllUser_groups();

        for (User_group ug : user_groups){
            group_id = ug.get_group_id();
            name = ug.get_userName();
            second_name = ug.get_userSurname();

            name = name + " " + second_name;
            itemUserGroupArrayList.add(new ItemUserGroup(R.drawable.ic_android, name));
        }

    }

    private void buildRecyclerViewUsersGroup() {
        recyclerView_usersGroup = findViewById(R.id.recyclerView_row_user_group);
        recyclerView_usersGroup.setHasFixedSize(true);
        layoutManager_usersGroup = new LinearLayoutManager(this);
        usersGroupAdapter = new UsersGroupAdapter(itemUserGroupArrayList);

        recyclerView_usersGroup.setLayoutManager(layoutManager_usersGroup);
        recyclerView_usersGroup.setAdapter(usersGroupAdapter);
    }

    class InsertUser extends AsyncTask<Void, Void, Void> {
        String resultString = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String myURL = "http://" + server_name + "/insert_user.php?email=" + email + "&group_id=" + group_id;
                String parammetrs = "/group.php?id=null&email=" + email + "&group_id=" + group_id;
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
}
