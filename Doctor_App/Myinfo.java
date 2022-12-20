package com.example.d_hopeapp;

import java.io.Serializable;

/**
 * Created by 연진 on 2017-08-19.
 */

public class Myinfo implements Serializable {
    private String id;
    private String name;

    public Myinfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Myinfo(){

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
}
