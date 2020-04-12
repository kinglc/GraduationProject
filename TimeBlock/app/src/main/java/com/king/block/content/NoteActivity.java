package com.king.block.content;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.king.block.R;

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
    int id;//-1为新建，其余为更新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

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
                //未完成-上传/更新
//                note_time.
//                 date = ;
//                Note note = new Note(0,note_title.getText(),note_content.getText(),note_time.get)
            }
        });
    }

    private void initData() {
        note_date.setText(getIntent().getStringExtra("note_date"));
        id = (getIntent().getIntExtra("id", -1));
        if (id != -1) {
            note_title.setText(getIntent().getStringExtra("note_title"));
            note_content.setText(getIntent().getStringExtra("note_content"));
            note_date.setText(getIntent().getStringExtra("note_date"));
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
