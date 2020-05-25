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

import com.king.block.Global;
import com.king.block.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
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
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartActivity extends AppCompatActivity {

    RadioGroup radiogroup;
    RadioButton rb_week, rb_month,rb_year;
    int type = 0;//0-周，1-月，2-年
    List<Chart> chart_list = new ArrayList<>();
    Global global;

    Calendar c;
    ImageView minus,add;
    TextView date;
    private int wYear, wMonth, wDay, wYearEnd,wMonthEnd,wDayEnd;//week页
    private int mYear, mMonth;//month页
    private int yYear;//year页

    int []week = new int[7];
    String[] weekday = {"周一","周二","周三","周四","周五","周六","周日"};

    private PieChartView pie_chart;
    private ColumnChartView col_chart;
    private List<SubcolumnValue> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        global = (Global)getApplication();
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

//        pie_chart = (PieChartView)findViewById(R.id.pie_chart);
        col_chart = (ColumnChartView)findViewById(R.id.col_chart);
    }

    private void initDate() {
        c = Calendar.getInstance();
        yYear = mYear = wYear = c.get(Calendar.YEAR);
        mMonth = wMonth = c.get(Calendar.MONTH) + 1;
        wDay = c.get(Calendar.DAY_OF_MONTH);
        int minu = c.get(Calendar.DAY_OF_WEEK);
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
        getCharts();
        initColChart();
    }

    private void getEndDay() {
        for(int i=0;i<7;i++) {
            c.clear();
            c.set(Calendar.YEAR, wYear);
            c.set(Calendar.MONTH, wMonth - 1);
            c.set(Calendar.DAY_OF_MONTH, wDay);
            c.add(Calendar.DAY_OF_YEAR, i);
            week[i]=c.get(Calendar.DAY_OF_MONTH);
        }
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
                getCharts();
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
        getCharts();
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

    private float[] minToHour(int[] min){
        float hour[] = new float[4];
        for(int i=0;i<4;i++){
            hour[i] = (float) min[0]/60;
        }
        return hour;
    }

    private void initColChart(){
        ColumnChartData data;
        int numColumns=7;
        int subColumns=4;
        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        float[][] hour = new float[31][4];
        String[] clr={"#EC2828","#FFEC87","#A6DA8C","#50C9EF"};//红黄绿蓝

        //初始化x轴
        switch (type){
            case 0:numColumns=7;break;
            case 1:numColumns=getMonthDay();break;
            case 2:numColumns=12;break;
        }

        for (int i = 0, j = 0; i < numColumns; i++) {
            float[] time = {0, 0, 0, 0};
            if(type==0) {
                mAxisXValues.add(new AxisValue(i).setLabel(weekday[i]));
                if (j < chart_list.size() && Integer.parseInt(chart_list.get(j).getDate().substring(8))==week[i]) {
                    time = minToHour(chart_list.get(j).getPass());
                    j++;
                }
            }else if (type == 1) {
                mAxisXValues.add(new AxisValue(i).setLabel(i + 1 + "日"));
                if (j < chart_list.size() && Integer.parseInt(chart_list.get(j).getDate().substring(8)) == i + 1) {
                    time = minToHour(chart_list.get(j).getPass());
                    j++;
                }
            }else {
                    mAxisXValues.add(new AxisValue(i).setLabel(i+1+"月"));
                    if (j < chart_list.size()) {
                        int month = Integer.parseInt(chart_list.get(j).getDate().substring(5,7));
                        while(month==i+1){
                            for(int k=0;k<4;k++){
                                time[k]+=(float) chart_list.get(j).getPass()[k]/60;
                            }
                            j++;
                            if(j==chart_list.size()) break;
                            month = Integer.parseInt(chart_list.get(j).getDate().substring(5,7));
                        }
                    }
                }
                System.arraycopy(time, 0, hour[i], 0, 4);
            }
        
        //初始化Y轴
//        Axis axisY = new Axis().setHasLines(true);
//        axisY.setMaxLabelChars(6);//max label length, for example 60
//        List<AxisValue> axis = new ArrayList<>();
//        for(int i = 0; i < 24; i+= 10){
//            AxisValue value = new AxisValue(i);
//            String label = i+1+"";
//            value.setLabel(label);
//            axis.add(value);
//        }
//        axisY.setValues(axis);

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

    //调用接口
    //获取
    private void getCharts(){
        chart_list.clear();
        try {
            URL url = new URL(global.getURL() + "/chart/query");
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
            String date="";
            if(type==0) {
                date = wYear+"-"+wMonth+"-"+wDay;
            }else if(type==1){
                date = mYear+"-"+mMonth+"-01";
            }else{
                date = yYear+"-01-01";
            }
            String content = "{\"user_id\":\"" + global.getUserId()
                    +"\",\"date\":\""+date
                    +"\",\"type\":"+type+"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONArray charts = res.getJSONArray("data");
                    for (int i = 0; i < charts.length(); i++) {
                        JSONObject chart = charts.getJSONObject(i);
                        chart_list.add(new Chart(chart.getString("date"), new int[]{chart.getInt("pass_red"), chart.getInt("pass_yellow"),
                                chart.getInt("pass_green"),chart.getInt("pass_blue")}));
                    }
                } else {
                    Toast.makeText(ChartActivity.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ChartActivity.this, "刷新计划信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ChartActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
        }
    }


}
