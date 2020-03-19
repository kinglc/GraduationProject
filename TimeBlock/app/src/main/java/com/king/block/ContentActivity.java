package com.king.block;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout note,list,plan,user;
    private ImageView note_pic,list_pic,plan_pic,user_pic;
    private TextView note_txt,list_txt,plan_txt,user_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

//        String name = intent.getStringExtra("name");//////////////////////////////////////////////
//        int age = intent.getIntExtra("age");

        initNavi();
        initStyle();
        initFrag(2);


    };

    //初始化导航栏组件
    private void initNavi(){
        note = (LinearLayout)findViewById(R.id.note);
        list = (LinearLayout)findViewById(R.id.list);
        plan = (LinearLayout)findViewById(R.id.plan);
        user = (LinearLayout)findViewById(R.id.user);

        note_pic = (ImageView)findViewById(R.id.note_pic);
        list_pic = (ImageView)findViewById(R.id.list_pic);
        plan_pic = (ImageView)findViewById(R.id.plan_pic);
        user_pic = (ImageView)findViewById(R.id.user_pic);

        note_txt=(TextView)findViewById(R.id.note_txt);
        list_txt=(TextView)findViewById(R.id.list_txt);
        plan_txt=(TextView)findViewById(R.id.plan_txt);
        user_txt=(TextView)findViewById(R.id.user_txt);

        note.setOnClickListener(this);
        list.setOnClickListener(this);
        plan.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    //初始化导航栏样式
    private void initStyle(){
        note_pic.setImageResource(R.drawable.note);
        note_txt.setTextColor(Color.parseColor("#bfbfbf"));
        list_pic.setImageResource(R.drawable.list);
        list_txt.setTextColor(Color.parseColor("#bfbfbf"));
        plan_pic.setImageResource(R.drawable.plan);
        plan_txt.setTextColor(Color.parseColor("#bfbfbf"));
        user_pic.setImageResource(R.drawable.user);
        user_txt.setTextColor(Color.parseColor("#bfbfbf"));
    }

    //初始化fragment
    private void initFrag(int index){

    }

    //修改导航栏样式，切换fragment
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.note:
                initStyle();
                note_pic.setImageResource(R.drawable.note_selected);
                note_txt.setTextColor(Color.parseColor("#3FC1EB"));
                initFrag(0);
                break;
            case R.id.list:
                initStyle();
                list_pic.setImageResource(R.drawable.list_selected);
                list_txt.setTextColor(Color.parseColor("#3FC1EB"));
                initFrag(1);
                break;
            case R.id.plan:
                initStyle();
                plan_pic.setImageResource(R.drawable.plan_selected);
                plan_txt.setTextColor(Color.parseColor("#3FC1EB"));
                initFrag(2);
                break;
            case R.id.user:
                initStyle();
                user_pic.setImageResource(R.drawable.user_selected);
                user_txt.setTextColor(Color.parseColor("#3FC1EB"));
                initFrag(3);
                break;
            default:break;
        }
    }

}
