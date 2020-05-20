package com.king.block;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Global extends Application {
    private String userId;
    private String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate() {
        userId = "jc";
        URL = "http://127.0.0.1:3000";
        super.onCreate();
    }

    public JSONObject streamtoJson(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line, s="";
        JSONObject result = null;
        while ((line = reader.readLine()) != null) {
            s+=line;
        }
        reader.close();
        try {
            result = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int countTime(String time) {
        int min = 0;
        time= time.substring(0, time.length() - 1);
        if (time.indexOf('h') != -1) {
            min = Integer.parseInt(time.split("h")[0]);
            time = time.substring(time.indexOf('h')+1);
        }
        min+=Integer.parseInt(time);
        return min;
    }
}
