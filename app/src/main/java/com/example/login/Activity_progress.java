package com.example.login;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.User_group;

import java.util.ArrayList;
import java.util.List;

public class Activity_progress extends Base_Activity {

    private RecyclerView recyclerView_Progress;
    private ProgressAdapter progressAdapter;
    private RecyclerView.LayoutManager layoutManager_Progress;
    private ArrayList<ItemProgress> itemProgressArrayList;

    public String email, name, second_name, name_user;
    int group_id, icon;

    ImageButton imageButton;

    EditText editText;

    SharedPref sharedpref;

    DatabaseHandler db;
    CheckIcon checkIcon;
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
        setContentView(R.layout.activity_progress);

        db = new DatabaseHandler(this);
        checkIcon = new CheckIcon();

        createListUsersGroup();
        buildRecyclerViewUsersGroup();

    }

    private void createListUsersGroup() {
        itemProgressArrayList = new ArrayList<>();

        user_groups = db.getAllUser_groups();

        for (User_group ug : user_groups){
            group_id = ug.get_group_id();
            name = ug.get_userName();
            second_name = ug.get_userSurname();
            icon = checkIcon.checkIconUser(ug.get_icon_id());
            name = name + " " + second_name;
            itemProgressArrayList.add(new ItemProgress(icon, name, 14, 8)); //Допилить прогресс
        }

    }

    private void buildRecyclerViewUsersGroup() {
        recyclerView_Progress = findViewById(R.id.recyclerView_row_progress);
        recyclerView_Progress.setHasFixedSize(true);
        layoutManager_Progress = new LinearLayoutManager(this);
        progressAdapter = new ProgressAdapter(itemProgressArrayList);

        recyclerView_Progress.setLayoutManager(layoutManager_Progress);
        recyclerView_Progress.setAdapter(progressAdapter);

        //swipeToDeleteHelper.attachToRecyclerView(recyclerView_usersGroup);
    }
}
