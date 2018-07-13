package com.gdtc.sjjms.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/*****************************
 * @Copyright(c) 2014-2018
 * 湖南惟仕信息科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：屏蔽掉ListView的滚动，解决与ScrollView的冲突
 * @Version:v1.0.0
 *****************************/
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //该自定义控件只是重写了ListView的onMeasure方法，使其不会出现滚动条
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
