package com.example.d_hopeapp;

import java.io.Serializable;

/**
 * Created by 연진 on 2017-08-28.
 */

public class PatientItem implements Serializable{
    private String id;
    private String name;
    private String gen;
    private String birth;
    private String room;
    private String disease;
    private String age;
    private String forbid_food;

    public String getpID() { return id; }

    public void setpID(String id) { this.id = id; }

    public String getpName() { return name; }

    public void setpName(String name) { this.name = name; }

    public String getpGen() { return gen; }

    public void setpGen(String gen) { this.gen = gen; }

    public String getpBirth() { return birth; }

    public void setpBirth(String birth) { this.birth = birth; }

    public String getpRoom() { return room; }

    public void setpRoom(String room) { this.room = room; }

    public String getpDisease() { return disease; }

    public void setpDisease(String disease) { this.disease = disease; }

    public String getpAge() { return age; }

    public void setpAge(String age) { this.age = age; }

    public String getForbid_food() { return forbid_food; }

    public void setForbid_food(String forbid_food) { this.forbid_food = forbid_food; }


}