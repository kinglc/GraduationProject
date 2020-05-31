package com.king.block.content;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.king.block.ContentActivity;
import com.king.block.Global;
import com.king.block.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    ImageView back;
    ImageView save;
    TextView note_title;
    TextView note_content;
    TextView note_place;
    TextView note_date;
    TimePicker note_time;

    Calendar calendar;
    int nHour;
    int nMinute;
    int note_id;//-1为新建，其余为更新
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        global = (Global)getApplication();

        back = (ImageView)findViewById(R.id.back);
        save = (ImageView)findViewById(R.id.note_save);
        note_title = (TextView)findViewById(R.id.note_title);
        note_content = (TextView)findViewById(R.id.note_content);
        note_place = (TextView)findViewById(R.id.note_place);
        note_date = (TextView)findViewById(R.id.note_date);
        note_time = (TimePicker)findViewById(R.id.note_time);
        note_time.setIs24HourView(true);

        initData();

        note_time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                nHour = hourOfDay;
                nMinute = minute;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteActivity.this.finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(note_title.getText().toString().length()<=0){
                    Toast.makeText(NoteActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(note_id==-1) insert();
                else update();
                Intent it = new Intent(NoteActivity.this, ContentActivity.class);
                it.putExtra("index",2);
                startActivity(it);
            }
        });
    }

    //调用接口
    //添加
    private void insert(){
        try {
            URL url = new URL(global.getURL() + "/note/add");
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
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"title\":\"" + note_title.getText()+ "\",\"content\":\"" + note_content.getText()
                    + "\",\"place\":\"" + note_place.getText()+ "\",\"date\":\"" + note_date.getText()+ "\",\"time\":\"" + note_time.getHour()+":"+note_time.getMinute()+ "\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(NoteActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NoteActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(NoteActivity.this, "添加失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(NoteActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }
    
    //更新
    private void update(){
        try {
            URL url = new URL(global.getURL() + "/note/update");
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
            String content = "{\"note_id\":" + note_id + ",\"title\":\"" + note_title.getText()+ "\",\"content\":\"" + note_content.getText()
                    + "\",\"place\":\"" + note_place.getText()+ "\",\"date\":\"" + note_date.getText()+ "\",\"time\":\"" + note_time.getHour()+":"+note_time.getMinute()+ "\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(NoteActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NoteActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(NoteActivity.this, "修改失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(NoteActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        note_date.setText(getIntent().getStringExtra("note_date"));
        note_id = (getIntent().getIntExtra("note_id", -1));
        if (note_id != -1) {
            note_title.setText(getIntent().getStringExtra("note_title"));
            note_content.setText(getIntent().getStringExtra("note_content"));
            String time = getIntent().getStringExtra("note_time");
            nHour = Integer.parseInt(time.split(":")[0]);
            nMinute = Integer.parseInt(time.split(":")[1]);
            note_place.setText(getIntent().getStringExtra("note_place"));
        } else {
            calendar = Calendar.getInstance();
            nHour = calendar.get(Calendar.HOUR_OF_DAY);
            nMinute = calendar.get(Calendar.MINUTE);
        }
        note_time.setHour(nHour);
        note_time.setMinute(nMinute);
    }
}
