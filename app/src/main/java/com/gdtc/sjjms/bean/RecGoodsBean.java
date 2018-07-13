package com.gdtc.sjjms.bean;

import java.io.Serializable;

public class RecGoodsBean implements Serializable {
    private String imgUrl = "";
    private String goodsName = "";
    private String serviceTime = "";
    private String score = "";
    private String soldOut = "";

    public RecGoodsBean(String imgUrl, String goodsName, String serviceTime, String score, String soldOut) {
        this.imgUrl = imgUrl;
        this.goodsName = goodsName;
        this.serviceTime = serviceTime;
        this.score = score;
        this.soldOut = soldOut;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }
}
