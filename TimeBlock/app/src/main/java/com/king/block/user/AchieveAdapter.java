package com.king.block.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.king.block.R;

import java.util.List;

public class AchieveAdapter extends BaseAdapter {
    private Context context;
    private List<Achieve> mList;

    private Achieve achieve;
    private AchieveAdapter.ViewHolder viewHolder;
    public AchieveAdapter(Context context, int textViewResourceId, List<Achieve> achieve_list){
        this.context=context;
        this.mList = achieve_list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        achieve = (Achieve)getItem(position);
        viewHolder = null;
        if(convertView == null || convertView.getTag() != null){
//            convertView = View.inflate(context,R.layout.item_achieve, null);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_achieve, parent,false);
            viewHolder = new AchieveAdapter.ViewHolder();
            initHolder(convertView,viewHolder);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (AchieveAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(achieve.getName());
        switch (achieve.getType()){
            case 0:viewHolder.cup.setImageResource(R.drawable.gold_plan);break;
            case 1:viewHolder.cup.setImageResource(R.drawable.silver_plan);break;
            case 2:viewHolder.cup.setImageResource(R.drawable.copper_plan);break;
            case 3:viewHolder.cup.setImageResource(R.drawable.gold_todo);break;
            case 4:viewHolder.cup.setImageResource(R.drawable.silver_todo);break;
            case 5:viewHolder.cup.setImageResource(R.drawable.copper_todo);break;
            default:break;
        }
        return convertView;
    }

    private void initHolder(View v, AchieveAdapter.ViewHolder vh){
        vh.name = (TextView) v.findViewById(R.id.name);
        vh.cup = (ImageView) v.findViewById(R.id.cup);
    }

    class ViewHolder{
        TextView name;
        ImageView cup;
    }
}

