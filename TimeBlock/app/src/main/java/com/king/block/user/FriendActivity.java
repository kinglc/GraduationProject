package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FriendActivity extends AppCompatActivity {
    View view;
    private ListView friend_lv;
    private FriendAdapter friendAdapter;
    public static List<Friend> friend_list = new ArrayList<>();
    Global global;

    ImageView add;
    EditText input;
    private String ids = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        global = (Global) getApplication();
        initTop();
        initEvent();
        initData();
        initLv();
    }

    //接口
    //调用添加或删除好友
    private boolean updateFriend(String user_id, String ids, String type) {
        try {
            URL url = new URL(global.getURL() + "/friend/update");
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
            String content = "{\"user_id\":\"" + user_id + "\",\"ids\":\"" + ids + "\"}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    return true;
                } else {
                    Toast.makeText(FriendActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FriendActivity.this, type+"好友失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FriendActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //通过ids获取好友信息
    private void getInfo(String ids) {
        if (ids == "") return;
        try {
            URL url = new URL(global.getURL() + "/friend/getInfo");
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
            String content = "{\"ids\":\"" + ids.substring(0,ids.length()-1) + "\"}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONArray friends = res.getJSONArray("data");
                    for (int i = 0; i < friends.length(); i++) {
                        JSONObject jo = friends.getJSONObject(i);
                        Friend f = new Friend(jo.getString("user_id"), jo.getString("name"), jo.getString("avatar"), jo.getString("plan_time"));
                        friend_list.add(f);
                    }
                } else {
                    Toast.makeText(FriendActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FriendActivity.this, "刷新好友信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FriendActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
        Collections.sort(friend_list, new Comparator<Friend>() {
            @Override
            public int compare(Friend o1, Friend o2) {
                return global.countTime(o2.getTime())-global.countTime(o1.getTime());
            }
        });
    }

    //通过user_id获取ids
    private String getIds(String user_id) {
        try {
            URL url = new URL(global.getURL() + "/friend/query");
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
            String content = "{\"user_id\":\"" + user_id + "\"}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    return res.getJSONArray("data").getJSONObject(0).getString("friend");
                } else {
                    Toast.makeText(FriendActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FriendActivity.this, "获取好友列表失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FriendActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
        return "error";
    }

    //判断id是否存在
    //200 存在
    //201 不存在
    private int isExist(String id) {
        try {
            URL url = new URL(global.getURL() + "/user/isExist");
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
            String content = "{\"id\":\"" + id + "\"}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                return res.optInt("code");
            } else {
                Toast.makeText(FriendActivity.this, "获取好友列表失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FriendActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
        return 404;
    }


    private void initData() {
        ids = getIds(global.getUserId());
        if (ids != "error") {
            getInfo(ids);
        }
    }

    private void initLv() {
        friendAdapter = new FriendAdapter(FriendActivity.this, R.layout.item_friend, friend_list);
        friend_lv = (ListView) findViewById(R.id.friend_lv);
        friend_lv.setAdapter(friendAdapter);
        friend_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(FriendActivity.this)
                        .setTitle("提示")
                        .setMessage("确定删除好友" + friend_list.get(position).getName() + "?")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String del_id = friend_list.get(position).getId();
                                ids = ids.replace("'" + del_id + "',", "");
                                updateFriend(global.getUserId(), ids, "删除");
                                updateFriend(del_id, getIds(del_id).replace("'" + global.getUserId() + "',", ""), "删除");
                                Toast.makeText(FriendActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                friend_list.remove(position);
                                friendAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                Button pButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                pButton.setTextColor(Color.rgb(240, 60, 80));
                nButton.setTextColor(Color.GRAY);
            }
        });
    }

    private void initTop() {
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friend_list.clear();
                FriendActivity.this.finish();
            }
        });
    }

    private void initEvent() {
        add = (ImageView) findViewById(R.id.add);
        input = (EditText) findViewById(R.id.input);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendid = input.getText().toString();
                if(ids.contains("'" + friendid + "',")){
                    Toast.makeText(FriendActivity.this, "该用户已存在列表中", Toast.LENGTH_SHORT).show();
                }else if (friendid.length() > 0) {
                    int exist = isExist(friendid);
                    if (exist == 200) {
                        ids=ids + "'" + friendid + "',";
                        updateFriend(global.getUserId(), ids,"添加");
                        updateFriend(friendid, getIds(friendid)+"'" + global.getUserId() + "'," ,"添加");
                        Toast.makeText(FriendActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        getInfo("'"+friendid+"',");
                        friendAdapter.notifyDataSetChanged();
                    }else if (exist == 201) {
                        Toast.makeText(FriendActivity.this, "该id 不存在", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FriendActivity.this, "请输入好友id", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}