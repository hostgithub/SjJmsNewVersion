package com.gdtc.sjjms.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.impl.ActionBarClickListener;
import com.gdtc.sjjms.widget.TranslucentActionBar;
import com.gdtc.sjjms.widget.TranslucentScrollView;

import butterknife.BindView;

public class SellerDetailActivity extends BaseActivity implements ActionBarClickListener, TranslucentScrollView.TranslucentChangedListener{


    @BindView(R.id.custom_scrollview)
    TranslucentScrollView translucentScrollView;
    @BindView(R.id.actionbar)
    TranslucentActionBar actionBar;
    @BindView(R.id.iv_image)
    ImageView zoomView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        init();
    }

    private void init() {
//        actionBar = (TranslucentActionBar) findViewById(R.id.actionbar);
        //初始actionBar
        actionBar.setData("我的", 0, null, 0, null, null);
        //开启渐变
        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(20);

//        translucentScrollView = (TranslucentScrollView) findViewById(R.id.pullzoom_scrollview);
        //设置透明度变化监听
        translucentScrollView.setTranslucentChangedListener(this);
        //关联需要渐变的视图
        translucentScrollView.setTransView(actionBar);
        //设置ActionBar键渐变颜色
//        translucentScrollView.setTransColor(getResources().getColor(R.color.orange_dft));
        translucentScrollView.setTransColor(getResources().getColor(R.color.white));

//        zoomView = findViewById(R.id.lay_header);
        //关联伸缩的视图
        translucentScrollView.setPullZoomView(zoomView);
    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onTranslucentChanged(int transAlpha) {
        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
    }
}
