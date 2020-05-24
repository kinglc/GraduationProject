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
//    TextView plan_ddl;
    ImageView select;
    private Chronometer pass;
    int on = 0;//1-暂停 0-计时
    private long nowtime=0;

    ImageView add;
    private ImageView menu;

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
                    i%4,"00:00:0");
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

    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }



}

