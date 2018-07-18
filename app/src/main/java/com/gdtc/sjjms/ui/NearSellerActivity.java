package com.gdtc.sjjms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;

import butterknife.BindView;
import butterknife.OnClick;

public class NearSellerActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.iv_tuijian)
    ImageView iv_tuijian;
    @BindView(R.id.iv_coll)//收藏
    ImageView iv_coll;
    @BindView(R.id.iv_dianping)//点评
    ImageView iv_dianping;

    @BindView(R.id.seller_name)
    TextView seller_name;
    @BindView(R.id.seller_price)
    TextView seller_price;
    @BindView(R.id.seller_kind)
    TextView seller_kind;
    @BindView(R.id.seller_location)
    TextView seller_location;
    @BindView(R.id.tv_service_time)
    TextView tv_service_time;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_tel_phone)
    TextView tv_tel_phone;

    @BindView(R.id.seller_translate)
    TextView seller_translate;
    @BindView(R.id.seller_stroy)
    TextView seller_stroy;
    @BindView(R.id.zhaopai_1)
    TextView zhaopai_1;
    @BindView(R.id.zhaopai_2)
    TextView zhaopai_2;

    private NearbySellerDetailBean.ResultsBean nearbySellerDetailBean;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_near_seller;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        Intent intent=getIntent();
        nearbySellerDetailBean= (NearbySellerDetailBean.ResultsBean) intent.getSerializableExtra(Config.NEWS);

        seller_name.setText(nearbySellerDetailBean.getBusinessName());
        seller_price.setText(nearbySellerDetailBean.getConsumption());
        seller_kind.setText(nearbySellerDetailBean.getCategory());
        tv_service_time.setText("营业至"+nearbySellerDetailBean.getEndHours());
        tv_address.setText(nearbySellerDetailBean.getBusinessAddress());
        tv_tel_phone.setText(nearbySellerDetailBean.getBusinessPhone());

        seller_translate.setText(nearbySellerDetailBean.getBusinessIntroduction());
        seller_stroy.setText(nearbySellerDetailBean.getBusinessStory());

    }

    @OnClick({ R.id.tv_back,R.id.iv_tuijian})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                NearSellerActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            case R.id.iv_tuijian://点击进入推荐菜列表

                break;
            default:
                break;
        }
    }
}
