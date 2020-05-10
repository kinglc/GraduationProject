package com.king.block.content;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.king.block.R;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment  {

    View view;
    private ListView note_lv;
    private NoteAdapter noteAdapter;
    public static List<Note> note_list = new ArrayList<>();

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    CalendarView mCalendarView;
    LinearLayout linearLayout;
    private int mYear;
    private CalendarLayout calendarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note, container, false);
        initData();
        initLv();
        initEvent();
        DrawerLayout dl = (DrawerLayout)getActivity().findViewById(R.id.drawerLayout);
        dl.openDrawer(Gravity.RIGHT);;

        return view;
    }

    private void initView() {
//        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
//        mTextYear = (TextView) findViewById(R.id.tv_year);
//        mTextLunar = (TextView) findViewById(R.id.tv_lunar);
//        linearLayout = (LinearLayout) findViewById(R.id.rl_tool);
        mCalendarView = (CalendarView)view.findViewById(R.id.calendarView);
//        mTextCurrentDay = (TextView) findViewById(R.id.tv_current_day);
//        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!calendarLayout.isExpand()) {
//                    mCalendarView.showSelectLayout(mYear);
//                    return;
//                }
//                mCalendarView.showSelectLayout(mYear);
//                mTextLunar.setVisibility(View.GONE);
//                mTextYear.setVisibility(View.GONE);
//                mTextMonthDay.setText(String.valueOf(mYear));
//            }
//        });
//        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCalendarView.scrollToCurrent();
//            }
//        });

        calendarLayout = (CalendarLayout) view.findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(new CalendarView.OnYearChangeListener() {
            @Override
            public void onYearChange(int year) {

            }
        });
//        mCalendarView.setOnDateSelectedListener(new CalendarView.on OnDateSelectedListener());
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }



//    @Override
//    public void onDateSelected(Calendar calendar, boolean isClick) {
//        mTextLunar.setVisibility(View.VISIBLE);
//        mTextYear.setVisibility(View.VISIBLE);
//        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
//        mTextYear.setText(String.valueOf(calendar.getYear()));
//        mTextLunar.setText(calendar.getLunar());
//        mYear = calendar.getYear();
//    }

    private void initEvent() {
        noteAdapter = new NoteAdapter(getActivity(), R.layout.item_note, note_list);
        note_lv =(ListView) view.findViewById(R.id.note_lv);
        note_lv.setAdapter(noteAdapter);
        noteAdapter.setListView(note_lv);
        noteAdapter.setList(note_list);
        note_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定删除此备忘？")
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int del_id = note_list.get(position).getId();
                                note_list.remove(position);
//                                noteAdapter.setListView(note_lv);
                                noteAdapter = new NoteAdapter(getActivity(), R.layout.item_note, note_list);
                                note_lv.setAdapter(noteAdapter);
//                               未完成-删除数据库
//                                int delete = DataSupport.deleteAll(Ddl.class, "task = ? and content = ?", del1, del2);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                Button pButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                pButton.setTextColor(Color.rgb(240,60,80));
                nButton.setTextColor(Color.GRAY);
            }
        });
    }

    private void initLv() {
        
    }


    private void initData(){
        //未完成 获取数据
        for(int i=0;i<10;i++) {
            Note note = new Note(i,"备忘"+i, "世味年来薄似纱，谁令骑马客京华。小楼一夜听春雨，深巷明朝卖杏花。矮纸斜行闲作草，晴窗细乳戏分茶。","这里的山路十八弯","1001-1-"+i,"11:00");
            note_list.add(note);
        }
    }
    
    

    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }

}
