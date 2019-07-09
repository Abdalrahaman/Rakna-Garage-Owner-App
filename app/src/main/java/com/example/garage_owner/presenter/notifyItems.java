package com.example.garage_owner.presenter;

public class notifyItems {
    private String circleImgURL, statusImgURL, notiMssg, notiPeriod;

    public notifyItems(String circleImgURL, String statusImgURL, String notiMssg, String notiPeriod) {
        this.circleImgURL = circleImgURL;
        this.statusImgURL = statusImgURL;
        this.notiMssg = notiMssg;
        this.notiPeriod = notiPeriod;
    }

    public String getCircleImgURL() {
        return circleImgURL;
    }

    public String getStatusImgURL() {
        return statusImgURL;
    }

    public String getNotiMssg() {
        return notiMssg;
    }

    public String getNotiPeriod() {
        return notiPeriod;
    }
}
