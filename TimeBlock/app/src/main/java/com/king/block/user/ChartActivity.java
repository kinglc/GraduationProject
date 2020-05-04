package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.king.block.R;

import java.util.Calendar;

public class ChartActivity extends AppCompatActivity {

    RadioGroup radiogroup;
    RadioButton rb_week, rb_month,rb_year;
    int type;//0-周，1-年，2-月

    Calendar c;
    ImageView minus,add;
    TextView date;
    private int wYear, wMonth, wDay, wYearEnd,wMonthEnd,wDayEnd;//week页
    private int mYear, mMonth;//month页
    private int yYear;//year页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initTop();
        initComp();
        initDate();
        initEvent();
    }

    private void initTop(){
        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartActivity.this.finish();
            }
        });
    }

    private void initComp(){
        radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
        rb_week = (RadioButton)findViewById(R.id.week);
        rb_month = (RadioButton)findViewById(R.id.month);
        rb_year = (RadioButton)findViewById(R.id.year);
        date = (TextView)findViewById(R.id.date);
        minus = (ImageView)findViewById(R.id.minus);
        add = (ImageView)findViewById(R.id.add);
    }

    private void initDate() {
        c = Calendar.getInstance();
        yYear = mYear = wYear = c.get(Calendar.YEAR);
        mMonth = wMonth = c.get(Calendar.MONTH) + 1;
        wDay = c.get(Calendar.DAY_OF_MONTH);
        int minu = c.get(Calendar.DAY_OF_WEEK);
        Toast.makeText(ChartActivity.this,""+minu,Toast.LENGTH_SHORT).show();
        if (minu == 1) {
            minu = -6;
        }else {
            minu -= 2;
            minu *= -1;
        }
        c.add(Calendar.DAY_OF_YEAR, minu);
        wYear = c.get(Calendar.YEAR);
        wMonth = c.get(Calendar.MONTH)+1;
        wDay = c.get(Calendar.DAY_OF_MONTH);
        getEndDay();
        date.setText(wYear + "-" + wMonth + "-" + wDay + " ~ " + wYearEnd + "-" + wMonthEnd + "-" + wDayEnd);
        initChart();
    }

    private void getEndDay() {
        c.clear();
        c.set(Calendar.YEAR, wYear);
        c.set(Calendar.MONTH, wMonth-1);
        c.set(Calendar.DAY_OF_MONTH, wDay);
        c.add(Calendar.DAY_OF_YEAR, 6);
        wYearEnd = c.get(Calendar.YEAR);
        wMonthEnd = c.get(Calendar.MONTH)+1;
        wDayEnd = c.get(Calendar.DAY_OF_MONTH);
    }

    private void initGroup(){
        rb_week.setBackground(getResources().getDrawable(R.drawable.left_radius));
        rb_week.setTextColor(getResources().getColor(R.color.commonBlue));
        rb_month.setBackgroundColor(getResources().getColor(R.color.white));
        rb_month.setTextColor(getResources().getColor(R.color.commonBlue));
        rb_year.setBackground(getResources().getDrawable(R.drawable.right_radius));
        rb_year.setTextColor(getResources().getColor(R.color.commonBlue));
    }

    private void initEvent(){
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                initGroup();
                switch(checkedId){
                    case R.id.week:
                        type=0;
                        rb_week.setBackgroundColor(getResources().getColor(R.color.commonBlue));
                        rb_week.setTextColor(getResources().getColor(R.color.white));
                        date.setText(wYear + "-" + wMonth + "-" + wDay+" ~ "+wYearEnd + "-" + wMonthEnd + "-" + wDayEnd);
                    break;
                    case R.id.month:
                        type=1;
                        rb_month.setBackgroundColor(getResources().getColor(R.color.commonBlue));
                        rb_month.setTextColor(getResources().getColor(R.color.white));
                        date.setText(mYear + "-" + mMonth);
                        break;
                    case R.id.year:
                        type=2;
                        rb_year.setBackgroundColor(getResources().getColor(R.color.commonBlue));
                        rb_year.setTextColor(getResources().getColor(R.color.white));
                        date.setText(""+yYear);
                        break;
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(-1);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(1);
            }
        });
    }

    private void changeDate(int minu){
        c.clear();
        if(type==0) {
            c.set(Calendar.YEAR, wYear);
            c.set(Calendar.MONTH, wMonth-1);
            c.set(Calendar.DAY_OF_MONTH, wDay);
            c.add(Calendar.DAY_OF_YEAR, 7*minu);
            wYear = c.get(Calendar.YEAR);
            wMonth = c.get(Calendar.MONTH)+1;
            wDay = c.get(Calendar.DAY_OF_MONTH);
            getEndDay();
            date.setText(wYear + "-" + wMonth + "-" + wDay+" ~ "+wYearEnd + "-" + wMonthEnd + "-" + wDayEnd);
        }else if(type==1){
            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth-1);
            c.add(Calendar.MONTH, 1*minu);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH)+1;
            date.setText(mYear + "-" + mMonth);
        }else if(type==2){
            yYear+=1*minu;
            date.setText(""+yYear);
        }
        initChart();
    }

    private void initChart(){
        Toast.makeText(ChartActivity.this,"chart",Toast.LENGTH_SHORT).show();
    }
}
