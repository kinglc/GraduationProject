package com.king.block.content;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.king.block.Global;
import com.king.block.R;

import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanFragment extends Fragment{
    View view;
    private ListView plan_lv;
    private PlanAdapter planAdapter;
    public static List<Plan> plan_list = new ArrayList<>();
    private Plan plan_now=null;

    TextView plan_title;
    TextView plan_content;
//    TextView plan_ddl;
    ImageView select;
    private Chronometer pass;
    int on = 1;//1-暂停 0-计时
    private long nowtime=0;

    ImageView add;
    private ImageView menu;
    Global global;

    int pass_red = 0,pass_yellow = 0,pass_green = 0,pass_blue = 0;
    private int prize[]={1,24,50,100,500,1000};

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            plan_now=null;
            plan_title.setText("");
            plan_content.setText("");
            pass.setBase(SystemClock.elapsedRealtime());
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        global = (Global)getActivity().getApplication();

        getPlan();
        initLv();
        initEvent();
        setNow();

        return view;
    }

    private void initLv(){
        planAdapter = new PlanAdapter(getActivity(), R.layout.item_plan, plan_list,handler);
        plan_lv =(ListView) view.findViewById(R.id.plan_lv);
        plan_lv.setAdapter(planAdapter);
        planAdapter.setListView(plan_lv);
        planAdapter.setGlobal(global);
        planAdapter.setList(plan_list);
        plan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                plan_now = getNow();
                if(on==0) {
                    on = 1;
                    stopTime();
                }
                plan_now = plan_list.get(position);
                setNow();
                planAdapter.setOn(on);
                planAdapter.setNowId(plan_now.getId());
            }
        });
    }

    private void initEvent() {
        plan_title = (TextView)view.findViewById(R.id.plan_title);
        plan_content = (TextView)view.findViewById(R.id.plan_content);
//        plan_ddl = (TextView)view.findViewById(R.plan_id.plan_ddl);
        pass = (Chronometer) view.findViewById(R.id.plan_pass);
        pass.setFormat("%s");

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
                Intent it = new Intent(getContext(), PlanActivity.class);
                getContext().startActivity(it);
            }
        });

        select = (ImageView)view.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(plan_now==null) return;
                if(on==1){
                    pass.setBase(pass.getBase() + (SystemClock.elapsedRealtime() - nowtime));
                    startTime();
                }else{
                    stopTime();
                    nowtime=SystemClock.elapsedRealtime();
                }
                on=1-on;
                planAdapter.setOn(on);
            }
        });
    }

    private void startTime(){
        select.setImageResource(R.drawable.pause);
        pass.start();
    }

    private void stopTime(){
        select.setImageResource(R.drawable.on);
        pass.stop();
        String now_t = pass.getText().toString();
        now_t = now_t.substring(0,now_t.length()-3)+"m";
        now_t= now_t.replace(":","h");
        if(now_t.charAt(0)=='0') now_t = now_t.replace("0h","");
        if(now_t=="00m") return;
        if(now_t.charAt(0)=='0') now_t=now_t.substring(1);
        pass(plan_now.getId(),now_t);

        String date = new SimpleDateFormat("yyyy-MM-dd").format( new Date());

        int user_pass_before = global.countTime(getTime());
        int pass_add = global.countTime(now_t)-global.countTime(plan_now.getPass());

        int user_pass_now = user_pass_before + pass_add;
        String plan_time = global.timeToString(user_pass_now);
        setTime(plan_time);

        int chart_id = isExist(date);
        switch (plan_now.urgency){
            case 0:pass_red+=pass_add;break;
            case 1:pass_yellow+=pass_add;break;
            case 2:pass_green+=pass_add;break;
            case 3:pass_blue+=pass_add;break;
            default:break;
        }
        if(chart_id==-1) addChart(date);
        else updateChart(chart_id);

        int pos = 0;
        for(;pos<prize.length;pos++) {
            if (user_pass_before < prize[pos]*60 && prize[pos]*60<=user_pass_now)
                break;
        }
        if(pos!=prize.length) {
            setAchieve(2 * pos);
            String content = "达成成就”" + global.getAchieve().get(2 * pos+1).getName() + "”";
            global.setLog(2, content, date);
            Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
        }

        plan_now.setPass(plan_time);
        plan_list.clear();
        getPlan();
    }

    private void setNow(){
        if(plan_now!=null){
            plan_title.setText(plan_now.getTitle());
            plan_content.setText(plan_now.getContent());
            nowtime = SystemClock.elapsedRealtime();
            long t = global.countTime(plan_now.getPass());
            pass.setBase(nowtime-t*60*1000);
            switch (plan_now.getUrgency()){
                case 0:
                    pass.setTextColor(getContext().getResources().getColor(R.color.red));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.red));
                    break;
                case 1:
                    pass.setTextColor(getContext().getResources().getColor(R.color.yellow));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.yellow));
                    break;
                case 2:
                    pass.setTextColor(getContext().getResources().getColor(R.color.green));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.green));
                    break;
                case 3:
                    pass.setTextColor(getContext().getResources().getColor(R.color.blue));
//                    plan_ddl.setTextColor(getContext().getResources().getColor(R.color.blue));
                    break;
                default:break;
            }
        }

    }

    //调用接口
    //获取
    private void getPlan() {
        plan_list.clear();
        try {
            URL url = new URL(global.getURL() + "/plan/query");
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
            String content = "{\"user_id\":\"" + global.getUserId() + "\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONArray plans = res.getJSONArray("data");
                    for (int i = 0; i < plans.length(); i++) {
                        JSONObject plan = plans.getJSONObject(i);
                        plan_list.add(new Plan(plan.getInt("plan_id"), plan.getString("title"), plan.getString("content"),
                                plan.getInt("urgency"), plan.getString("pass")));
                    }
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "获取失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //进行
    private void pass(int plan_id,String pass){
        try {
            URL url = new URL(global.getURL() + "/plan/pass");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"plan_id\":" + plan_id  + ",\"pass\":\""+pass+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
//                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "暂停失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //获取plan_time
    private String getTime(){
        try {
            URL u = new URL(global.getURL() + "/user/getTime");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"user_id\":\"" + global.getUserId() + "\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    return res.getJSONArray("data").getJSONObject(0).optString("plan_time");
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "刷新信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    //更新plan_time
    private void setTime(String plan_time){
        try {
            URL u = new URL(global.getURL() + "/user/setTime");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"plan_time\":\""+plan_time+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    return ;
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "更新失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //更新achieve
    private void setAchieve(int prize_plan){
        try {
            URL u = new URL(global.getURL() + "/achieve/plan");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"prize_plan\":"+prize_plan+"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
//                    Toast.makeText(getContext(),"完成所有待办！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "更新失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //chart是否存在
    private int isExist(String date){
        try {
            URL u = new URL(global.getURL() + "/chart/isExist");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"date\":\""+date +"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    JSONObject j =  res.getJSONArray("data").getJSONObject(0);
                    pass_red =j.optInt("pass_red");
                    pass_yellow =j.optInt("pass_yellow");
                    pass_green =j.optInt("pass_green");
                    pass_blue =j.optInt("pass_blue");
                    return j.optInt("chart_id");
                } else if(code==201){
                    pass_red = 0;
                    pass_yellow = 0;
                    pass_green = 0;
                    pass_blue = 0;
                    return -1;
                }else{
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "获取失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    //新建chart
    private void addChart(String date){
        try {
            URL url = new URL(global.getURL() + "/chart/add");
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
            String content = "{\"user_id\":\"" + global.getUserId() +
                    "\",\"pass_red\":"+pass_red+
                    ",\"pass_yellow\":"+pass_yellow+
                    ",\"pass_green\":"+pass_green+
                    ",\"pass_blue\":"+pass_blue+
                    ",\"date\":\""+date+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
//                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "新建数据失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //更新chart
    private void updateChart(int chart_id){
        try {
            URL u = new URL(global.getURL() + "/chart/update");
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            String content = "{\"chart_id\":" + chart_id +
                    ",\"pass_red\":"+pass_red+
                    ",\"pass_yellow\":"+pass_yellow+
                    ",\"pass_green\":"+pass_green+
                    ",\"pass_blue\":"+pass_blue+"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    return ;
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "更新失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }
}

