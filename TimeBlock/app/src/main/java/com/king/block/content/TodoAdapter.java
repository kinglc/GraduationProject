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
    private int lastEdit=-1;
    private ListView mListView;


    public TodoAdapter(Context context, int resourceId, List<Todo> todo_list){
        super(context, resourceId, todo_list);
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
                    if(lastEdit>-1){
                        turnOutEdit();
                    }
                    if(!viewHolder.todo_checked.isChecked()) {
                        turnInEdit(position);
                        lastEdit = position;
                    }
                }
            });
            viewHolder.todo_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    turnOutEdit();
                    //未完成-更新数据
//                    updateTodo(position);
                }
            });
            viewHolder.todo_checked.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(viewHolder.todo_checked.isChecked()){
                        Toast.makeText(getContext(),"checked",Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(getContext(),"not checked",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(todo.isChecked()){
            viewHolder.todo_title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.todo_title.setTextColor(Color.parseColor("#bfbfbf"));
        }else{
            viewHolder.todo_title.setPaintFlags(viewHolder.todo_title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.todo_title.setTextColor(Color.parseColor("#000000"));
        }
        viewHolder.todo_title.setText(todo.getTitle());
        viewHolder.todo_checked.setChecked(todo.isChecked());
        return convertView;
    }

    private void turnInEdit(int position){
        View view = mListView.getChildAt(position - mListView.getFirstVisiblePosition());
        ViewHolder holder = (ViewHolder) view.getTag();
        initHolder(view,holder);
        holder.todo_input.setText(holder.todo_title.getText());
        holder.todo_edit.setVisibility(View.INVISIBLE);
        holder.todo_title.setVisibility(View.GONE);
        holder.todo_checked.setVisibility(View.GONE);
        holder.todo_input.setVisibility(View.VISIBLE);
        holder.todo_save.setVisibility(View.VISIBLE);
    }

    public void turnOutEdit(){
        View view = mListView.getChildAt(lastEdit - mListView.getFirstVisiblePosition());
        ViewHolder holder = (ViewHolder) view.getTag();
        initHolder(view,holder);
        holder.todo_title.setText(holder.todo_input.getText());
//        updateView(view,lastEdit,holder.todo_title.getText().toString(),holder.todo_checked.isChecked());
//        todo.setTitle(holder.todo_title.getText().toString());
        holder.todo_edit.setVisibility(View.VISIBLE);
        holder.todo_title.setVisibility(View.VISIBLE);
        holder.todo_checked.setVisibility(View.VISIBLE);
        holder.todo_input.setVisibility(View.GONE);
        holder.todo_save.setVisibility(View.GONE);
        lastEdit=-1;
    }


    private void initHolder(View v, ViewHolder vh){
        vh.todo_edit = (ImageView)v.findViewById(R.id.todo_edit);
        vh.todo_title = (TextView) v.findViewById(R.id.todo_title);
        vh.todo_checked = (CheckBox) v.findViewById(R.id.todo_checked);
        vh.todo_input = (EditText) v.findViewById(R.id.todo_input);
        vh.todo_save = (ImageView)v.findViewById(R.id.todo_save);
    }

//    public void updateView(View view, int itemIndex,String title,boolean checked) {
//        if (view == null) {
//            return;
//        }
//        //从view中取得holder
//        ViewHolder holder = (ViewHolder) view.getTag();
//        initHolder(view,holder);
//        holder.todo_title.setText(title);
//        holder.todo_checked.setChecked(checked);
//    }

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
        ImageView todo_save;
    }
}
