package com.king.block.user;

public class Chart {
    private String date;
    private int[] pass = new int[4];

    public Chart(String date, int[] pass) {
        this.date = date;
        this.pass = pass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int[] getPass() {
        return pass;
    }

    public void setPass(int[] pass) {
        this.pass = pass;
    }
}
