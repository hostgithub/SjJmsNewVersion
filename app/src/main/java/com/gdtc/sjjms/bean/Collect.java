package com.gdtc.sjjms.bean;

import java.io.Serializable;

/**
 * Created by wangjiawei on 2018-7-23.
 */

public class Collect implements Serializable{

    private String success;
    private String openId;
    private String information;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
