package com.example.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Activity_Note extends Base_Activity {
    ImageButton floatButton;
    protected String name_note;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        floatButton = (ImageButton) findViewById(R.id.imageButton_insert_note);
        floatButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Activity_Note.this);
                final View mView = getLayoutInflater().inflate(R.layout.create_note, null);
                editText = (EditText) mView.findViewById(R.id.NameNote);
                Button mLogin = (Button) mView.findViewById(R.id.NoteCreate);
                Button nLogin = (Button) mView.findViewById(R.id.CancelNote);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        name_note = String.valueOf(editText.getText().toString());



                        dialog.dismiss();
                        openEditNoteActivity();

//                        insertItem(position, name_group);
                    }
                });
                nLogin.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });


            }

        });
    }
    public void openEditNoteActivity(){
        Intent intent  = new Intent(this, NoteEditActivity.class);
        startActivity(intent);
    }
}




/*public class Activity_Note extends AppCompatActivity {
    SharedPref sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);*/
      /*  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_menu, menu);
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
       // if (id == R.id.){
       //     Intent intent = new Intent(this, Activity_Note.class);
       //     startActivity(intent);
       // }

        return super.onOptionsItemSelected(item);
    }
}*/
