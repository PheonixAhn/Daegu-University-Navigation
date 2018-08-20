package com.example.pheonix.daeguuniversitynavigation;


import android.os.AsyncTask;
import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skadl on 2018-05-24.
 */

//ウェブサーバとHttp通信をする「Class」
    // +) このアプリでは公共バース情報を使うために利用する
public class MyAsyncTask extends AsyncTask<String, Void, String> {

    String IP;
    String returnValue;
    BufferedReader br;
    MyAsyncTask(String ip){
        IP = ip;
    }

    @Override
    public String doInBackground(String... params) {
        try {
            String url = IP;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

            writer.flush();

            int retCode = conn.getResponseCode();

            InputStream is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "euc-kr"));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            br.close();

            returnValue = response.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }
}