package com.king.block.user;

public class Achieve {
    int id;
    int type;//0-2 金银铜计划，3-5 金银铜待办
    String name;
    String note;//点击时提示信息

    public Achieve(int id, int type, String name, String note) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
