package com.gdtc.sjjms.utils;

public interface HttpCallBackListener {


    void onFinish(String response);


    void onError(Exception e);

}
