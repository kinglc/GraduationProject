package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.block.R;

public class HistoryActivity extends AppCompatActivity {

    private  int index;
    private String txts[]={"待办","计划","备忘"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initTop();
    }

    private void initTop(){
        ImageView back = (ImageView)findViewById(R.id.back);
        TextView title = (TextView)findViewById(R.id.title);

        index = getIntent().getIntExtra("index",-1);
        title.setText("历史"+txts[index]);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.this.finish();
            }
        });
    }
}