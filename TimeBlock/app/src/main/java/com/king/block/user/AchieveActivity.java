package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.king.block.R;

import java.util.ArrayList;

public class AchieveActivity extends AppCompatActivity {

    private GridView gv;
    private AchieveAdapter achieveAdapter;
    private ArrayList<Achieve> achieve_list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        initTop();
        initData();
        initGv();
    }

    private void initTop() {
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AchieveActivity.this.finish();
            }
        });
    }

    private void initData() {
        // 未完成-初始化数据
        achieve_list = new ArrayList<Achieve>();
        for (int i = 0; i < 30; i++) {
//            achieve_list.add(new Achieve(i, i % 6, "累积计划" + i + "天"));
        }
        System.out.println(achieve_list);
    }

    private void initGv() {
        gv = (GridView) findViewById(R.id.achieve_gv);
        achieveAdapter = new AchieveAdapter(AchieveActivity.this, R.layout.item_achieve, achieve_list);
        gv.setAdapter(achieveAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AchieveActivity.this, ""+achieve_list.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
