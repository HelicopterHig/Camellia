package com.example.login;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateCheckedNote {
    public static String server_name = "message.dlinkddns.com:8008";

    private int note_id;
    private int val;

    public void UpdateCheckNoteVoid(int id_note, int true_or_false){
        note_id = id_note;
        if (true_or_false == 0){
            val = 0;
        }else {
            val = 1;
        }
        try{
            new UpdateCheckNote().execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class UpdateCheckNote extends AsyncTask<Void, Void, Void> {
        String resultString = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String myURL = "http://" + server_name + "/update_check_note.php?note_id=" + note_id + "&val=" + val;
                String parammetrs = "?note_id=" + note_id;
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
