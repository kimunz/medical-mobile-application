package com.hope.patient.p_hopeapp;

/**
 * Created by eunji on 2017. 8. 16..
 */

public class RoundsItem {

    private int color;
    private String location ;
    private String desc;

    public void setColor(int color) {
        this.color = color ;
    }
    public void setLocation(String location) {
        this.location = location ;
    }
    public void setDesc(String desc) {
        this.desc = desc ;
    }
    public int getColor(){ return this.color; }
    public String getLocation() {
        return this.location ;
    }
    public String getDesc() {
        return this.desc ;
    }

}
