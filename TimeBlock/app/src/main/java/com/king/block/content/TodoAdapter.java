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
                    changeStyle(holder,holder.todo_checked.isChecked());

                    Todo t = todo_list.get(position);
                    editTodo(t.getId(),holder.todo_checked.isChecked()?1:0);
                    if(holder.todo_checked.isChecked()){
                        notfinish--;
                    }else{
                        notfinish++;
                    }
                    if(notfinish==0){
                        Date now = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        int num = getNum(sdf.format(now));
                        if(num>0){
                            Toast.makeText(getContext(),"完成今日全部"+num+"项待办",Toast.LENGTH_SHORT).show();
                        }
                    }
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
            out.writeBytes(content);
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
                Toast.makeText(getContext(), "刷新待办信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
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
            out.writeBytes(content);
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
                Toast.makeText(getContext(), "刷新待办信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
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
            out.writeBytes(content);
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
                Toast.makeText(getContext(), "刷新待办信息失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
        return -1;
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
