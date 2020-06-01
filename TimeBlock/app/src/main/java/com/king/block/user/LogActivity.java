package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.king.block.Global;
import com.king.block.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    View view;
    Global global;
    private ListView log_lv;
    private LogAdapter logAdapter;
    public static List<Log> log_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        global = (Global)getApplication();
        initTop();
        getLog();
        initLv();
    }

    private void initTop() {
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_list.clear();
                LogActivity.this.finish();
            }
        });
    }

    //调用接口
    //获取数据
    private void getLog() {
        try {
            URL url = new URL(global.getURL() + "/log/query");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"user_id\":\"" + global.getUserId() +"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONArray logs = res.getJSONArray("data");
                    for(int i=0;i<logs.length();i++){
                        JSONObject log = logs.getJSONObject(i);
                        log_list.add(new Log(log.getInt("log_id"),log.getInt("type"),log.getString("date"), log.getString("name")));
                    }
                } else {
                    Toast.makeText(LogActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LogActivity.this, "获取失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LogActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void initLv() {
        logAdapter = new LogAdapter(LogActivity.this, R.layout.item_log, log_list);
        log_lv = (ListView) findViewById(R.id.log_lv);
        log_lv.setAdapter(logAdapter);
    }
}