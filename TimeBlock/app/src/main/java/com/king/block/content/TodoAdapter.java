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

import com.king.block.R;

import java.util.List;


public class TodoAdapter extends ArrayAdapter<Todo>{
    private int resourceId;
    public int now_index=-1;
    private int newResourceId;
    private ListView mListView;


    public TodoAdapter(Context context, int resourceId, List<Todo> todo_list){
        super(context, resourceId, todo_list);
        newResourceId = resourceId;
    }

    Todo todo;
    View view;
    ViewHolder viewHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        todo = (Todo) getItem(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(newResourceId, parent, false);
            viewHolder = new ViewHolder();
            initHolder(view,viewHolder);
//            viewHolder.todo_edit = (ImageView)view.findViewById(R.id.todo_edit);
//            viewHolder.todo_title = (TextView) view.findViewById(R.id.todo_title);
//            viewHolder.todo_checked = (CheckBox) view.findViewById(R.id.todo_checked);
//            viewHolder.todo_input = (EditText) view.findViewById(R.id.todo_input);
//            viewHolder.todo_update = (ImageView)view.findViewById(R.id.todo_update);
            viewHolder.todo_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    turnInEdit(position);
                }
            });
//            viewHolder.todo_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    changeStyle();
//                    if (buttonView.isChecked()) {
//                        buttonView.setPaintFlags(viewHolder.todo_title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                        buttonView.setTextColor(Color.parseColor("#000000"));
//                    } else {
//                        buttonView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                        buttonView.setTextColor(Color.parseColor("#bfbfbf"));
//                    }
//                    notifyDataSetChanged();
//                }
//            });
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
//        if(now_index==position){
//            Toast.makeText(getContext(),now_index+"",Toast.LENGTH_SHORT);

//            }


        viewHolder.todo_title.setText(todo.getTitle());
        viewHolder.todo_checked.setChecked(todo.isChecked());
        return view;
    }
    private void turnInEdit(int position){
        View view = mListView.getChildAt(position - mListView.getFirstVisiblePosition());
        ViewHolder holder = (ViewHolder) view.getTag();
        initHolder(view,holder);
        holder.todo_edit.setVisibility(View.INVISIBLE);
        holder.todo_title.setVisibility(View.GONE);
        holder.todo_checked.setVisibility(View.GONE);
        holder.todo_input.setVisibility(View.VISIBLE);
        holder.todo_update.setVisibility(View.VISIBLE);
    }
    private void turnOutEdit(){

    }

    private void initHolder(View v, ViewHolder vh){
        vh.todo_edit = (ImageView)v.findViewById(R.id.todo_edit);
        vh.todo_title = (TextView) v.findViewById(R.id.todo_title);
        vh.todo_checked = (CheckBox) v.findViewById(R.id.todo_checked);
        vh.todo_input = (EditText) v.findViewById(R.id.todo_input);
        vh.todo_update = (ImageView)v.findViewById(R.id.todo_update);
    }

    private void changeStyle(){
        Toast.makeText(getContext(),viewHolder.todo_title.toString(),Toast.LENGTH_SHORT).show();
        if(todo.isChecked()){
            viewHolder.todo_title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.todo_title.setTextColor(Color.parseColor("#bfbfbf"));
        }else{
            viewHolder.todo_title.setPaintFlags(viewHolder.todo_title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.todo_title.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void update(int position, ListView lv) {
        int firstVisiblePosition = lv.getFirstVisiblePosition();
        //获取对应item的View对象
        View view = lv.getChildAt(position - firstVisiblePosition);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.todo_title = (TextView) view.findViewById(R.id.todo_title);
        holder.todo_checked = (CheckBox) view.findViewById(R.id.todo_checked);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }

    class ViewHolder{
        ImageView todo_edit;
        TextView todo_title;
        CheckBox todo_checked;
        EditText todo_input;
        ImageView todo_update;
    }
}
