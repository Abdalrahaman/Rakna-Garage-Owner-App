package com.example.garage_owner.presenter.Islam;

public class Data {
    private String name, number, imgurl, rate;

    public Data(String name, String number, String imgurl, String rate) {
        this.name = name;
        this.number = number;
        this.imgurl = imgurl;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getRate(){
        return rate;
    }

}