package com.gdtc.sjjms.event;

/**
 * Created by wangjiawei on 2018-1-26.
 */

public class EventUtil {

    private String id;
    private String street;

    public EventUtil(String msg,String street) {
        this.id = msg;
        this.street = street;
    }

    public String getId(){
        return this.id;
    }
    public String getStreet(){
        return this.street;
    }
}
