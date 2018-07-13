package com.gdtc.sjjms.utils;

import android.content.Context;
import android.widget.AbsListView;

import com.gdtc.sjjms.R;


/**
 * Created by wangjiawei on 2018-4-17.
 */

public class ViewUtil {

    public static EmptyLayout getEmptyLayout(Context context , String text){

        EmptyLayout layout=new EmptyLayout(context);
        layout.setEmptyText(text);
        layout.setEmptyTextSize(dip2px(context,15));
        layout.setEmptyTextColor(context.getResources().getColor(R.color.colorAccent));
        layout.setEmptyDrawable(context.getResources().getDrawable(R.mipmap.icon_empty_image));
        layout.setImgWH(dip2px(context, 69), dip2px(context, 47));
        //这里控制了
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, dip2px(context, 300));
        layout.setLayoutParams(params);

        return layout;
    }


    /**
     * dp转px
     */
    public static int dip2px(Context context ,float dpValue){

        float density=context.getResources().getDisplayMetrics().density;

        return (int)(dpValue*density+0.5);
    }

}
