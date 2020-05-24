package com.king.block.content;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.king.block.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private int resourceId;
    public int now_index = -1;
    private int newResourceId;
    private ListView mListView;
    private List<Note> note_list;

    private int colors[]= {R.drawable.note0,
            R.drawable.note1,
            R.drawable.note2,
            R.drawable.note3,
            R.drawable.note4,
            R.drawable.note5};

    public NoteAdapter(Context context, int resourceId, List<Note> note_list) {
        super(context, resourceId, note_list);
        newResourceId = resourceId;
    }

    private Note note;
    private NoteAdapter.ViewHolder viewHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        note = (Note) getItem(position);
        viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
            viewHolder = new NoteAdapter.ViewHolder();
            initHolder(convertView, viewHolder);
            viewHolder.note_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = note_list.get(position);
                    Intent it = new Intent(getContext(), NoteActivity.class);
                    it.putExtra("id",note.getId());
                    it.putExtra("note_title",note.getTitle());
                    it.putExtra("note_content",note.getContent());
                    it.putExtra("note_place",note.getPlace());
                    it.putExtra("note_date",note.getDate());
                    it.putExtra("note_time",note.getTime());
                    getContext().startActivity(it);
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NoteAdapter.ViewHolder) convertView.getTag();
        }
        String avatar = note.getTitle().charAt(0)+"";
        viewHolder.note_avatar.setText(avatar);
        String d[] = note.getDate().split("-");
        int pos = (Integer.parseInt(d[0])+Integer.parseInt(d[1])+Integer.parseInt(d[2]))%6;
        viewHolder.note_avatar.setBackgroundResource(colors[pos]);
        viewHolder.note_title.setText(note.getTitle());
        viewHolder.note_content.setText(note.getContent());
        viewHolder.note_date.setText(note.getDate());
        viewHolder.note_time.setText(note.getTime());
        viewHolder.note_place.setText(note.getPlace());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        String now[] = sdf.format(new Date(System.currentTimeMillis())).split(" ");
        int status=0;//0过去，1今天，2未来
        for(int i=0;i<now[0].length();i++){
            if(now[0].charAt(i)>note.getDate().charAt(i)){
                status = 0;
                break;
            }else if(now[0].charAt(i)<note.getDate().charAt(i)){
                status = 2;
                break;
            }else if(i==now[0].length()-1){
                for(int j=0;j<now[1].length();j++){
                    if(now[1].charAt(j)>note.getTime().charAt(j)){
                        status = 0;
                        break;
                    }else if(now[1].charAt(j)<note.getTime().charAt(j)){
                        status = 1;
                        break;
                    }
                }
            }
        }
        switch (status){
            case 0:
                viewHolder.note_title.setTextColor(getContext().getResources().getColor(R.color.lightFontGray));
                viewHolder.note_content.setTextColor(getContext().getResources().getColor(R.color.lightFontGray));
                viewHolder.note_place.setTextColor(getContext().getResources().getColor(R.color.lightFontGray));
                viewHolder.note_date.setTextColor(getContext().getResources().getColor(R.color.lightFontGray));
                viewHolder.note_time.setTextColor(getContext().getResources().getColor(R.color.lightFontGray));
                break;
            case 1:
                viewHolder.note_title.setTextColor(getContext().getResources().getColor(R.color.red));
                viewHolder.note_content.setTextColor(getContext().getResources().getColor(R.color.red));
                viewHolder.note_place.setTextColor(getContext().getResources().getColor(R.color.red));
                viewHolder.note_date.setTextColor(getContext().getResources().getColor(R.color.red));
                viewHolder.note_time.setTextColor(getContext().getResources().getColor(R.color.red));
                break;
            case 2:
                viewHolder.note_title.setTextColor(getContext().getResources().getColor(R.color.black));
                viewHolder.note_content.setTextColor(getContext().getResources().getColor(R.color.gray));
                viewHolder.note_place.setTextColor(getContext().getResources().getColor(R.color.black));
                viewHolder.note_date.setTextColor(getContext().getResources().getColor(R.color.black));
                viewHolder.note_time.setTextColor(getContext().getResources().getColor(R.color.black));
                break;
            default:break;
        }

        return convertView;
    }

    private void initHolder(View v, NoteAdapter.ViewHolder vh) {
        vh.note_avatar = (TextView) v.findViewById(R.id.note_avatar);
        vh.note_title = (TextView) v.findViewById(R.id.note_title);
        vh.note_content = (TextView) v.findViewById(R.id.note_content);
        vh.note_date = (TextView) v.findViewById(R.id.note_date);
        vh.note_time = (TextView) v.findViewById(R.id.note_time);
        vh.note_place = (TextView) v.findViewById(R.id.note_place);
        vh.note_edit = (ImageView)v.findViewById(R.id.note_edit);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }

    public List<Note> getList() {
        return note_list;
    }

    public void setList(List<Note> note_list) {
        this.note_list = note_list;
    }

    class ViewHolder {
        TextView note_avatar;
        TextView note_title;
        TextView note_content;
        TextView note_date;
        TextView note_time;
        TextView note_place;
        ImageView note_edit;
    }
}