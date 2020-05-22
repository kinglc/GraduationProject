package com.king.block.content;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.king.block.Global;
import com.king.block.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TodoFragment extends Fragment{
    View view;
    Global global;
    private ListView todo_lv;
    private TodoAdapter todoAdapter;
    public static List<Todo> todo_list = new ArrayList<>();

    private TextView date;
    public int year, month, day;
    private ImageView menu;
    private EditText input;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);

        global = (Global)getActivity().getApplication();
        initEvent();
        initDate();
        initLv();
        getTodo(year + "-" + month + "-" + day);

        return view;
    }

    //调用接口
    //获取当日待办
    private void getTodo(String date){
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        try {
            d = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int now = cmpDate(d);
        try {
            URL url = new URL(global.getURL() + "/todo/query");
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
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"date\":\""+date+"\"}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONArray todos = res.getJSONArray("data");
                    for(int i=0;i<todos.length();i++){
                        JSONObject todo = todos.getJSONObject(i);
                        todo_list.add(new Todo(todo.getInt("todo_id"),todo.getString("title"),
                                todo.getInt("isChecked")==1,todo.getString("date")));
                        if(now==0&&todo.getInt("isChecked")==0){
                            todoAdapter.setNotfinish(todoAdapter.getNotfinish()+1);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "刷新待办信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }

    }

    //删除
    private void deleteTodo(int del_id){
        try {
            URL url = new URL(global.getURL() + "/todo/delete");
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
            String content = "{\"todo_id\":" + del_id + "}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "刷新待办信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            input.setText(e.toString());
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //添加
    private void addTodo(){
        try {
            URL url = new URL(global.getURL() + "/todo/add");
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
            String content = "{\"user_id\":\"" + global.getUserId() +
                    "\",\"title\":\""+input.getText()+
                    "\",\"date\":\""+year+"-"+month+"-"+day+"\"}";
            out.writeBytes(content);
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    int id = res.optInt("insertId");
                    todo_list.add(new Todo(id,input.getText().toString(),false, year+"-"+month+"-"+day));
                    todoAdapter.notifyDataSetChanged();
                    input.setText("");
                    input.clearFocus();
                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "刷新待办信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            input.setText(e.toString());
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    private int cmpDate(Date d){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int nowd = Integer.parseInt(sdf.format(now));
        int dd = Integer.parseInt(sdf.format(d));
        if (dd< nowd) {
            return -1;
        } else if (dd > nowd) {
            return 1;
        }
        return 0;
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
        todoAdapter.setGlobal(global);
        todo_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(!todoAdapter.isClickable()) return;
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定删除此待办项？")
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean isChecked = todo_list.get(position).isChecked();
                                int del_id = todo_list.get(position).getId();
                                deleteTodo(del_id);
                                todo_list.remove(position);
                                todoAdapter.notifyDataSetChanged();
                                if (!isChecked) {
                                    todoAdapter.setNotfinish(todoAdapter.getNotfinish() - 1);
                                }
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
        final LinearLayout todo = (LinearLayout)view.findViewById(R.id.todo);
        final LinearLayout add_layout = (LinearLayout)view.findViewById(R.id.add_layout);
        input=(EditText)view.findViewById(R.id.input);

        date = (TextView) view.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {      //  日期选择对话框
                    @Override
                    public void onDateSet(DatePicker view, final int year, int month, final int dayOfMonth) {
                        month++;
                        String dateString = year + "-" + month + "-" + dayOfMonth;
                        Date d = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
                        try {
                            d = simpleDateFormat.parse(dateString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date.setText(dateString + "  ▼");
                        int cmp = cmpDate(d);
                        if (cmp==1) {
                            todo.setVisibility(View.GONE);
                            add_layout.setVisibility(View.GONE);
                            return ;
                        } else if (cmp==-1) {
                            todo.setVisibility(View.VISIBLE);
                            todoAdapter.setClickable(false);
                            add_layout.setVisibility(View.GONE);
                        } else {
                            todo.setVisibility(View.VISIBLE);
                            todoAdapter.setClickable(true);
                            add_layout.setVisibility(View.VISIBLE);
                        }
                        todo_list.clear();
                        getTodo(year + "-" + month + "-" + dayOfMonth);
                        todoAdapter.notifyDataSetChanged();
                    }
                }, year, month - 1, day).show();   //  弹出日历对话框时，默认显示 年，月，日
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
                if(input.getText().length()>0){
                    addTodo();
                    todoAdapter.setNotfinish(todoAdapter.getNotfinish()+1);
                }else{
                    Toast.makeText(getContext(),"请输入内容",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
