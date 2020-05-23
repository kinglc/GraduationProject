package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.king.block.Global;
import com.king.block.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class AchieveActivity extends AppCompatActivity {

    private GridView gv;
    private AchieveAdapter achieveAdapter;
    private ArrayList<Achieve> achieve_list = null;
    Global global;
    int plan=14,todo=13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        global = (Global)getApplication();
        initTop();
        initData();
        initGv();
    }

    private void initTop() {
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                achieve_list.clear();
                AchieveActivity.this.finish();
            }
        });
    }

    //调用接口
    //通过user_id查询成就信息
    private void getInfo() {
        try {
            URL url = new URL(global.getURL() + "/achieve/query");
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
//            String content = "user_id:" + global.getUserId();
            String content = "{\"user_id\":\"" + global.getUserId() + "\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONObject prize = res.getJSONArray("data").getJSONObject(0);
                    plan=prize.getInt("prize_plan");
                    todo = prize.getInt("prize_todo");
                } else {
                    Toast.makeText(AchieveActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AchieveActivity.this, "刷新成就信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AchieveActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        achieve_list = new ArrayList<Achieve>();
        getInfo();
        int maxi = Math.max(plan,todo);
        int mini  = Math.min(plan,todo);
        for (int i = maxi; i >mini; i-=2) {
            achieve_list.add(global.getAchieve().get(i-1));
        }
        for (int i = mini; i >0; i--) {
            achieve_list.add(global.getAchieve().get(i-1));
        }
    }

    private void initGv() {
        gv = (GridView) findViewById(R.id.achieve_gv);
        achieveAdapter = new AchieveAdapter(AchieveActivity.this, R.layout.item_achieve, achieve_list);
        gv.setAdapter(achieveAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AchieveActivity.this, achieve_list.get(position).getNote(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
