package com.king.block.content;

public class Plan {
    int id;
    String plan_title;
    String plan_content;
    int urgency;
    String plan_date;
    String plan_ddl;
    String plan_time;

    public Plan(int id, String plan_title, String plan_content, int urgency, String plan_date, String plan_ddl, String plan_time) {
        this.id = id;
        this.plan_title = plan_title;
        this.plan_content = plan_content;
        this.urgency = urgency;
        this.plan_date = plan_date;
        this.plan_ddl = plan_ddl;
        this.plan_time = plan_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlan_title() {
        return plan_title;
    }

    public void setPlan_title(String plan_title) {
        this.plan_title = plan_title;
    }

    public String getPlan_content() {
        return plan_content;
    }

    public void setPlan_content(String plan_content) {
        this.plan_content = plan_content;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getPlan_ddl() {
        return plan_ddl;
    }

    public void setPlan_ddl(String plan_ddl) {
        this.plan_ddl = plan_ddl;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
        this.plan_time = plan_time;
    }
}