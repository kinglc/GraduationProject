package com.king.block.content;

import java.sql.Time;

public class Note {
    private int id;
    private String title;
    private String content;
    private String place;
    private String date;
    private String time;

    public Note(int id, String title, String content, String place, String date, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.place = place;
        this.date = date;
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
