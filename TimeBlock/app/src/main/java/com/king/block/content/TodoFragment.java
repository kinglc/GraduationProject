package com.king.block.content;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.king.block.ContentActivity;
import com.king.block.Global;
import com.king.block.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TodoFragment extends Fragment {
    View view;
    Global global;
    private ListView todo_lv;
    private TodoAdapter todoAdapter;
    public static List<Todo> todo_list = new ArrayList<>();

    private TextView date;
    public int year, month, day;
    private ImageView menu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);

        global = (Global)getActivity().getApplication();
        initData();
        initLv();
        initEvent();
        initDate();

        Toast.makeText(getContext(),""+date,Toast.LENGTH_SHORT).show();
        return view;
    }


    //调用接口
    //获取当日待办
    private void getTodo(){

    }

    private void initData(){
        //未完成 获取数据
        for(int i=0;i<10;i++) {
            Todo todo = new Todo(i,i+""+i+i+i+i, i%2==0,"aa");
            todo_list.add(todo);
        }
    }

    private void initDate(){
        Calendar mcalendar = Calendar.getInstance();     //  获取当前时间    —   年、月、日
        year = mcalendar.get(Calendar.YEAR);         //  得到当前年
        month = mcalendar.get(Calendar.MONTH)+1;       //  得到当前月
        day = mcalendar.get(Calendar.DAY_OF_MONTH);  //  得到当前日
        date.setText(year + "-" + month + "-" + day + "  ▼");
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

        date = (TextView) view.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {      //  日期选择对话框
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //  这个方法是得到选择后的 年，月，日，分别对应着三个参数 — year、month、dayOfMonth
                        month++;
                        date.setText(year + "-" + month + "-" + dayOfMonth + "  ▼");
                    }
                }, year, month-1, day).show();   //  弹出日历对话框时，默认显示 年，月，日
            }
        });

        menu = (ImageView) view.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout dl = (DrawerLayout) getActivity().findViewById(R.id.drawerLayout);
                dl.openDrawer(Gravity.RIGHT);
            }
        });

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
                Todo todo = new Todo(111,title.getText().toString(),false,"aa");
                todo_list.add(todo);
//  未完成-提交数据
            }
        });
    }

}
