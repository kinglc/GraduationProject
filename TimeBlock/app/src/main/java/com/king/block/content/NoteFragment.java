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
import com.king.block.Global;
import com.king.block.R;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import org.json.JSONArray;
import org.json.JSONObject;

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
    private int[] colors= {0xFF40db25, 0xFFe69138, 0xFFdf1356,0xFFedc56d,0xFFaacc44,0xFFbc13f0};

    RelativeLayout mRelativeTool;
    Global global;
    private CalendarLayout mCalendarLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note, container, false);

        global = (Global)getActivity().getApplication();
        initCalendar();
//        initScheme();
        getNote(mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth()+"-"+mCalendarView.getCurDay());
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

            Map<String, Calendar> map = new HashMap<>();
            for(int i=0;i<note_list.size();i++) {
                String[] d = note_list.get(i).getDate().split("-");
                String txt = ""+note_list.get(i).getTitle().charAt(0);
                int year = Integer.parseInt(d[0]);
                int month = Integer.parseInt(d[1]);
                int day = Integer.parseInt(d[2]);
                map.put(getSchemeCalendar(year, month, day, colors[(year+month+day)%6], txt).toString(),
                        getSchemeCalendar(year, month, day,  colors[(year+month+day)%6], txt));
            }
//
//            map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                    getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//            map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                    getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//            map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                    getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//            map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
//                    getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
//            map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
//                    getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
//            map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
//                    getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//            map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
//                    getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//            map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
//                    getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
            //此方法在巨大的数据量上不影响遍历性能，推荐使用
            mCalendarView.setSchemeDate(map);

    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onMonthChange(int year, int month) {
        getNote(year+"-"+month+"-01");
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

    //调用接口
    //获取
    private void getNote(String date) {
        try {
            URL url = new URL(global.getURL() + "/note/query");
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
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"date\":\""+date+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONArray notes = res.getJSONArray("data");
                    for(int i=0;i<notes.length();i++) {
                        JSONObject note = notes.getJSONObject(i);
                        note_list.add(new Note(note.getInt("note_id"), note.getString("title"), note.getString("content"),
                                note.getString("place"), note.getString("date").split("T")[0], note.getString("time")));
                    }
                    initScheme();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "刷新待办信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

}
