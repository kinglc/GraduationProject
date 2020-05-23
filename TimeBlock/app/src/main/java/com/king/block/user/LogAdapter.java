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
    private List<Log> log_list;
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
        switch (log.getType()) {
            case 0:
                viewHolder.type.setImageResource(R.drawable.log_todo);
                break;
            case 1:
                viewHolder.type.setImageResource(R.drawable.log_plan);
                break;
            case 2:
                viewHolder.type.setImageResource(R.drawable.log_achieve);
                break;
            default:break;
        }
        viewHolder.date.setText(log.getDate().split("T")[0]);
        viewHolder.content.setText(log.getName());
        return convertView;
    }

    private void initHolder(View v, LogAdapter.ViewHolder vh){
        vh.type = (ImageView) v.findViewById(R.id.type);
        vh.content = (TextView) v.findViewById(R.id.content);
        vh.date = (TextView) v.findViewById(R.id.date);
    }

    public void setLog_list(List<Log> log_list) {
        this.log_list = log_list;
    }

    class ViewHolder{
        ImageView type;
        TextView content;
        TextView date;
    }
}
