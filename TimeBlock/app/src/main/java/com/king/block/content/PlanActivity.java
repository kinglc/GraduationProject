package com.king.block.content;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.king.block.ContentActivity;
import com.king.block.Global;
import com.king.block.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView back;
    ImageView save;
    TextView plan_title;
    TextView plan_content;
//    DatePicker plan_date;
//    TimePicker plan_time;
    TextView red;
    TextView yellow;
    TextView green;
    TextView blue;

    int plan_id;
    int urgency;//-1 未选中，0 红，1 黄，2 绿，3 蓝
//    Calendar calendar;
//    int pYear;
//    int pMonth;
//    int pDay;
//    int pHour;
//    int pMinute;
    Global global;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        
        global = (Global)getApplication(); 

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
//        plan_date = (DatePicker) findViewById(R.plan_id.plan_date);
//        plan_time = (TimePicker)findViewById(R.plan_id.plan_time);
//        plan_time.setIs24HourView(true);
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
                    if(plan_title.getText().toString().length()==0){
                        Toast.makeText(PlanActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
                        return ;
                    }else if(plan_title.getText().toString().length()>100){
                        Toast.makeText(PlanActivity.this, "主题不得超过100字符", Toast.LENGTH_SHORT).show();
                        return ;
                    }else if(plan_content.getText().toString().length()>400){
                        Toast.makeText(PlanActivity.this, "内容不得超过400字符", Toast.LENGTH_SHORT).show();
                        return ;
                    }else if(urgency==-1){
                        Toast.makeText(PlanActivity.this,"请选择紧急度",Toast.LENGTH_SHORT).show();
                        return ;
                    }
                    if(plan_id==-1) insert();
                    else update();
                    Intent it = new Intent(PlanActivity.this, ContentActivity.class);
//                    it.putExtra("index",2);
                    startActivity(it);
                PlanActivity.this.finish();
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
        plan_id = (getIntent().getIntExtra("plan_id", -1));
        if (plan_id != -1) {
            plan_title.setText(getIntent().getStringExtra("plan_title"));
            plan_content.setText(getIntent().getStringExtra("plan_content"));
            urgency = getIntent().getIntExtra("urgency",-1);
            setStyle();
//            String date = getIntent().getStringExtra("plan_date");
//            pYear = Integer.parseInt(date.split("-")[0]);
//            pMonth = Integer.parseInt(date.split("-")[1]);
//            pDay = Integer.parseInt(date.split("-")[2]);
//            String time = getIntent().getStringExtra("plan_time");
//            pHour = Integer.parseInt(time.split(":")[0]);
//            pMinute = Integer.parseInt(time.split(":")[1]);
        }
//        else {
//            calendar = Calendar.getInstance();
//            pYear = calendar.get(Calendar.YEAR);
//            pMonth = calendar.get(Calendar.MONTH);
//            pDay = calendar.get(Calendar.DAY_OF_MONTH);
//            pHour = calendar.get(Calendar.HOUR_OF_DAY);
//            pMinute = calendar.get(Calendar.MINUTE);
//        }
//        plan_date.init(pYear, pMonth, pDay, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                pYear=year;
//                pMonth=monthOfYear;
//                pDay=dayOfMonth;
//                Toast.makeText(PlanActivity.this,pYear+""+pMonth+""+pDay,Toast.LENGTH_SHORT).show();
//            }
//        });
//        plan_time.setHour(pHour);
//        plan_time.setMinute(pMinute);
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

    //调用接口
    //添加
    private void insert(){
        try {
            URL url = new URL(global.getURL() + "/plan/add");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
//            String content = "user_id:" + global.getUserId();
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"title\":\"" + plan_title.getText()+ "\",\"content\":\"" + plan_content.getText()
                    + "\",\"urgency\":" + urgency+ "}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(PlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlanActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PlanActivity.this, "添加失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(PlanActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //更新
    private void update(){
        try {
            URL url = new URL(global.getURL() + "/plan/update");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
//            String content = "user_id:" + global.getUserId();
            String content = "{\"plan_id\":" + plan_id + ",\"title\":\"" + plan_title.getText()+ "\",\"content\":\"" + plan_content.getText()
                    + "\",\"urgency\":" + urgency+ "}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(PlanActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlanActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PlanActivity.this, "修改失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(PlanActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }
}
