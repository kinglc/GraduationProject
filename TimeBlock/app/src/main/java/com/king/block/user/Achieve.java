package com.king.block.user;

public class Achieve {
    int id;
    int type;//0-2 金银铜计划，3-5 金银铜待办
    String name;
    String time;//获取时间

    public Achieve(int id, int type, String name, String time) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.time = time;
    }

    public Achieve(int id, int type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
