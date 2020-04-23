package com.king.block.user;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.king.block.R;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Friend>{
    private int resourceId;
    public int selectIndex = -1;
    private ListView mListView;

    private Friend friend;
    private ViewHolder viewHolder;
    public FriendAdapter(Context context,int textViewResourceId,List<Friend> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        friend = (Friend)getItem(position);
        viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_friend, parent, false);
            viewHolder = new ViewHolder();
            initHolder(convertView,viewHolder);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.rank.setText(position+"");
        viewHolder.name.setText(friend.getName());
        viewHolder.time.setText(friend.getTime());
        switch (position){
            case 0:viewHolder.rank.setTextColor(getContext().getColor(R.color.gold));break;
            case 1:viewHolder.rank.setTextColor(getContext().getColor(R.color.silver));break;
            case 2:viewHolder.rank.setTextColor(getContext().getColor(R.color.copper));break;
            default:viewHolder.rank.setTextColor(getContext().getColor(R.color.backGray));break;
        }
        return convertView;
    }

    private void initHolder(View v, ViewHolder vh){
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