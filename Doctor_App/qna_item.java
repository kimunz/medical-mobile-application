package com.example.d_hopeapp;

import java.io.Serializable;

/**
 * Created by eunji on 2017. 9. 8..
 */

public class qna_item implements Serializable {

    private String p_id;
    private String p_name;
    private String p_room;
    private String q_date;
    private String body;
    private String is_read;

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public void setQDate(String q_date) {
        this.q_date = q_date;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setP_name(String p_name) { this.p_name = p_name; }

    public void setP_room(String p_room) { this.p_room = p_room; }

    public void setIs_read(String is_read) { this.is_read = is_read; }

    public String getP_id() {
        return this.p_id;
    }

    public String getQDate() {
        return this.q_date;
    }

    public String getBody() {
        return this.body;
    }

    public String getP_name() { return p_name; }

    public String getP_room() { return p_room; }

    public String getIs_read() { return is_read; }

}
