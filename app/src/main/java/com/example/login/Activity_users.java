package com.example.login;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_users extends Base_Activity {
    private RecyclerView recyclerView_usersGroup;
    private UsersGroupAdapter usersGroupAdapter;
    private RecyclerView.LayoutManager layoutManager_usersGroup;
    private ArrayList<ItemUserGroup> itemUserGroupArrayList;

    public String email, name, second_name, name_user;

    ImageButton imageButton;

    EditText editText;

    SharedPref sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

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

        itemUserGroupArrayList.add(new ItemUserGroup(R.drawable.ic_android, "dnsmnd"));
    }

    private void buildRecyclerViewUsersGroup() {
        recyclerView_usersGroup = findViewById(R.id.recyclerView_row_user_group);
        recyclerView_usersGroup.setHasFixedSize(true);
        layoutManager_usersGroup = new LinearLayoutManager(this);
        usersGroupAdapter = new UsersGroupAdapter(itemUserGroupArrayList);

        recyclerView_usersGroup.setLayoutManager(layoutManager_usersGroup);
        recyclerView_usersGroup.setAdapter(usersGroupAdapter);
    }
}
