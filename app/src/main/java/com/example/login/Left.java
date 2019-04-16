package com.example.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Left extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment = null;
    private FragmentManager fragmentManager;

    //объявляем fab
    ImageButton floatButton;
    //объявляем обновление списка диалогов
    SwipeRefreshLayout swipeRefreshLayout;

    int number = 0;

    protected String user_id, name, second_name, email, password, birthday_date;
    protected String name_group;

    TextView textView_name;
    TextView textView_email;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left);

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

        user_id = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        second_name = getIntent().getStringExtra("second_name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        birthday_date = getIntent().getStringExtra("birthday_date");

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

                        try{
                            new CreateGroup().execute();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        dialog.dismiss();
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
        displayView(0, getResources().getString(R.string.app_name));

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

        } else if (id == R.id.them) {

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

        }

        displayView(0,getResources().getString(R.string.app_name));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayView(int position, String title) {
        // update the main content by replacing fragments
        fragment = null;
        String fragmentTags = "";
        switch (position) {
            case 0:
                fragment = new RecyclerViewFragment();
                break;


            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragmentTags).commit();
            getSupportActionBar().setTitle(title);
        }
    }

    class CreateGroup extends AsyncTask<Void, Void, Void> {
        String resultString = null;

        public String server_name = "message.dlinkddns.com:8008";

        //protected String name_group, user_id;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String myURL = "http://" + server_name + "/group.php?id=null&name_group=" + name_group + "&admin_user_id=" + user_id + "&group_icon_id=1";
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
}

