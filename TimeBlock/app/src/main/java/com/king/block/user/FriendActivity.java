package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.king.block.Global;
import com.king.block.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {
    View view;
    private ListView friend_lv;
    private FriendAdapter friendAdapter;
    public static List<Friend> friend_list = new ArrayList<>();
    Global global;

    ImageView add;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        global = (Global)getApplication();
        initTop();
        initEvent();
        initData();
        initLv();
    }


    private void initData() {
        try {
            URL url = new URL(global.getURL() + "/friend/query");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "user_id:" + global.getUserId();
//            String content = "{\"user_id\":\"" + global.getUserId() + "\"}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.getInt("code");
                String msg = res.getString("msg");
                if (code == 200) {
                    JSONArray friends = res.getJSONArray("data");
                    for (int i = 0; i < friends.length(); i++) {
                        JSONObject jo = friends.getJSONObject(i);
                        Friend f = new Friend(jo.getInt("id"), jo.getString("name"), jo.getString("avatar"), jo.getString("plan_time"));
                        friend_list.add(f);
                    }
                } else {
                    Toast.makeText(FriendActivity.this, msg, Toast.LENGTH_SHORT).show();
                    input.setText(res.getString("err"));
                }
            } else {
                Toast.makeText(FriendActivity.this, con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FriendActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }

    }

    private void initLv(){
        friendAdapter = new FriendAdapter(FriendActivity.this, R.layout.item_friend, friend_list);
        friend_lv =(ListView)findViewById(R.id.friend_lv);
        friend_lv.setAdapter(friendAdapter);
//        friendAdapter.setListView(friend_lv);
        friend_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(FriendActivity.this)
                        .setTitle("提示")
                        .setMessage("确定删除好友"+friend_list.get(position).getName()+"?")
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int del_id = friend_list.get(position).getId();
                                friend_list.remove(position);
//                                friendAdapter.setListView(friend_lv);
                                friendAdapter = new FriendAdapter(FriendActivity.this, R.layout.item_friend, friend_list);
                                friend_lv.setAdapter(friendAdapter);
//                               未完成-删除数据库
//                                int delete = DataSupport.deleteAll(Ddl.class, "task = ? and content = ?", del1, del2);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                Button pButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                pButton.setTextColor(Color.rgb(240,60,80));
                nButton.setTextColor(Color.GRAY);
            }
        });
    }

    private void initTop(){
        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendActivity.this.finish();
            }
        });
    }

    private void initEvent(){
        add = (ImageView)findViewById(R.id.add);
        input = (EditText)findViewById(R.id.input);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}