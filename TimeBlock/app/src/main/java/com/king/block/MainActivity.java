package com.king.block;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.king.block.user.AchieveActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button regi;
    EditText input;
    Global global;
    String user_id;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        global = (Global)getApplication();
        global.setURL("http://10.0.2.2:8080");
//        global.setURL("http://140.143.78.135:8080");

        regi = (Button) findViewById(R.id.regi);
        input = (EditText)findViewById(R.id.input);

        deleteFile("user.txt");
        initEvent();
        if(getUser()){
            login();
        }
    }


    //从本地获取user_id及name
    private boolean getUser(){
        try {
            File file = new File(this.getFilesDir()+"/user.txt");
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String info[] = bufferedReader.readLine().split(";");
            user_id = info[0];
            name=info[1];
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setUser(){
        UUID id = UUID.randomUUID();
        String FILENAME = "user.txt";
        user_id = id.toString().replace("-","");
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write((user_id+";"+name).getBytes());
            this.getFilesDir();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initEvent(){
        regi.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(regi.getText().toString().equals("注 册")){
                    regi.setText("提 交");
                    input.setVisibility(View.VISIBLE);
                }else if(regi.getText().toString().equals("提 交")) {
                    if(input.getText().length()==0){
                        Toast.makeText(MainActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
                    }else if(input.getText().length()>50){
                        Toast.makeText(MainActivity.this,"请输入50字符以内昵称",Toast.LENGTH_SHORT).show();
                    }else {
                        name = input.getText().toString();
                        isExist();
                    }
                }
            }
        });
    }

    private void login(){
        global.setUserId(user_id);
        Intent it = new Intent(MainActivity.this, ContentActivity.class);
        it.putExtra("name",name);
        startActivity(it);
        MainActivity.this.finish();
    }

    //调用接口
    //注册
    private void register(){
        try {
            URL url = new URL(global.getURL() + "/user/register");
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
            String content = "{\"user_id\":\"" + user_id + "\",\"name\":\""+name+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    login();
                } else {
                    Toast.makeText(MainActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "注册失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            input.setText(e.toString());
            Toast.makeText(MainActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //判断name是否存在
    //200 存在
    //201 不存在
    private void isExist() {
        try {
            URL url = new URL(global.getURL() + "/user/isNameExist");
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
            String content = "{\"name\":\"" + name + "\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                int code = global.streamtoJson(con.getInputStream()).optInt("code");
                if (code == 200) {
                    setUser();
                    register();
                } else if (code == 201) {
                    Toast.makeText(MainActivity.this, "该用户名已存在", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(MainActivity.this, "获取用户名信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }
}



