package com.king.block.content;

public class Todo {
    private int id;
    private String title;
    private boolean isChecked;
    private String date;//创建日期

    public Todo(int id, String title, boolean isChecked, String date) {
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
