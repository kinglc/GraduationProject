package com.king.block.content;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.king.block.Global;
import com.king.block.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class TodoAdapter extends ArrayAdapter<Todo>{
    private int resourceId;
    public int now_index=-1;
    private int newResourceId;
    private int lastEdit=-1;
    private ListView mListView;
    private List<Todo> todo_list;

    private int notfinish = 0;
    private boolean clickable = true;
    private int tododay=-1;
    private int prize[]={1,7,30,100,500,1000};
    private Global global;

    public void setGlobal(Global global) {
        this.global = global;
    }

    public TodoAdapter(Context context, int resourceId, List<Todo> todo_list){
        super(context, resourceId, todo_list);
        this.todo_list = todo_list;
        newResourceId = resourceId;
    }

    private Todo todo;
    private ViewHolder viewHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        todo = (Todo)getItem(position);
        viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
            viewHolder = new ViewHolder();
            initHolder(convertView,viewHolder);
            viewHolder.todo_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!clickable) return;
                    turnOutEdit();
                    turnInEdit(position);
                }
            });
            viewHolder.todo_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!clickable) return;
                    turnOutEdit();

                    View view = mListView.getChildAt(position - mListView.getFirstVisiblePosition());
                    ViewHolder holder = (ViewHolder) view.getTag();
                    initHolder(view,holder);
                    Todo t = todo_list.get(position);
                    editTodo(t.getId(),holder.todo_input.getText().toString());
                }
            });
            viewHolder.todo_checked.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    View view = mListView.getChildAt(position - mListView.getFirstVisiblePosition());
                    ViewHolder holder = (ViewHolder) view.getTag();
                    initHolder(view,holder);
                    if(!clickable) {
                        holder.todo_checked.setChecked(!holder.todo_checked.isChecked());
                        return;
                    }
                    changeStyle(holder,holder.todo_checked.isChecked());//修改样式
                    Todo t = todo_list.get(position);
                    editTodo(t.getId(),holder.todo_checked.isChecked()?1:0);//更新Todo信息
                    if(holder.todo_checked.isChecked()){
                        notfinish--;
                    }else{
                        notfinish++;
                    }
                    finish();
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        changeStyle(viewHolder,todo.isChecked());
        viewHolder.todo_title.setText(todo.getTitle());
        viewHolder.todo_checked.setChecked(todo.isChecked());
        return convertView;
    }

    private void changeStyle(ViewHolder vh, boolean isChecked){
        if(isChecked){
            vh.todo_title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            vh.todo_title.setTextColor(getContext().getResources().getColor(R.color.lightFontGray));
        }else{
            vh.todo_title.setPaintFlags(viewHolder.todo_title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            vh.todo_title.setTextColor(Color.parseColor("#000000"));
        }
    }

    private void turnInEdit(int position){
        View view = mListView.getChildAt(position - mListView.getFirstVisiblePosition());
        ViewHolder holder = (ViewHolder) view.getTag();
        initHolder(view,holder);
        if(!holder.todo_checked.isChecked()) {
            holder.todo_input.setText(holder.todo_title.getText());
            holder.todo_edit.setVisibility(View.INVISIBLE);
            holder.todo_title.setVisibility(View.GONE);
            holder.todo_checked.setVisibility(View.GONE);
            holder.todo_input.setVisibility(View.VISIBLE);
            holder.todo_save.setVisibility(View.VISIBLE);
            lastEdit = position;
        }
    }

    public void turnOutEdit(){
        if(lastEdit==-1) return;
        View view = mListView.getChildAt(lastEdit - mListView.getFirstVisiblePosition());
        ViewHolder holder = (ViewHolder) view.getTag();
        initHolder(view,holder);
        holder.todo_title.setText(holder.todo_input.getText());
        holder.todo_edit.setVisibility(View.VISIBLE);
        holder.todo_title.setVisibility(View.VISIBLE);
        holder.todo_checked.setVisibility(View.VISIBLE);
        holder.todo_input.setVisibility(View.GONE);
        holder.todo_save.setVisibility(View.GONE);
        lastEdit=-1;
    }

    public void finish(){
        if(notfinish==0){//若不存在未完成待办
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(now);
            int num = getNum(date);
            if(num>0){//当前存在待办事项
                int log_id = isExist(0,date);
                if(log_id==-1){//首次完成所有待办，历程未创建
                    int day = getTododay()+1;
                    setTododay(day);//用户信息更新完成待办他减数
                    global.setLog(0, "完成" + num + "项待办", date);//创建完成待办历程
                    int  pos= 0;
                    for(;pos<prize.length;pos++)
                        if(prize[pos]==day) break;
                    if(pos!=prize.length){//完成待办天数达到获取成就标准
                        setAchieve(2*pos+1);//更新用户成就信息
                        String content = "达成成就”"+global.getAchieve().get(2*pos).getName()+"”";
                        global.setLog(2, content,date);//创建获得成就历程
                        Toast.makeText(getContext(),content,Toast.LENGTH_SHORT).show();
                    }
                }else {//非首次完成
                    updateLog(log_id,"完成" + num + "项待办");//更新历程
                }
                Toast.makeText(getContext(),"完成所有待办！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //调用接口
    //修改title
    private void editTodo(int todo_id,String title){
        try {
            URL u = new URL(global.getURL() + "/todo/edit");
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
            String content = "{\"todo_id\":" + todo_id +
                    ",\"title\":\""+title+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "修改失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //设置check
    private void editTodo(int todo_id,int check){
        try {
            URL u = new URL(global.getURL() + "/todo/check");
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
            String content = "{\"todo_id\":" + todo_id +
                    ",\"isChecked\":"+check+"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
//                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "修改失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //获取事项数
    private int getNum(String date){
        try {
            URL u = new URL(global.getURL() + "/todo/number");
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
            String content = "{\"user_id\":\"" + global.getUserId() +
                    "\",\"date\":\""+date+"\"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    return res.getJSONArray("data").getJSONObject(0).optInt("count(*)");
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
        return -1;
    }

    //获取tododay
    private int getTododay(){
        try {
            URL u = new URL(global.getURL() + "/user/getDay");
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
                    return res.getJSONArray("data").getJSONObject(0).optInt("todo_day");
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
        return -1;
    }

    //设置tododay
    private void setTododay(int todo_day){
        try {
            URL u = new URL(global.getURL() + "/user/setDay");
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
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"todo_day\":"+todo_day+"}";
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
                Toast.makeText(getContext(), "刷新信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //更新todo成就
    private void setAchieve(int prize_todo){
        try {
            URL u = new URL(global.getURL() + "/achieve/todo");
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
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"prize_todo\":"+prize_todo+"}";
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
                Toast.makeText(getContext(), "更新成就失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //log是否存在
    private int isExist(int type,String date){
        try {
            URL u = new URL(global.getURL() + "/log/isExist");
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
            String content = "{\"user_id\":\"" + global.getUserId() + "\",\"date\":\""+date +"\",\"type\":"+type+"}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    return res.getJSONArray("data").getJSONObject(0).optInt("log_id");
                } else if(code==201){
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

    //更新log
    private void updateLog(int log_id, String name){
        try {
            URL u = new URL(global.getURL() + "/log/update");
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
            String content = "{\"log_id\":" + log_id + ",\"name\":\""+name +"\"}";
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

    private void initHolder(View v, ViewHolder vh){
        vh.todo_edit = (ImageView)v.findViewById(R.id.todo_edit);
        vh.todo_title = (TextView) v.findViewById(R.id.todo_title);
        vh.todo_checked = (CheckBox) v.findViewById(R.id.todo_checked);
        vh.todo_input = (EditText) v.findViewById(R.id.todo_input);
        vh.todo_save = (ImageView)v.findViewById(R.id.todo_save);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }

    public void setNotfinish(int notfinish) {
        this.notfinish = notfinish;
    }

    public int getNotfinish() {
        return notfinish;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return clickable;
    }

    class ViewHolder{
        ImageView todo_edit;
        TextView todo_title;
        CheckBox todo_checked;
        EditText todo_input;
        ImageView todo_save;
    }
}
