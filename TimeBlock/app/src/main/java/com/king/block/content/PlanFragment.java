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

import com.king.block.R;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
    TextView plan_ddl;
    TextView plan_pass;
    ImageView select;
    int on;//0-暂停 1-计时
    private Chronometer timer;
    private long baseTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);

        initData();
        initLv();
        initEvent();
        setNow();


        return view;
    }

    private void initData(){
        //未完成 获取数据
        for(int i=0;i<10;i++) {
            Plan plan = new Plan(i,i+""+i+i+i+i, "间关莺语花底滑，幽咽泉流冰下难",
                    i%4,"00:0"+i,"-1","2011-01-02","01:01");
            plan_list.add(plan);
        }
    }

    private void initLv(){
        planAdapter = new PlanAdapter(getActivity(), R.layout.item_plan, plan_list);
        plan_lv =(ListView) view.findViewById(R.id.plan_lv);
        plan_lv.setAdapter(planAdapter);
        planAdapter.setListView(plan_lv);
        planAdapter.setList(plan_list);
        plan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                plan_now = getNow();
                plan_now = plan_list.get(position);
                setNow();;
            }
        });
    }

    public void startTimer() {
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 60);
        timer.setFormat("0"+String.valueOf(hour)+":%s");
        timer.setBase(SystemClock.elapsedRealtime()-300*1000);//从5分钟开始计时
        timer.start();//开启计时
    }

    private void initEvent() {
        plan_title = (TextView)view.findViewById(R.id.plan_title);
        plan_content = (TextView)view.findViewById(R.id.plan_content);
        plan_ddl = (TextView)view.findViewById(R.id.plan_ddl);
        plan_pass = (TextView)view.findViewById(R.id.plan_pass);
        select = (ImageView)view.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOn();
            }
        });


    }

    private void changeOn(){
        if(plan_now==null) return;
        if(on==0){
            select.setImageResource(R.drawable.pause);
            on = 1;
        }else{
            select.setImageResource(R.drawable.on);
            on = 1;
        }
    }

    private void setNow(){
        //未完成-获取on状态
        on=1;
        changeOn();

        if(plan_now!=null){
            plan_title.setText(plan_now.getTitle());
            plan_content.setText(plan_now.getContent());
            plan_ddl.setText(plan_now.getDate()+" "+plan_now.getTime());
            plan_pass.setText(plan_now.getPass());
            switch (plan_now.getUrgency()){
                case 0:
                    plan_pass.setTextColor(getContext().getResources().getColor(R.color.red));
                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.red));
                    break;
                case 1:
                    plan_pass.setTextColor(getContext().getResources().getColor(R.color.yellow));
                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.yellow));
                    break;
                case 2:
                    plan_pass.setTextColor(getContext().getResources().getColor(R.color.green));
                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.green));
                    break;
                case 3:
                    plan_pass.setTextColor(getContext().getResources().getColor(R.color.blue));
                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.blue));
                    break;
                default:break;
            }
        }
    }

    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }



}

