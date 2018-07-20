package com.gdtc.sjjms.bean;

/**
 * Created by wangjiawei on 2018-7-19.
 */

public class UserInfo {


    /**
     * success : true
     */

    private String success;
    private String openId;
    private String image;
    private String name;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
