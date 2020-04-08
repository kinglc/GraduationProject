package com.king.block.content;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.king.block.R;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    View view;
    private ListView todo_lv;
    private TodoAdapter todoAdapter;
    public static List<Todo> todo_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);

        initData();
        initLv();
        initEvent();


        return view;
    }

    private void initData(){
        //未完成 获取数据
        for(int i=0;i<10;i++) {
            Todo todo = new Todo(i,i+""+i+i+i+i, i%2==0);
            todo_list.add(todo);
        }
    }

    private void initLv(){
        todoAdapter = new TodoAdapter(getActivity(), R.layout.item_todo, todo_list);
        todo_lv =(ListView) view.findViewById(R.id.todo_lv);
        todo_lv.setAdapter(todoAdapter);
        todoAdapter.setListView(todo_lv);
        todo_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定删除此待办项？")
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int del_id = todo_list.get(position).getId();
                                todo_list.remove(position);
//                                todoAdapter.setListView(todo_lv);
                                todoAdapter.turnOutEdit();
                                todoAdapter = new TodoAdapter(getActivity(), R.layout.item_todo, todo_list);
                                todo_lv.setAdapter(todoAdapter);
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


    private void initEvent(){
        ImageView repeat = (ImageView)view.findViewById(R.id.todo_repeat);
        ImageView share = (ImageView)view.findViewById(R.id.todo_share);
        ImageView insert = (ImageView)view.findViewById(R.id.todo_insert);


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//  未完成-重复当日
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//  未完成-图片形式分享
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = (TextView)view.findViewById(R.id.todo_addinput);
//  未完成-提交数据
            }
        });
    }
    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }
}
