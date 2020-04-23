package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.king.block.R;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {
    View view;
    private ListView friend_lv;
    private FriendAdapter friendAdapter;
    public static List<Friend> friend_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        initData();
        initTop();
        initLv();
    }

    private void initData(){
        //未完成 获取数据
        for(int i=0;i<10;i++) {
            Friend friend = new Friend(i,i+""+i+i+i+i, "123456");
            friend_list.add(friend);
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
}