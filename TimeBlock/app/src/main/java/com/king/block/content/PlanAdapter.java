package com.king.block.content;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.king.block.R;

import java.util.List;

public class PlanAdapter extends ArrayAdapter<Plan> {
    private int resourceId;
    public int now_index = -1;
    private int newResourceId;
    private ListView mListView;


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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PlanAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.plan_title.setText(plan.getTitle());
        viewHolder.plan_content.setText(plan.getContent());
        viewHolder.plan_ddl.setText(plan.getDate()+" "+plan.getTime());
        changeColor(viewHolder,plan.getUrgency());
        return convertView;
    }

    private void changeColor(ViewHolder vh,int urgency){
        switch (urgency){
            case 0:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.red));
                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.red));
                break;
            case 1:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.yellow));
                break;
            case 2:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.green));
                break;
            case 3:
                vh.urgency.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                vh.plan_ddl.setTextColor(getContext().getResources().getColor(R.color.blue));
                break;
                default:break;
        }
    }

    private void initHolder(View v, PlanAdapter.ViewHolder vh) {
        vh.urgency = (TextView) v.findViewById(R.id.urgency);
        vh.plan_title = (TextView) v.findViewById(R.id.plan_title);
        vh.plan_content = (TextView) v.findViewById(R.id.plan_content);
        vh.plan_ddl = (TextView) v.findViewById(R.id.plan_ddl);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }

    class ViewHolder {
        TextView urgency;
        TextView plan_title;
        TextView plan_content;
        TextView plan_ddl;
    }
}