package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.king.block.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartActivity extends AppCompatActivity {

    RadioGroup radiogroup;
    RadioButton rb_week, rb_month,rb_year;
    int type = 0;//0-周，1-月，2-年

    Calendar c;
    ImageView minus,add;
    TextView date;
    private int wYear, wMonth, wDay, wYearEnd,wMonthEnd,wDayEnd;//week页
    private int mYear, mMonth;//month页
    private int yYear;//year页

    String x[][]={{"周一","周二","周三","周四","周五","周六","周日"},
            {"1日","2日","3日","4日","5日","6日","7日","8日","9日","10日","11日","12日","13日","14日","15日","16日","17日",
                    "18日","19日","20日","21日","22日","23日","24日","25日","26日","27日","28日","29日","30日","31日"},
            {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"}};
    private PieChartView pie_chart;
    private ColumnChartView col_chart;
    private List<SubcolumnValue> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initTop();
        initComp();
        initDate();
        initEvent();

//        initX();
//        initPoint();
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

//        pie_chart = (PieChartView)findViewById(R.id.pie_chart);
        col_chart = (ColumnChartView)findViewById(R.id.col_chart);
    }

    private void initDate() {
        c = Calendar.getInstance();
        yYear = mYear = wYear = c.get(Calendar.YEAR);
        mMonth = wMonth = c.get(Calendar.MONTH) + 1;
        wDay = c.get(Calendar.DAY_OF_MONTH);
        int minu = c.get(Calendar.DAY_OF_WEEK);
        Toast.makeText(ChartActivity.this, "" + minu, Toast.LENGTH_SHORT).show();
        if (minu == 1) {
            minu = -6;
        } else {
            minu -= 2;
            minu *= -1;
        }
        c.add(Calendar.DAY_OF_YEAR, minu);
        wYear = c.get(Calendar.YEAR);
        wMonth = c.get(Calendar.MONTH) + 1;
        wDay = c.get(Calendar.DAY_OF_MONTH);
        getEndDay();
        date.setText(wYear + "年" + wMonth + "月" + wDay + "日 ~ " + wYearEnd + "年" +wMonthEnd + "月" +  wDayEnd + "日");
        initColChart();
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
                        date.setText(wYear + "年" + wMonth + "月" + wDay+"日 ~ "+wYearEnd + "年" + wMonthEnd + "月" + wDayEnd+"日");
                        break;
                    case R.id.month:
                        type=1;
                        rb_month.setBackgroundColor(getResources().getColor(R.color.commonBlue));
                        rb_month.setTextColor(getResources().getColor(R.color.white));
                        date.setText(mYear + "年" + mMonth + "月" );
                        break;
                    case R.id.year:
                        type=2;
                        rb_year.setBackgroundColor(getResources().getColor(R.color.commonBlue));
                        rb_year.setTextColor(getResources().getColor(R.color.white));
                        date.setText(yYear + "年" );
                        break;
                }
                initColChart();
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
            date.setText(wYear + "年"  + wMonth + "月" + wDay+"日 ~ "+wYearEnd + "年"  + wMonthEnd + "月" + wDayEnd+"日");
        }else if(type==1){
            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth-1);
            c.add(Calendar.MONTH, 1*minu);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH)+1;
            date.setText(mYear + "年"  + mMonth + "月" );
        }else if(type==2){
            yYear+=1*minu;
            date.setText(yYear + "年" );
        }
        values.clear();
        initColChart();
    }

    private int getMonthDay(){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    private void initColChart(){
        ColumnChartData data;
        int numColumns;
        int subColumns=4;
        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        int[][] hour = new int[31][4];
        String[] clr={"#EC2828","#FFEC87","#A6DA8C","#50C9EF"};//红黄绿蓝

        //初始化x轴
        if(type==1){
            numColumns=getMonthDay();
        } else {
            numColumns = x[type].length;
        }
        for (int i = 0; i < numColumns; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(x[type][i]));
            for(int j=0;j<4;j++) {
                hour[i][j] = (i+j) %7;//待删
            }
        }

        //获取y轴
//        String[] axisy

        //未完成-获取y数据
        //hour

        //设置每个柱
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            for(int j=0;j<subColumns;j++) {
                values.add(new SubcolumnValue(hour[i][j], Color.parseColor(clr[j])));
            }
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis(mAxisXValues).setHasLines(false).setTextColor(Color.BLACK));
        data.setAxisYLeft(new Axis().setHasLines(true).setTextColor(Color.BLACK).setMaxLabelChars(2).setName("时长/h"));
        data.setStacked(true);
        data.setFillRatio(0.75F);


//        col_chart.setValueSelectionEnabled(true);
//        col_chart.setInteractive(true);
//        col_chart.setZoomType(ZoomType.HORIZONTAL);
//        Viewport viewport =new Viewport(0,  col_chart.getMaximumViewport().height()*1.25f,10, 0);
        if(type==0) {
            Viewport viewport = new Viewport(0, 24, 7, 0);
            col_chart.setCurrentViewport(viewport);
            col_chart.setColumnChartData(data);
            col_chart.moveTo(0, 0);
        }else{
            col_chart.setColumnChartData(data);
            Viewport viewport = new Viewport(0, 24, 7, 0);
            col_chart.setCurrentViewport(viewport);
            col_chart.moveTo(0, 0);
        }
    }

}
