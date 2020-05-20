package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.king.block.R;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    View view;
    private ListView log_lv;
    private LogAdapter logAdapter;
    public static List<Log> log_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initTop();
        initData();
        initLv();
    }

    private void initTop() {
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogActivity.this.finish();
            }
        });
    }

    private void initData() {
        //未完成 获取数据
        for (int i = 0; i < 10; i++) {
//            Log log = new Log(i, i % 3, i);
//            log_list.add(log);
        }
    }

    private void initLv() {
        logAdapter = new LogAdapter(LogActivity.this, R.layout.item_log, log_list);
        log_lv = (ListView) findViewById(R.id.log_lv);
        log_lv.setAdapter(logAdapter);
    }
}