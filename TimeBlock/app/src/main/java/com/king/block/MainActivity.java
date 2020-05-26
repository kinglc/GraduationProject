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
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button regi;
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
        global.setURL("http://10.0.2.2:3000");
        regi = (Button) findViewById(R.id.regi);
        initEvent();
        if(getUser()){
            login();
        }
    }

    //从本地获取user_id及name
    private boolean getUser(){
        return true;
    }
    
    private void setUser(){

    }

    private void initEvent(){
        regi.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
               register();
               setUser();
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

    }
}



