package com.king.block.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.king.block.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
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
            {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"},
            {"1","2","3","4","5","6","7","8","9","10","11","12"}};
    PieChartView pie_chart;
    ColumnChartView col_chart;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

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

        pie_chart = (PieChartView)findViewById(R.id.pie_chart);
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
        initChart();
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

    private void initChart(){
        Toast.makeText(ChartActivity.this,"aaa",Toast.LENGTH_SHORT).show();

        ColumnChartView chart;            //柱状图的自定义View
        ColumnChartData data;             //存放柱状图数据的对象
        boolean hasAxes = true;            //是否有坐标轴
        boolean hasAxesNames = true;       //是否有坐标轴的名字
        boolean hasLabels = false;          //柱子上是否显示标识文字
        boolean hasLabelForSelected = true;    //柱子被点击时，是否显示标识的文字

        int numSubcolumns = 1;
        int numColumns = 8;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Axis X");
        axisY.setName("Axis Y");

    }

//    private void initX(){
//        for (int i = 0; i < x[type].length; i++) {
//            if(type==1&&i==getMonthDay()) break;
//            mAxisXValues.add(new AxisValue(i).setLabel(x[type][i]));
//        }
//    }
//
//    private void initPoint(){
//        //未完成-获取对应时间
//        for (int i = 0; i < 7; i++) {
//            mPointValues.add(new PointValue(i, i));
//        }
//    }

//    private void initChart(){
//        Column col = new Column(ChartActivity.this);
////                Column(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
//        List<Column> cols = new ArrayList<Column>();
//        col.set
//        col.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
//        col.setCubic(false);//曲线是否平滑，即是曲线还是折线
//        col.setFilled(false);//是否填充曲线的面积
//        col.setHasLabels(true);//曲线的数据坐标是否加上备注
////      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
//        col.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
//        col.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
//        cols.add(line);
//        ColumnChartData data = new ColumnChartData();
//        data.setColumns(cols);
//
//        //坐标轴
//        Axis axisX = new Axis(); //X轴
//        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
//        axisX.setTextColor(Color.GRAY);  //设置字体颜色
//        //axisX.setName("date");  //表格名称
//        axisX.setTextSize(10);//设置字体大小
//        axisX.setMaxLabelChars(20); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
//        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
//        data.setAxisXBottom(axisX); //x 轴在底部
//        //data.setAxisXTop(axisX);  //x 轴在顶部
//        axisX.setHasLines(true); //x 轴分割线
//
//        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
//        Axis axisY = new Axis();  //Y轴
//        axisY.setName("");//y轴标注
//        axisY.setTextSize(10);//设置字体大小
//        data.setAxisYLeft(axisY);  //Y轴设置在左边
//        //data.setAxisYRight(axisY);  //y轴设置在右边
//
//
//        //设置行为属性，支持缩放、滑动以及平移
//        lineChart.setInteractive(true);
//        lineChart.setZoomType(ZoomType.HORIZONTAL);
//        lineChart.setMaxZoom((float) 2);//最大方法比例
//        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//        lineChart.setLineChartData(data);
//        lineChart.setVisibility(View.VISIBLE);
//        /**注：下面的7，10只是代表一个数字去类比而已
//         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
//         */
//        Viewport v = new Viewport(lineChart.getMaximumViewport());
//        v.left = 0;
//        v.right = 7;
//        lineChart.setCurrentViewport(v);
//    }
}
