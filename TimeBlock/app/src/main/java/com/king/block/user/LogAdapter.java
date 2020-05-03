package com.king.block.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.block.R;
import com.king.block.content.Plan;
import com.king.block.content.Todo;

import java.util.List;

public class LogAdapter extends ArrayAdapter<Log> {

    private int resourceId;
    private Log log;
    private LogAdapter.ViewHolder viewHolder;
    public LogAdapter(Context context, int textViewResourceId, List<Log> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        log = (Log)getItem(position);
        viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_log, parent, false);
            viewHolder = new LogAdapter.ViewHolder();
            initHolder(convertView,viewHolder);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (LogAdapter.ViewHolder) convertView.getTag();
        }
        //未完成-获取对应事项内容及事件
        int id = log.getLogId();
        switch (log.getType()) {
            case 0:
                viewHolder.type.setImageResource(R.drawable.log_todo);
                Todo todo = new Todo(1,"aaa",true,"2020-01-01","2020-01-01 01:01");
                viewHolder.time.setText(todo.getTime());
                viewHolder.content.setText("完成当日待办");
                break;
            case 1:
                viewHolder.type.setImageResource(R.drawable.log_plan);
                Plan plan = new Plan(1,"计划B","111",1,",,","2020-03-03 03:03","111","111");
                viewHolder.time.setText(plan.getFinish());
                viewHolder.content.setText("完成计划"+plan.getTitle());
                break;
            case 2:
                viewHolder.type.setImageResource(R.drawable.log_achieve);
                Achieve achieve = new Achieve(1,1,"成就C","2020-03-03 03:03");
                viewHolder.time.setText(achieve.getTime());
                viewHolder.content.setText("获得成就"+achieve.getName());
                break;
            default:break;
        }
        return convertView;
    }

    private void initHolder(View v, LogAdapter.ViewHolder vh){
        vh.type = (ImageView) v.findViewById(R.id.type);
        vh.content = (TextView) v.findViewById(R.id.content);
        vh.time = (TextView) v.findViewById(R.id.time);
    }

    class ViewHolder{
        ImageView type;
        TextView content;
        TextView time;
    }
}
