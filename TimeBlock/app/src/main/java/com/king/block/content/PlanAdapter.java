package com.king.block.content;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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
import com.king.block.R;

import java.util.List;

public class PlanAdapter extends ArrayAdapter<Plan> {
    private int resourceId;
    public int now_index = -1;
    private int newResourceId;
    private ListView mListView;
    private List<Plan> mList;


    public PlanAdapter(Context context, int resourceId, List<Plan> plan_list) {
        super(context, resourceId, plan_list);
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
                            mList.remove(position);
                            notifyDataSetChanged();
//                                planAdapter.setListView(plan_lv);
//                                    planAdapter = new PlanAdapter(getActivity(), R.layout.item_plan, mList);
//                                    mListView.setAdapter(planAdapter);
//                               未完成-删除数据库
//                                int delete = DataSupport.deleteAll(Ddl.class, "task = ? and content = ?", del1, del2);
                            bsd.dismiss();
                        }
                    });

                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //未完成
                            bsd.dismiss();
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

    class ViewHolder {
        TextView urgency;
        TextView plan_title;
        TextView plan_content;
//        TextView plan_ddl;
        ImageView func;
//        TextView delete;
    }
}