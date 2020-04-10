//package com.king.block.content;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.king.block.R;
//
//import java.util.List;
//
//public class PlanAdapter extends ArrayAdapter<Plan> {
//    private int resourceId;
//    public int now_index = -1;
//    private int newResourceId;
//    private ListView mListView;
//
//
//    public PlanAdapter(Context context, int resourceId, List<Plan> plan_list) {
//        super(context, resourceId, plan_list);
//        newResourceId = resourceId;
//    }
//
//    private Plan plan;
//    private PlanAdapter.ViewHolder viewHolder;
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        plan = (Plan) getItem(position);
//        viewHolder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_plan, parent, false);
//            viewHolder = new PlanAdapter.ViewHolder();
//            initHolder(convertView, viewHolder);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (PlanAdapter.ViewHolder) convertView.getTag();
//        }
//        viewHolder.plan_title.setText(plan.getTitle());
//        viewHolder.plan_checked.setChecked(plan.isChecked());
//        return convertView;
//    }
//
//    private void initHolder(View v, PlanAdapter.ViewHolder vh) {
//        vh.todo_edit = (ImageView) v.findViewById(R.id.todo_edit);
//        vh.todo_title = (TextView) v.findViewById(R.id.todo_title);
//        vh.todo_checked = (CheckBox) v.findViewById(R.id.todo_checked);
//        vh.todo_input = (EditText) v.findViewById(R.id.todo_input);
//        vh.todo_save = (ImageView) v.findViewById(R.id.todo_save);
//    }
//
//    public ListView getListView() {
//        return mListView;
//    }
//
//    public void setListView(ListView mListView) {
//        this.mListView = mListView;
//    }
//
//    class ViewHolder {
//        ImageView todo_edit;
//        TextView todo_title;
//        CheckBox todo_checked;
//        EditText todo_input;
//        ImageView todo_save;
//    }
//}