package com.king.block.content;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.king.block.Global;
import com.king.block.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlanAdapter extends ArrayAdapter<Plan> {
    private int resourceId;
    private int on = 1;
    private int nowId = -1;


    private int newResourceId;
    private ListView mListView;
    private List<Plan> mList;
    Global global;

    private Handler handler;

    public PlanAdapter(Context context, int resourceId, List<Plan> plan_list, Handler handler) {
        super(context, resourceId, plan_list);
        this.handler = handler;
        newResourceId = resourceId;
    }

    private Plan plan;
    private PlanAdapter.ViewHolder viewHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        plan = (Plan) getItem(position);
        viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_plan, parent, false);
            viewHolder = new PlanAdapter.ViewHolder();
            initHolder(convertView, viewHolder);
            viewHolder.func.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View bsdv = LayoutInflater.from(getContext()).inflate(R.layout.dialog_plan,null);
                    final BottomSheetDialog bsd = new BottomSheetDialog(getContext());
                    bsd.setContentView(bsdv);
                    //给布局设置透明背景色，让图片突出来
                    bsd.getDelegate().findViewById(R.id.design_bottom_sheet)
                            .setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
                    bsd.show();

                    TextView update = (TextView) bsdv.findViewById(R.id.update);
                    TextView delete= (TextView) bsdv.findViewById(R.id.delete);
                    TextView finish= (TextView) bsdv.findViewById(R.id.finish);
                    TextView cancel= (TextView) bsdv.findViewById(R.id.cancel);

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Plan plan = mList.get(position);
                            Intent it = new Intent(getContext(), PlanActivity.class);
                            it.putExtra("plan_id", plan.getId());
                            it.putExtra("plan_title", plan.getTitle());
                            it.putExtra("plan_content", plan.getContent());
                            it.putExtra("urgency", plan.getUrgency());
//                            it.putExtra("plan_date", plan.getDate());
//                            it.putExtra("plan_time", plan.getTime());
                            getContext().startActivity(it);
                            bsd.dismiss();
                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int del_id = mList.get(position).getId();
                            delete(del_id);
                            mList.remove(position);
                            notifyDataSetChanged();
//                                planAdapter.setListView(plan_lv);
//                                    planAdapter = new PlanAdapter(getActivity(), R.layout.item_plan, mList);
//                                    mListView.setAdapter(planAdapter);
                            bsd.dismiss();
                            initNow();
                        }
                    });

                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int del_id = mList.get(position).getId();
                            if(del_id==nowId&&on==0){
                                Toast.makeText(getContext(),"请先暂停该计划",Toast.LENGTH_SHORT).show();
                                return ;
                            }
                            String pass = mList.get(position).getPass();
                            String title = mList.get(position).getTitle();
                            finish(del_id,pass,title);
                            mList.remove(position);
                            notifyDataSetChanged();
                            bsd.dismiss();
                            initNow();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bsd.dismiss();
                        }
                    });
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PlanAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.plan_title.setText(plan.getTitle());
        viewHolder.plan_content.setText(plan.getContent());
//        viewHolder.plan_ddl.setText(plan.getDate() + " " + plan.getTime());
        changeColor(viewHolder, plan.getUrgency());
        return convertView;
    }


    //调用接口
    //删除
    private void delete(int del_id){
        try {
            URL url = new URL(global.getURL() + "/plan/delete");
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
            String content = "{\"plan_id\":" + del_id  + "}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "删除失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    //完成
    private void finish(int plan_id,String pass,String title){
        try {
            URL url = new URL(global.getURL() + "/plan/finish");
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
            String content = "{\"plan_id\":" + plan_id  + "}";
            out.write(content.getBytes());
            out.flush();
            out.close();

            if (con.getResponseCode() == 200) {
                JSONObject res = global.streamtoJson(con.getInputStream());
                int code = res.optInt("code");
                String msg = res.optString("msg");
                if (code == 200) {
                    Toast.makeText(getContext(), "完成计划！", Toast.LENGTH_SHORT).show();
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(now);
                    if(pass.contains("h")){
                        pass = pass.replace("h","小时");
                    }
                    pass = pass.replace("m","分钟");
                    global.setLog(0, "经过"+pass+"，完成计划“"+title+"”", date);
                } else {
                    Toast.makeText(getContext(), msg + res.getString("err"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "勾选失败" + con.getErrorStream().toString(), Toast.LENGTH_SHORT).show();
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void initNow(){
        Message msg = new Message();
//        msg.what = clickIndex;
//        msg.arg1 = position;
        Bundle b = new Bundle();
//        b.putInt(POSITION, position);
        msg.setData(b);
        handler.sendMessage(msg);
    }

    private void changeColor(ViewHolder vh,int urgency){
        switch (urgency){
            case 0:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.red));
//                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.red));
                break;
            case 1:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
//                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.yellow));
                break;
            case 2:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.green));
//                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.green));
                break;
            case 3:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
//                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.blue));
                break;
                default:break;
        }
    }

    private void initHolder(View v, PlanAdapter.ViewHolder vh) {
        vh.urgency = (TextView) v.findViewById(R.id.urgency);
        vh.plan_title = (TextView) v.findViewById(R.id.plan_title);
        vh.plan_content = (TextView) v.findViewById(R.id.plan_content);
//        vh.plan_ddl = (TextView) v.findViewById(R.plan_id.plan_ddl);
        vh.func = (ImageView) v.findViewById(R.id.function);
//        vh.delete = (TextView)v.findViewById(R.plan_id.delete);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }

    public List<Plan> getList() {
        return mList;
    }

    public void setList(List<Plan> mList) {
        this.mList = mList;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public void setNowId(int nowId) {
        this.nowId = nowId;
    }

    class ViewHolder {
        TextView urgency;
        TextView plan_title;
        TextView plan_content;
//        TextView plan_ddl;
        ImageView func;
//        TextView delete;
    }
}