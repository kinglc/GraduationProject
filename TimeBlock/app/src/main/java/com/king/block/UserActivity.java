package com.king.block;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    //顶部
    private ImageView back;
    private TextView title;

    private int index;//0-好友，1-历史，2-时间日志，3-成就
    private int content_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initTop();
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity.this.finish();
            }
        });
    }

    private void initTop(){
        index= getIntent().getIntExtra("user_index",-1);
        content_index= getIntent().getIntExtra("content_index",-1);
        String[] titles = new String[]{"好友","历史","时间日志","成就勋章"};
        if(index==1) {
            switch (content_index) {
                case 0:
                    titles[1] += "待办";
                    break;
                case 1:
                    titles[1] += "计划";
                    break;
                case 2:
                    titles[1] += "备忘";
                    break;
                default:
                    break;
            }
        }
        title = (TextView)findViewById(R.id.title);
        title.setText(titles[index]);
    }

}
