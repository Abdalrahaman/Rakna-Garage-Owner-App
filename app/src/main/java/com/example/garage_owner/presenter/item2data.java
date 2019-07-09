package com.example.garage_owner.presenter;

public class item2data {
    private String name, number, date, from_time, to_time, imgurl;
    private double cost, rate;

    public item2data(String name, String number, String date, String from_time, String to_time, String imgurl, double cost, double rate) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.from_time = from_time;
        this.to_time = to_time;
        this.imgurl = imgurl;
        this.cost = cost;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getFrom_time() {
        return from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public String getImgurl() {
        return imgurl;
    }

    public double getCost() {
        return cost;
    }

    public double getRate() {
        return rate;
    }
}
//for commiting
