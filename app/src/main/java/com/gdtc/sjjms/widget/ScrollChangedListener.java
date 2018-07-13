package com.gdtc.sjjms.widget;


/*****************************
 * @Copyright(c) 2014-2018
 * 湖南惟仕信息科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：监听ScrollView滑动距离接口
 * @Version:v1.0.0
 *****************************/
public interface ScrollChangedListener {
    void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt);
}
