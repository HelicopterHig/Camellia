package com.example.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.LocalDataBase.DatabaseHandler;
import com.example.login.LocalDataBase.Note;
import com.example.login.LocalDataBase.UNote;
import com.example.login.LocalDataBase.User;
import com.example.login.LocalDataBase.User_group;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteEditActivity extends AppCompatActivity {
    private EditText inputNote;
    public Note temp;
    String text;
    private String notename;
    int noteID;
    DatabaseHandler db;
    String name, surname, datenote, email;
    int id, icon;

    int group_id, note_id;

    public static String server_name = "message.dlinkddns.com:8008";

    private Date date_now;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        db = new DatabaseHandler(this);
        Toolbar toolbar = findViewById(R.id.edit_note_activity_toolbar);
        setSupportActionBar(toolbar);
        notename = getIntent().getStringExtra("notename");
        noteID = getIntent().getIntExtra("finalnoteID", -1);
        inputNote = findViewById(R.id.input_note);

        List<User_group> user_groupList = db.getAllUser_groups();

        for (User_group ug : user_groupList){
            group_id = ug.get_group_id();
        }

        List<Note> noteList = db.getAllNotes();

        for (Note nt : noteList){
            note_id = nt.get_noteID();
        }

        date_now = new Date();

        if (noteID !=-1) {
            temp = new Note();
            temp = db.getNote(noteID);
            inputNote.setText(temp.get_description());
        }
        else {
            inputNote.setFocusable(true);
        }

    }
    public void openTskActivity(){
        Intent intent  = new Intent(this,Activity_Note.class );
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note_from_menu){
            onSaveNote();
        }

        return super.onOptionsItemSelected(item);
    }


    private void onSaveNote() {

        System.out.println(noteID);
        text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            datenote = df.format(c);
            List<User> list = db.getAllContacts();
            for (User ug: list){
                id = ug.getID();
                name = ug.getName();
                surname = ug.getSecName();
                email = ug.getMail();
                icon = ug.getIcon();
            }

            if  (temp == null) {
                temp = new Note(note_id, notename, datenote, text, 0, id, group_id, name, surname, email, icon); //Добавить параметры
                db.addNote(temp);
                System.out.println("Reading all notes..");
                List<Note> note_local = db.getAllNotes();

                for (Note cn : note_local) {
                    String log = "Id: " + cn.get_id() + " , NoteID: " + cn.get_noteID() + " , Name: " + cn.get_name() + " , Date: " + cn.get_date()
                            + ", Description: " + cn.get_description() + ", Done: " + cn.get_done()
                            + ", UserID: " + cn.get_userID() + ", GroupID: " + cn.get_groupID();

                    System.out.print("Note: ");
                    System.out.println(log);
                }

                try{
                    new InsertNote().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

            } else {
                temp.set_description(text);
                temp.set_date(datenote);
                db.updateNote(temp);
            }

            Toast.makeText(NoteEditActivity.this,
                    R.string.save_note,
                    Toast.LENGTH_SHORT).show();
            openTskActivity();
        }

    }

    class InsertNote extends AsyncTask<Void, Void, Void> {
        String resultString = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String myURL = "http://" + server_name + "/insert_new_note.php?name=" + notename + "&date=" + datenote + "&discription=" + text + "&done=" + false + "&user_id=" + id + "&group_id=" + group_id;
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
