package com.hope.patient.p_hopeapp;

import java.io.Serializable;

/**
 * Created by eunji on 2017. 8. 17..
 */

public class QnAItem implements Serializable {

    private String q_date;
    private String body;
    private String isRead;

    public void setQDate(String q_date) {
        this.q_date = q_date;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getQDate() {
        return this.q_date;
    }

    public String getBody() {
        return this.body;
    }

    public String getIsRead() { return this.isRead; }
}