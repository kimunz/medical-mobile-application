package com.hope.patient.p_hopeapp;

/**
 * Created by eunji on 2017. 8. 17..
 */

public class RegisterItem {

    String cartegory = "";
    String volume = "";
    String date = "";
    String time = "";

    public String getCartegory(){
        return cartegory;
    }

    public void setCartegory(String cartegory){
        this.cartegory = cartegory;
    }

    public String getVolume(){
        return volume;
    }

    public void setVolume(String volume){
        this.volume = volume;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}
