package com.king.block;

import android.app.Application;
import android.widget.Toast;

import com.king.block.user.Achieve;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Global extends Application {
    private String userId;
    private String URL;
    private ArrayList<Achieve> achieve = new ArrayList<Achieve>(Arrays.asList(
            new Achieve(1,5,"浅尝辄止","累计完成待办1天"),
            new Achieve(2,2,"走马观花","累计进行计划1小时"),
            new Achieve(3,5,"学贵有恒","累计完成待办7天"),
            new Achieve(4,2,"目不转睛","累计进行计划24小时"),
            new Achieve(5,5,"锲而不舍","累计完成待办30天"),
            new Achieve(6,2,"聚精会神","累计进行计划50小时"),
            new Achieve(7,4,"持之以恒","累计完成待办100天"),
            new Achieve(8,1,"全神贯注","累计进行计划100小时"),
            new Achieve(9,4,"细水长流","累计完成待办500天"),
            new Achieve(10,1,"心无旁骛","累计进行计划500小时"),
            new Achieve(11,3,"聚沙成塔","累计完成待办1000天"),
            new Achieve(12,0,"废寝忘食","累计进行计划1000小时")
    ));

    public ArrayList<Achieve> getAchieve() {
        return achieve;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate() {
        userId = "jc";
        URL = "http://127.0.0.1:3000";
        super.onCreate();
    }

    public JSONObject streamtoJson(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line, s="";
        JSONObject result = null;
        while ((line = reader.readLine()) != null) {
            s+=line;
        }
        reader.close();
        try {
            result = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int countTime(String time) {
        int min = 0;
        time= time.substring(0, time.length() - 1);
        if (time.indexOf('h') != -1) {
            String s[]=time.split("h");
            min = 60*Integer.parseInt(s[0]);
            time = s[1];
        }
        min+=Integer.parseInt(time);
        return min;
    }

    //比较日期
    public int cmpDate(Date d){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int nowd = Integer.parseInt(sdf.format(now));
        int dd = Integer.parseInt(sdf.format(d));
        if (dd< nowd) {
            return -1;
        } else if (dd > nowd) {
            return 1;
        }
        return 0;
    }

    //调用接口 设置log
//    public void setLog(int type, String name, String date){
//        try {
//            URL u = new URL(this.URL + "/log/set");
//            // 打开连接
//            HttpURLConnection con = (HttpURLConnection) u.openConnection();
//            con.setRequestProperty("accept", "*/*");
//            con.setRequestProperty("Connection", "Keep-Alive");
//            con.setRequestProperty("Cache-Control", "no-cache");
//            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//            con.setRequestMethod("POST");
//            con.setDoOutput(true);
//            con.setDoInput(true);
//            con.connect();
//
//            DataOutputStream out = new DataOutputStream(con.getOutputStream());
//            String content = "{\"user_id\":\"" + this.userId + "\",\"type\":"+type+
//                    ",\"name\":\""+name+ "\",\"date\":\""+date+"\"}";
//            out.writeBytes(content);
//            out.flush();
//            out.close();
//
//            if (con.getResponseCode() == 200) {
//                JSONObject res = this.streamtoJson(con.getInputStream());
//                int code = res.optInt("code");
//                String msg = res.optString("msg");
//                if (code == 200) {
////                    Toast.makeText("完成所有待办！",Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    Toast.makeText(Global.this, msg + res.getString("err"), Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(Global.this, "刷新信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
//            }
//            con.disconnect();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(Global.this, "连接错误", Toast.LENGTH_SHORT).show();
//        }
//    }
}
