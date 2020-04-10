package com.king.block.content;

import android.content.Context;
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

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private int resourceId;
    public int now_index = -1;
    private int newResourceId;
    private ListView mListView;


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
           
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NoteAdapter.ViewHolder) convertView.getTag();
        }
        String avatar = note.getTitle().charAt(0)+"";
        viewHolder.note_avatar.setText(avatar);
        viewHolder.note_title.setText(note.getTitle());
        viewHolder.note_content.setText(note.getContent());
        viewHolder.note_time.setText(note.getTime());
        viewHolder.note_place.setText(note.getPlace());
        return convertView;
    }

    private void initHolder(View v, NoteAdapter.ViewHolder vh) {
        vh.note_avatar = (TextView) v.findViewById(R.id.note_avatar);
        vh.note_title = (TextView) v.findViewById(R.id.note_title);
        vh.note_content = (TextView) v.findViewById(R.id.note_content);
        vh.note_time = (TextView) v.findViewById(R.id.note_time);
        vh.note_place = (TextView) v.findViewById(R.id.note_place);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }

    class ViewHolder {
        TextView note_avatar;
        TextView note_title;
        TextView note_content;
        TextView note_time;
        TextView note_place;
    }
}