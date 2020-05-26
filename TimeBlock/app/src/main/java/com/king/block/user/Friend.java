package com.king.block.user;

import com.king.block.Global;

public class Friend{
    private String id;
    private String name;
//    private String avatar;
    private String time;

    public Friend(String id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
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

//    public String getAvatar() {
//        return avatar;
//    }

//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
