package com.king.block.content;

public class Todo {
    private int id;
    private String title;
    private boolean isChecked;
    private String date;//创建日期
    private String finish;//完成时间

    public Todo(int id, String title, boolean isChecked, String date, String finish) {
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
        this.date = date;
        this.finish = finish;
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

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
