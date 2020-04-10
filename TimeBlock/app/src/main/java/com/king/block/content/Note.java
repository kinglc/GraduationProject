package com.king.block.content;

import java.sql.Time;

public class Note {
    private int id;
    private String title;
    private String content;
    private String time;
    private String place;

    public Note(int id, String title, String content, String time, String place) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
