package com.king.block.user;

public class Log {
    int id;
    int type;//0-待办，1-计划，2-成就
    int logId;//对应事项id

    public Log(int id, int type, int logId) {
        this.id = id;
        this.type = type;
        this.logId = logId;
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

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }
}
