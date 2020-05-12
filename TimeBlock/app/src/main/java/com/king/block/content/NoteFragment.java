package com.king.block.content;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
public class NoteFragment extends Fragment implements CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener,CalendarView.OnCalendarSelectListener {

    View view;
    ImageView menu;
    ImageView add;

    private ListView note_lv;
    private NoteAdapter noteAdapter;
    public static List<Note> note_list = new ArrayList<>();

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    CalendarView mCalendarView;
    private int mYear;

    RelativeLayout mRelativeTool;

    private CalendarLayout mCalendarLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note, container, false);

        initCalendar();
        initScheme();
        initData(mCalendarView.getCurYear(),mCalendarView.getCurMonth());
        initView();

        return view;
    }

    private void initCalendar() {
        mTextMonthDay = (TextView) view.findViewById(R.id.tv_month_day);
        mTextYear = (TextView) view.findViewById(R.id.tv_year);
        mTextLunar = (TextView) view.findViewById(R.id.tv_lunar);
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                        mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });

        mCalendarLayout = (CalendarLayout) view.findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    private void initScheme(){
            int year = mCalendarView.getCurYear();
            int month = mCalendarView.getCurMonth();

            Map<String, Calendar> map = new HashMap<>();
            map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                    getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
            map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                    getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
            map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
                    getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
            map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                    getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
            map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
                    getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
            map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                    getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
            map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
                    getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
            map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
                    getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
            map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
                    getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
            //此方法在巨大的数据量上不影响遍历性能，推荐使用
            mCalendarView.setSchemeDate(map);

    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onMonthChange(int year, int month) {
        initData(year,month);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    private void initView() {
        menu = (ImageView) view.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout dl = (DrawerLayout) getActivity().findViewById(R.id.drawerLayout);
                dl.openDrawer(Gravity.RIGHT);
            }
        });
        add= (ImageView) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), NoteActivity.class);
                getContext().startActivity(it);
            }
        });

        noteAdapter = new NoteAdapter(getActivity(), R.layout.item_note, note_list);
        note_lv = (ListView) view.findViewById(R.id.note_lv);
        note_lv.setAdapter(noteAdapter);
        noteAdapter.setListView(note_lv);
        noteAdapter.setList(note_list);
        note_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定删除此备忘？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
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
                pButton.setTextColor(Color.rgb(240, 60, 80));
                nButton.setTextColor(Color.GRAY);
            }
        });
    }

    private void initData(int year,int month) {
        Toast.makeText(getContext(), year+" "+month,Toast.LENGTH_SHORT).show();
        //未完成 获取数据
        for (int i = 0; i < 10; i++) {
            Note note = new Note(i, "备忘" + i, "世味年来薄似纱，谁令骑马客京华。小楼一夜听春雨，深巷明朝卖杏花。矮纸斜行闲作草，晴窗细乳戏分茶。", "这里的山路十八弯", "1001-1-" + i, "11:00");
            note_list.add(note);
        }
    }

}
