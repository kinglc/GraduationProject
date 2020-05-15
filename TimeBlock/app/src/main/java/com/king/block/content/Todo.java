package com.king.block.content;

public class Todo {
    private int id;
    private String title;
    private boolean isChecked;
    private String date;//创建日期
    private String time;//完成时间

    public Todo(int id, String title, boolean isChecked, String date, String time) {
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
        this.date = date;
        this.time = time;
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

    public Todo(int id, String title, boolean isChecked) {
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
