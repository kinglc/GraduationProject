package com.king.block.content;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.king.block.R;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment{
    View view;
    private ListView plan_lv;
    private PlanAdapter planAdapter;
    public static List<Plan> plan_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        
        initData();
        initLv();
        initEvent();
        
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
        plan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        plan_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("对此待办项进行？")
                        .setPositiveButton("删除",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int del_id = plan_list.get(position).getId();
                                plan_list.remove(position);
//                                planAdapter.setListView(plan_lv);
                                planAdapter = new PlanAdapter(getActivity(), R.layout.item_plan, plan_list);
                                plan_lv.setAdapter(planAdapter);
//                               未完成-删除数据库
//                                int delete = DataSupport.deleteAll(Ddl.class, "task = ? and content = ?", del1, del2);
                            }
                        })
                        .setNeutralButton("编辑", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//未完成-bug
                                Plan plan = plan_list.get(position);
                                Intent it = new Intent(getContext(), PlanActivity.class);
                                it.putExtra("id",plan.getId());
                                it.putExtra("plan_title",plan.getTitle());
                                it.putExtra("plan_content",plan.getContent());
                                it.putExtra("urgency",plan.getUrgency());
                                it.putExtra("plan_date",plan.getDate());
                                it.putExtra("plan_time",plan.getTime());
                                getContext().startActivity(it);
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
                return true;
            }
        });
    }


    private void initEvent() {
    }

    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }


}

