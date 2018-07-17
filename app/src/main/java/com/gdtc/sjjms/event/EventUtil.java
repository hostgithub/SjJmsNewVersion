package com.gdtc.sjjms.event;

/**
 * Created by wangjiawei on 2018-1-26.
 */

public class EventUtil {
    private String msg;

    public EventUtil(String msg) {
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }
}
