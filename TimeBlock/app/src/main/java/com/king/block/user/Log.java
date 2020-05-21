package com.king.block.user;

public class Log {
    int id;
    int type;//0-待办，1-计划，2-成就
    String date;
    String name;

    public Log(int id, int type, String date, String name) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
