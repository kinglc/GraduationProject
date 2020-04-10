package com.king.block;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login);
       
        login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ContentActivity.class);
                //未完成-登录
//                it.putExtra("name","xiazdong");///////////////////////////////////////////////////////////////
//                it.putExtra("age",20);
                startActivity(it);
                MainActivity.this.finish();
            }
        });
    }
}



