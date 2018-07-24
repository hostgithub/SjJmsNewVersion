package com.gdtc.sjjms.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.bean.TuijianList;

import butterknife.BindView;
import butterknife.OnClick;

public class PicActivity extends BaseActivity {

    @BindView(R.id.seller_name)
    TextView seller_name;

    @BindView(R.id.seller_price)
    TextView seller_price;

    @BindView(R.id.seller_stroy)
    TextView seller_stroy;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_image)
    ImageView iv_image;

    private TuijianList.ResultsBean resultsBean;

    @Override
    protected int getLayoutId() {

        /**全屏设置，隐藏窗口所有装饰**/
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
//        requestWindowFeature(Window.FEATURE_NO_TITLE);


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT); //也可以设置成灰色透明的，比较符合Material Design的风格
        }

        return R.layout.activity_pic;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        resultsBean= (TuijianList.ResultsBean) getIntent().getSerializableExtra(Config.NEWS);
        seller_name.setText(resultsBean.getGoodsName());
        seller_price.setText("￥"+resultsBean.getGoodsMoney());
        seller_stroy.setText(resultsBean.getGoodsExplain());
        Glide.with(PicActivity.this).load(resultsBean.getGoodsImage()).into(iv_image);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                PicActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            default:
                break;
        }
    }
}
