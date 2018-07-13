package com.gdtc.sjjms.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdtc.sjjms.R;


/**
 * Created by wangjiawei on 2018-4-17.
 */

public class EmptyLayout extends LinearLayout {

    /**
     * 空页面图片
     */
    private ImageView emptyImg;
    /**
     * 空页面文本
     */
    private TextView emptyTv;
    /**
     * 空文字
     */
    private String emptyText;
    /**
     * 空文字颜色
     */
    private int emptyTextColor;
    /**
     * 空文字大小
     */
    private float emptyTextSize;

    /**
     * 空图片
     */
    private Drawable emptyDrawable;
    /**
     * 图片宽度
     */
    private int imgWidth;
    /**
     * 图片高度
     */
    private int imgHeight;

    public EmptyLayout(Context context) {
        this(context ,null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化布局
        initView();
        //初始化参数
        initDatas();
    }

    /**
     * 初始化布局
     */
    private void initView(){

        View view= LayoutInflater.from(getContext()).inflate(R.layout.empty_layout,this);//注意inflate方法的parent传入LinearLayout

        emptyImg= (ImageView) view.findViewById(R.id.common_iv);
        emptyTv= (TextView) view.findViewById(R.id.common_tv);
    }

    /**
     * 设置文本
     */
    public void setEmptyText(String emptyText) {

        this.emptyText=emptyText;

        if(emptyText!=null)
            emptyTv.setText(emptyText);
    }

    /**
     * 设置文本颜色
     */
    public void setEmptyTextColor(int emptyTextColor) {

        this.emptyTextColor=emptyTextColor;

        emptyTv.setTextColor(emptyTextColor);
    }

    /**
     * 设置空文本字体大小
     *
     * @param emptyTextSize 单位是sp
     */
    public void setEmptyTextSize(float emptyTextSize) {

        this.emptyTextSize=emptyTextSize;

        emptyTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,emptyTextSize);

    }
    /**
     * 设置图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setEmptyDrawable(Drawable drawable) {

        this.emptyDrawable=drawable;

        emptyImg.setBackground(drawable);
    }

    /**
     * 设置图片的宽度和高度
     */
    public void setImgWH(int imgWidth ,int imgHeight) {

        this.imgHeight=imgHeight;
        this.imgWidth=imgWidth;

        ViewGroup.LayoutParams lp=emptyImg.getLayoutParams();
        lp.width=imgWidth;
        lp.height=imgHeight;
        emptyImg.setLayoutParams(lp);
    }

    /**
     * 初始化参数
     */
    public void initDatas(){
        setEmptyText(emptyText);
        setEmptyTextColor(emptyTextColor);
        setEmptyTextSize(emptyTextSize);
        setEmptyDrawable(emptyDrawable);
        setImgWH(imgWidth,imgHeight);
    }

}
