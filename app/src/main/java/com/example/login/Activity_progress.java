package com.example.login;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.Note;
import com.example.login.LocalDataBase.User_group;

import java.util.ArrayList;
import java.util.List;

public class Activity_progress extends Base_Activity {

    private RecyclerView recyclerView_Progress;
    private ProgressAdapter progressAdapter;
    private RecyclerView.LayoutManager layoutManager_Progress;
    private ArrayList<ItemProgress> itemProgressArrayList;

    public String email, name, second_name, name_user;
    int group_id, icon, count = 0, count_true = 0;

    ImageButton imageButton;

    EditText editText;

    SharedPref sharedpref;

    DatabaseHandler db;
    CheckIcon checkIcon;
    List<User_group> user_groups;
    List<Note> noteList;
    List<Note> noteList2;

    private int size_note = 0;

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

        size_note = db.getNoteCount();

        createListUsersGroup();
        buildRecyclerViewUsersGroup();

    }

    private void createListUsersGroup() {
        itemProgressArrayList = new ArrayList<>();

        user_groups = db.getAllUser_groups();

        for (User_group ug : user_groups){
            count = 0;
            count_true = 0;
            group_id = ug.get_group_id();
            name = ug.get_userName();
            second_name = ug.get_userSurname();
            icon = checkIcon.checkIconUser(ug.get_icon_id());
            name = name + " " + second_name;

            noteList = db.getAllNotes();
            for (Note nt : noteList){
                if (nt.get_userID() == ug.get_user_id()){
                    count++;
                }
            }

            noteList2 = db.getAllNotes();
            for(Note nt2 : noteList2){
                if (nt2.get_done() == 1){
                    count_true++;
                }
            }

            itemProgressArrayList.add(new ItemProgress(icon, name, count, count_true)); //Допилить прогресс
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
