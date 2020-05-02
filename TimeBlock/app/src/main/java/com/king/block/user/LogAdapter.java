package com.king.block.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.king.block.R;

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
        viewHolder.rank.setText(position+"");
        viewHolder.name.setText(log.getType());
        viewHolder.time.setText(log.getLogId());
        switch (position){
            case 0:viewHolder.rank.setTextColor(getContext().getColor(R.color.gold));break;
            case 1:viewHolder.rank.setTextColor(getContext().getColor(R.color.silver));break;
            case 2:viewHolder.rank.setTextColor(getContext().getColor(R.color.copper));break;
            default:viewHolder.rank.setTextColor(getContext().getColor(R.color.backGray));break;
        }
        return convertView;
    }

    private void initHolder(View v, LogAdapter.ViewHolder vh){
        vh.rank = (TextView) v.findViewById(R.id.rank);
        vh.name = (TextView) v.findViewById(R.id.name);
        vh.time = (TextView) v.findViewById(R.id.time);
    }

    class ViewHolder{
        TextView rank;
        TextView name;
        TextView time;
    }
}
