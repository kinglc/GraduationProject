package com.king.block.user;

import com.king.block.Global;

public class Friend implements Comparable<Friend>{
    private String id;
    private String name;
    private String avatar;
    private String time;
    private int min;

    public Friend(String id, String name, String avatar, String time) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.time = time;
    }



    @Override
    public int compareTo(Friend f) {
        return f.min - this.min;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
