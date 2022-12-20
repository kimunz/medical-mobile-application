package com.hope.patient.p_hopeapp;

import java.io.Serializable;

/**
 * Created by eunji on 2017. 8. 13..
 */

public class Myinfo implements Serializable {
    private String pID = "";
    private String pName = "";
    private String dID = "";
    private String dName = "";
    private String pRoomNum = "";
    private String pGender = "";
    private String pAge = "";
    private String pBirth = "";
    private String pDisease = "";
    private String pForbidFood = "";

    public String getpID() {
        return pID;
    }

    public String getpName() { return pName; }

    public String getdID() {
        return dID;
    }

    public String getdName() {
        return dName;
    }

    public String getpRoomNum() {
        return pRoomNum;
    }

    public String getpGender() {
        return pGender;
    }

    public String getpAge() {
        return pAge;
    }

    public String getpBirth() {
        return pBirth;
    }

    public String getpDiseaseName() {
        return pDisease;
    }

    public String getpForbidFood() { return pForbidFood; }

    public void setpID(String pID) { this.pID = pID;  }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setdID(String dID) { this.dID = dID;  }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public void setpRoomNum(String pRoomNum) {
        this.pRoomNum = pRoomNum;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender;
    }

    public void setpAge(String pAge) {
        this.pAge = pAge;
    }

    public void setpBirth(String pBirth) {
        this.pBirth = pBirth;
    }

    public void setpDiseaseName(String pDiseaseName) {
        this.pDisease = pDiseaseName;
    }

    public void setpForbidFood(String pForbidFood) {
        this.pForbidFood = pForbidFood;
    }
}
