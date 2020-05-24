package com.king.block.content;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.king.block.Global;
import com.king.block.R;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment{
    View view;
    private ListView plan_lv;
    private PlanAdapter planAdapter;
    public static List<Plan> plan_list = new ArrayList<>();
    private Plan plan_now=null;

    TextView plan_title;
    TextView plan_content;
//    TextView plan_ddl;
    ImageView select;
    private Chronometer pass;
    int on = 0;//1-暂停 0-计时
    private long nowtime=0;

    ImageView add;
    private ImageView menu;
    Global global;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        global = (Global)getActivity().getApplication();

        getPlan();
        initLv();
        initEvent();
        setNow();

        return view;
    }

    private void initLv(){
        planAdapter = new PlanAdapter(getActivity(), R.layout.item_plan, plan_list);
        plan_lv =(ListView) view.findViewById(R.id.plan_lv);
        plan_lv.setAdapter(planAdapter);
        planAdapter.setListView(plan_lv);
        planAdapter.setGlobal(global);
        planAdapter.setList(plan_list);
        plan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                plan_now = getNow();
                plan_now = plan_list.get(position);
                setNow();
            }
        });
    }

    private void initEvent() {
        plan_title = (TextView)view.findViewById(R.id.plan_title);
        plan_content = (TextView)view.findViewById(R.id.plan_content);
//        plan_ddl = (TextView)view.findViewById(R.plan_id.plan_ddl);
        pass = (Chronometer) view.findViewById(R.id.plan_pass);
        pass.setFormat("%s");

        menu = (ImageView) view.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout dl = (DrawerLayout) getActivity().findViewById(R.id.drawerLayout);
                dl.openDrawer(Gravity.RIGHT);
            }
        });

        add= (ImageView) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), PlanActivity.class);
                getContext().startActivity(it);
            }
        });

        select = (ImageView)view.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(plan_now==null) return;
                if(on==1){
                    pass.setBase(pass.getBase() + (SystemClock.elapsedRealtime() - nowtime));
                    startTime();
                }else{
                    stopTime();
                    nowtime=SystemClock.elapsedRealtime();
                }
                on=1-on;
            }
        });
    }

    private void startTime(){
        select.setImageResource(R.drawable.pause);
        pass.start();
    }

    private void stopTime(){
        select.setImageResource(R.drawable.on);
        pass.stop();
    }

    private void setNow(){
        if(plan_now!=null){
            plan_title.setText(plan_now.getTitle());
            plan_content.setText(plan_now.getContent());
//            plan_ddl.setText(plan_now.getDate()+" "+plan_now.getTime());
//            plan_pass.setText(plan_now.getPass());
            //未完成-获取on状态
            on=1;
            stopTime();
            nowtime = SystemClock.elapsedRealtime();
            pass.setBase(nowtime-plan_now.getId()*1000);
            switch (plan_now.getUrgency()){
                case 0:
                    pass.setTextColor(getContext().getResources().getColor(R.color.red));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.red));
                    break;
                case 1:
                    pass.setTextColor(getContext().getResources().getColor(R.color.yellow));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.yellow));
                    break;
                case 2:
                    pass.setTextColor(getContext().getResources().getColor(R.color.green));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.green));
                    break;
                case 3:
                    pass.setTextColor(getContext().getResources().getColor(R.color.blue));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.blue));
                    break;
                default:break;
            }
        }

    }

    //调用接口
    //获取
    private void getPlan() {
        plan_list.clear();
        try {
            URL url = new URL(global.getURL() + "/plan/query");
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
                    JSONArray plans = res.getJSONArray("data");
                    for (int i = 0; i < plans.length(); i++) {
                        JSONObject plan = plans.getJSONObject(i);
                        plan_list.add(new Plan(plan.getInt("plan_id"), plan.getString("title"), plan.getString("content"),
                                plan.getInt("urgency"), plan.getString("pass")));
                    }
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "刷新计划信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //进行
    private void pass(int plan_id,String pass){
        try {
            URL url = new URL(global.getURL() + "/plan/delete");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"plan_id\":" + plan_id  + ",\"pass\":\""+pass+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
//                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "删除失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }
}

