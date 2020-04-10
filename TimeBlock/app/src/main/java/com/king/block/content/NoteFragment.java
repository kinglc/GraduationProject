package com.king.block.content;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.king.block.ContentActivity;
import com.king.block.MainActivity;
import com.king.block.R;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {
    View view;
    private ListView note_lv;
    private NoteAdapter noteAdapter;
    public static List<Note> note_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note, container, false);
        initData();
        initLv();
        initEvent();
        return view;
    }

    private void initEvent() {
        noteAdapter = new NoteAdapter(getActivity(), R.layout.item_note, note_list);
        note_lv =(ListView) view.findViewById(R.id.note_lv);
        note_lv.setAdapter(noteAdapter);
        noteAdapter.setListView(note_lv);
        note_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent it = new Intent(getContext(), NoteActivity.class);
                startActivity(it);
            }
        });
    }

    private void initLv() {
        
    }


    private void initData(){
        //未完成 获取数据
        for(int i=0;i<10;i++) {
            Note note = new Note(i,"备忘"+i, "世味年来薄似纱，谁令骑马客京华。小楼一夜听春雨，深巷明朝卖杏花。矮纸斜行闲作草，晴窗细乳戏分茶。","11:00","这里的山路十八弯");
            note_list.add(note);
        }
    }
    
    

    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }
}
