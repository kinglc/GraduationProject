package com.king.block.content;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.king.block.R;

import java.util.Calendar;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView back;
    ImageView save;
    TextView plan_title;
    TextView plan_content;
    DatePicker plan_date;
    TimePicker plan_time;
    TextView red;
    TextView yellow;
    TextView green;
    TextView blue;

    int id;
    int urgency;//-1 未选中，0 红，1 黄，2 绿，3 蓝
    Calendar calendar;
    int pYear;
    int pMonth;
    int pDay;
    int pHour;
    int pMinute;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        urgency=-1;
        initComp();
        initEvent();
        initStyle();
        initData();

    }

    private void initComp(){
        back = (ImageView)findViewById(R.id.back);
        save = (ImageView)findViewById(R.id.plan_save);
        plan_title = (TextView)findViewById(R.id.plan_title);
        plan_content = (TextView)findViewById(R.id.plan_content);
        plan_date = (DatePicker) findViewById(R.id.plan_date);
        plan_time = (TimePicker)findViewById(R.id.plan_time);
        plan_time.setIs24HourView(true);
        red = (TextView)findViewById(R.id.red);
        yellow = (TextView)findViewById(R.id.yellow);
        green = (TextView)findViewById(R.id.green);
        blue = (TextView)findViewById(R.id.blue);
    }

    private void initEvent(){
        red.setOnClickListener(this);
        yellow.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanActivity.this.finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未完成-上传/更新
//                note_time.
//                 date = ;
//                Note note = new Note(0,note_title.getText(),note_content.getText(),note_time.get)
            }
        });
    }

    private void initStyle(){
        switch (urgency){
            case 0:
                red.setBackground(getResources().getDrawable(R.drawable.ring_red));
                break;
            case 1:
                yellow.setBackground(getResources().getDrawable(R.drawable.ring_yellow));
                break;
            case 2:
                green.setBackground(getResources().getDrawable(R.drawable.ring_green));
                break;
            case 3:
                blue.setBackground(getResources().getDrawable(R.drawable.ring_blue));
                break;
                default:break;
        }
    }

    private void setStyle(){
        switch (urgency){
            case 0:
                red.setBackground(getResources().getDrawable(R.drawable.circle_red));
                break;
            case 1:
                yellow.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                break;
            case 2:
                green.setBackground(getResources().getDrawable(R.drawable.circle_green));
                break;
            case 3:
                blue.setBackground(getResources().getDrawable(R.drawable.circle_blue));
                break;
            default:break;
        }
    }

    private void initData() {
        id = (getIntent().getIntExtra("id", -1));
        if (id != -1) {
            plan_title.setText(getIntent().getStringExtra("plan_title"));
            plan_content.setText(getIntent().getStringExtra("plan_content"));
            urgency = getIntent().getIntExtra("urgency",-1);
            setStyle();
            String date = getIntent().getStringExtra("plan_date");
            pYear = Integer.parseInt(date.split("-")[0]);
            pMonth = Integer.parseInt(date.split("-")[1]);
            pDay = Integer.parseInt(date.split("-")[2]);
            String time = getIntent().getStringExtra("plan_time");
            pHour = Integer.parseInt(time.split(":")[0]);
            pMinute = Integer.parseInt(time.split(":")[1]);
        } else {
            calendar = Calendar.getInstance();
            pYear = calendar.get(Calendar.YEAR);
            pMonth = calendar.get(Calendar.MONTH);
            pDay = calendar.get(Calendar.DAY_OF_MONTH);
            pHour = calendar.get(Calendar.HOUR_OF_DAY);
            pMinute = calendar.get(Calendar.MINUTE);
        }
        plan_date.init(pYear, pMonth, pDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                pYear=year;
                pMonth=monthOfYear;
                pDay=dayOfMonth;
                Toast.makeText(PlanActivity.this,pYear+""+pMonth+""+pDay,Toast.LENGTH_SHORT).show();
            }
        });
        plan_time.setHour(pHour);
        plan_time.setMinute(pMinute);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.red:
                if(urgency==0){
                    red.setBackground(getResources().getDrawable(R.drawable.ring_red));
                    urgency=-1;
                }else {
                    initStyle();
                    urgency=0;
                    red.setBackground(getResources().getDrawable(R.drawable.circle_red));
                }
                break;
            case R.id.yellow:
                if(urgency==1){
                    yellow.setBackground(getResources().getDrawable(R.drawable.ring_yellow));
                    urgency=-1;
                }else {
                    initStyle();
                    urgency=1;
                    yellow.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                }
                break;
            case R.id.green:
                if(urgency==2){
                    green.setBackground(getResources().getDrawable(R.drawable.ring_green));
                    urgency=-1;
                }else {
                    initStyle();
                    urgency=2;
                    green.setBackground(getResources().getDrawable(R.drawable.circle_green));
                }
                break;
            case R.id.blue:
                if(urgency==3){
                    blue.setBackground(getResources().getDrawable(R.drawable.ring_blue));
                    urgency=-1;
                }else {
                    initStyle();
                    urgency=3;
                    blue.setBackground(getResources().getDrawable(R.drawable.circle_blue));
                }
                break;
            default:
                break;
        }
    }
}