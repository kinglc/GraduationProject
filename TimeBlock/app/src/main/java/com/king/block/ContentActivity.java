package com.king.block;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;

import com.king.block.content.NoteFragment;
import com.king.block.content.PlanFragment;
import com.king.block.content.TodoFragment;
import com.king.block.user.AchieveActivity;
import com.king.block.user.ChartActivity;
import com.king.block.user.FriendActivity;
//import com.king.block.user.HistoryActivity;
import com.king.block.user.LogActivity;

import java.util.Calendar;
import java.util.Date;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {

    //顶部
//    private TextView date;
//    public int year, month, day;
//    private ImageView menu;
    private DrawerLayout drawerLayout;

    //底部
    private LinearLayout note, todo, plan;
    private ImageView note_pic, todo_pic, plan_pic;
    private TextView note_txt, todo_txt, plan_txt;

    //侧边
    private ImageView avatar;
    private TextView name;
    private LinearLayout friend, log, achieve, history;
    private String id;
//    private TextView history_txt;
//    private String txts[]={"待办","计划","备忘"};

    //正文
    private TodoFragment frag_todo;
    private PlanFragment frag_plan;
    private NoteFragment frag_note;
    private int index;//0-待办，1-计划，2-备忘录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
//        String name = getIntent().getStringExtra("name");///////////////////////////////////
//        String a = getIntent().getStringExtra("a");///////////////////////////////////
//        Toast.makeText(ContentActivity.this,name+a,Toast.LENGTH_SHORT).show();
        index = getIntent().getIntExtra("index", 1);
        initComp();
        initEvent();
        initStyle();
        initFrag();

        //初始计划页面
        plan_pic.setImageResource(R.drawable.plan_selected);
        plan_txt.setTextColor(Color.parseColor("#3FC1EB"));

    }


    //初始化组件
    private void initComp() {
        //顶部
//        date = (TextView) findViewById(R.id.date);
//        menu = (ImageView) findViewById(R.id.menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //底部
        note = (LinearLayout) findViewById(R.id.note);
        todo = (LinearLayout) findViewById(R.id.todo);
        plan = (LinearLayout) findViewById(R.id.plan);

        note_pic = (ImageView) findViewById(R.id.note_pic);
        todo_pic = (ImageView) findViewById(R.id.todo_pic);
        plan_pic = (ImageView) findViewById(R.id.plan_pic);

        note_txt = (TextView) findViewById(R.id.note_txt);
        todo_txt = (TextView) findViewById(R.id.todo_txt);
        plan_txt = (TextView) findViewById(R.id.plan_txt);

        //侧边
        avatar =(ImageView)findViewById(R.id.avatar);
        name = (TextView)findViewById(R.id.name);

        friend = (LinearLayout) findViewById(R.id.friend);
        history = (LinearLayout) findViewById(R.id.history);
        log = (LinearLayout) findViewById(R.id.log);
        achieve = (LinearLayout) findViewById(R.id.achieve);
//        history_txt = (TextView)findViewById(R.id.history_txt);
    }

    //初始化事件
    private void initEvent() {
        note.setOnClickListener(this);
        todo.setOnClickListener(this);
        plan.setOnClickListener(this);

        friend.setOnClickListener(this);
        history.setOnClickListener(this);
        log.setOnClickListener(this);
        achieve.setOnClickListener(this);

//        //选择日期
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ContentActivity.this, new DatePickerDialog.OnDateSetListener() {      //  日期选择对话框
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        //  这个方法是得到选择后的 年，月，日，分别对应着三个参数 — year、month、dayOfMonth
//                        month++;
//                        date.setText(year + "-" + month + "-" + dayOfMonth + "  ▼");
//                    }
//                }, year, month-1, day).show();   //  弹出日历对话框时，默认显示 年，月，日
//                setDate();
//            }
//        });

        //弹出侧边栏
//        menu.setOnClickListener(new View.OnClickListener() {/*重点，点击监听*/
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(Gravity.RIGHT);/*重点，LEFT是xml布局文件中侧边栏布局所设置的方向*/
//            }
//        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("id", id);
// 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(ContentActivity.this,"已复制ID至剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //重置初始化样式
    private void initStyle() {
        note_pic.setImageResource(R.drawable.note);
        note_txt.setTextColor(getResources().getColor(R.color.fontBlack));
        todo_pic.setImageResource(R.drawable.todo);
        todo_txt.setTextColor(getResources().getColor(R.color.fontBlack));
        plan_pic.setImageResource(R.drawable.plan);
        plan_txt.setTextColor(getResources().getColor(R.color.fontBlack));

//        Calendar mcalendar = Calendar.getInstance();     //  获取当前时间    —   年、月、日
//        year = mcalendar.get(Calendar.YEAR);         //  得到当前年
//        month = mcalendar.get(Calendar.MONTH)+1;       //  得到当前月
//        day = mcalendar.get(Calendar.DAY_OF_MONTH);  //  得到当前日
//        date.setText(year + "-" + month + "-" + day + "  ▼");

//        history_txt.setText("历史"+txts[index]);
    }

    //    修改导航栏样式，切换fragment
    //    或跳转user页面
    @Override
    public void onClick(View v) {
        Intent it = new Intent();
//        LinearLayout top = (LinearLayout)findViewById(R.id.top);
        switch (v.getId()) {
            case R.id.todo:
                index = 0;
                initStyle();
                todo_pic.setImageResource(R.drawable.todo_selected);
                todo_txt.setTextColor(getResources().getColor(R.color.commonBlue));
                history.setVisibility(View.GONE);
//                top.setVisibility(View.VISIBLE);
                initFrag();
                break;
            case R.id.plan:
                index = 1;
                initStyle();
                plan_pic.setImageResource(R.drawable.plan_selected);
                plan_txt.setTextColor(getResources().getColor(R.color.commonBlue));
                history.setVisibility(View.VISIBLE);
//                top.setVisibility(View.VISIBLE);
                initFrag();
                break;
            case R.id.note:
                index = 2;
                initStyle();
                note_pic.setImageResource(R.drawable.note_selected);
                note_txt.setTextColor(getResources().getColor(R.color.commonBlue));
                history.setVisibility(View.GONE);
//                top.setVisibility(View.GONE);
                initFrag();
                break;
            case R.id.friend:
                drawerLayout.closeDrawer(Gravity.RIGHT);
                it.setClass(ContentActivity.this, FriendActivity.class);
                startActivity(it);
                break;
            case R.id.history:
                drawerLayout.closeDrawer(Gravity.RIGHT);
                it.setClass(ContentActivity.this, ChartActivity.class);
                startActivity(it);
                break;
            case R.id.log:
                drawerLayout.closeDrawer(Gravity.RIGHT);
                it.setClass(ContentActivity.this, LogActivity.class);
                startActivity(it);
                break;
            case R.id.achieve:
                drawerLayout.closeDrawer(Gravity.RIGHT);
                it.setClass(ContentActivity.this, AchieveActivity.class);
                startActivity(it);
                break;
            default:
                break;
        }
    }

//    private void setDate(){
//        Bundle bundle = new Bundle();
//        bundle.putString("DATE",year + "-" + month + "-" + day);
//        switch (index){
//            case 0:frag_todo.setArguments(bundle);break;
//            case 1:frag_plan.setArguments(bundle);break;
//            case 2:frag_note.setArguments(bundle);break;
//            default:break;
//        }
//    }

    //初始化fragment
    private void initFrag() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (frag_todo == null) {
                    frag_todo = new TodoFragment();
                    transaction.add(R.id.contentfrag, frag_todo);
                } else {
                    transaction.show(frag_todo);
                }
                break;
            case 1:
                if (frag_plan == null) {
                    frag_plan = new PlanFragment();
                    transaction.add(R.id.contentfrag, frag_plan);
                } else {
                    transaction.show(frag_plan);
                }
                break;
            case 2:
                if (frag_note == null) {
                    frag_note = new NoteFragment();
                    transaction.add(R.id.contentfrag, frag_note);
                } else {
                    transaction.show(frag_note);
                }
                break;
            default:
                break;
        }
        transaction.commit();
//        setDate();
    }

    //隐藏Fragment
    private void hideFragment(android.app.FragmentTransaction transaction) {
        if (frag_todo != null) {
            transaction.hide(frag_todo);
        }
        if (frag_plan != null) {
            transaction.hide(frag_plan);
        }
        if (frag_note != null) {
            transaction.hide(frag_note);
        }
    }

}
