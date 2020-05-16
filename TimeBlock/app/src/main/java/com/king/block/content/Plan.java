package com.king.block.content;

public class Plan {
    int id;
    String title;
    String content;
    int urgency;//紧急度 0,1,2,3
    String pass;//进行时间
    String date;
    String time;

    public Plan(int id, String title, String content, int urgency, String pass, String date, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.urgency = urgency;
        this.pass = pass;
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

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
