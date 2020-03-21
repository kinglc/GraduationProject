package com.king.block;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener{

    //顶部
    private TextView date;
    private int year,month,day;
    private ImageView menu;
    private DrawerLayout drawerLayout;

    //底部
    private LinearLayout note,list,plan;
    private ImageView note_pic,list_pic,plan_pic;
    private TextView note_txt,list_txt,plan_txt;

    //侧边
    private LinearLayout friend,history,log,achieve;

    private int index;//0-待办，1-计划，2-备忘录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
//        String name = getIntent().getStringExtra("name");///////////////////////////////////
//        String a = getIntent().getStringExtra("a");///////////////////////////////////
//        Toast.makeText(ContentActivity.this,name+a,Toast.LENGTH_SHORT).show();
        initComp();
        initEvent();
        initStyle();
        initFrag();

        //初始计划页面
//        index=1;
        plan_pic.setImageResource(R.drawable.plan_selected);
        plan_txt.setTextColor(Color.parseColor("#3FC1EB"));

    };

    //初始化组件
    private void initComp(){
        //顶部
        date = (TextView) findViewById(R.id.date);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        menu = (ImageView)findViewById(R.id.menu);

        //底部
        note = (LinearLayout)findViewById(R.id.note);
        list = (LinearLayout)findViewById(R.id.list);
        plan = (LinearLayout)findViewById(R.id.plan);

        note_pic = (ImageView)findViewById(R.id.note_pic);
        list_pic = (ImageView)findViewById(R.id.list_pic);
        plan_pic = (ImageView)findViewById(R.id.plan_pic);

        note_txt=(TextView)findViewById(R.id.note_txt);
        list_txt=(TextView)findViewById(R.id.list_txt);
        plan_txt=(TextView)findViewById(R.id.plan_txt);

        //侧边
        friend = (LinearLayout)findViewById(R.id.friend);
        history = (LinearLayout)findViewById(R.id.history);
        log = (LinearLayout)findViewById(R.id.log);
        achieve = (LinearLayout)findViewById(R.id.achieve);
    }

    //初始化事件
    private void initEvent(){
        note.setOnClickListener(this);
        list.setOnClickListener(this);
        plan.setOnClickListener(this);

        friend.setOnClickListener(this);
        history.setOnClickListener(this);
        log.setOnClickListener(this);
        achieve.setOnClickListener(this);

        //选择日期
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ContentActivity.this, new DatePickerDialog.OnDateSetListener() {      //  日期选择对话框
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //  这个方法是得到选择后的 年，月，日，分别对应着三个参数 — year、month、dayOfMonth
                        date.setText(year + "-" + month + "-" + dayOfMonth+ "  ▼");
                    }
                }, year, month, day).show();   //  弹出日历对话框时，默认显示 年，月，日
            }
        });

        //弹出侧边栏
        menu.setOnClickListener(new View.OnClickListener() {/*重点，点击监听*/
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);/*重点，LEFT是xml布局文件中侧边栏布局所设置的方向*/
            }
        });
    }

    //重置初始化样式
    private void initStyle(){
        note_pic.setImageResource(R.drawable.note);
        note_txt.setTextColor(Color.parseColor("#bfbfbf"));
        list_pic.setImageResource(R.drawable.list);
        list_txt.setTextColor(Color.parseColor("#bfbfbf"));
        plan_pic.setImageResource(R.drawable.plan);
        plan_txt.setTextColor(Color.parseColor("#bfbfbf"));

        Calendar mcalendar = Calendar.getInstance();     //  获取当前时间    —   年、月、日
        year = mcalendar.get(Calendar.YEAR);         //  得到当前年
        month = mcalendar.get(Calendar.MONTH);       //  得到当前月
        day = mcalendar.get(Calendar.DAY_OF_MONTH);  //  得到当前日
        date.setText(year + "-" + month + "-" + day+ "  ▼");
    }

//    修改导航栏样式，切换fragment
//    或跳转user页面
    @Override
    public void onClick(View v) {
        Intent it = new Intent();
        switch (v.getId()){
            case R.id.list:
                index=0;
                initStyle();
                list_pic.setImageResource(R.drawable.list_selected);
                list_txt.setTextColor(Color.parseColor("#3FC1EB"));
                initFrag();
                break;
            case R.id.plan:
                index=1;
                initStyle();
                plan_pic.setImageResource(R.drawable.plan_selected);
                plan_txt.setTextColor(Color.parseColor("#3FC1EB"));
                initFrag();
                break;
            case R.id.note:
                index=2;
                initStyle();
                note_pic.setImageResource(R.drawable.note_selected);
                note_txt.setTextColor(Color.parseColor("#3FC1EB"));
                initFrag();
                break;
            case R.id.friend:
                it.setClass(ContentActivity.this, UserActivity.class);
                it.putExtra("user_index",0);
                startActivity(it);
                break;
            case R.id.history:
                it.setClass(ContentActivity.this, UserActivity.class);
                it.putExtra("content_index",index);
                it.putExtra("user_index",1);
                startActivity(it);
                break;
            case R.id.log:
                it.setClass(ContentActivity.this, UserActivity.class);
                it.putExtra("user_index",2);
                startActivity(it);
                break;
            case R.id.achieve:
                it.setClass(ContentActivity.this, UserActivity.class);
                it.putExtra("user_index",3);
                startActivity(it);
                break;
            default:break;
        }
    }

    //初始化fragment
    private void initFrag(){

    }


}
