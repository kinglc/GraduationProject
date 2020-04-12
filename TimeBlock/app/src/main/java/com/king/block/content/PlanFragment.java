package com.king.block.content;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.king.block.R;

import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment{
    View view;
    private ListView plan_lv;
//    private PlanAdapter planAdapter;
    public static List<Plan> plan_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        Intent it = new Intent(getContext(),PlanActivity.class);
        startActivity(it);

        return view;
    }

    private void initData(){
        //未完成 获取数据
        for(int i=0;i<10;i++) {
//            Plan plan = new Plan(i,i+""+i+i+i+i, i%2==0);
//            plan_list.add(plan);
        }
    }

    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }


}

