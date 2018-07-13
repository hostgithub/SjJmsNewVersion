package com.gdtc.sjjms;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.fresco.CommonUtils;
import com.gdtc.sjjms.ui.HomePageActivity;
import com.gdtc.sjjms.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by wangjiawei on 2018-7-10.
 */

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.aw_img)
    SimpleDraweeView mImg;

    private static final int COUNT_DOWN_TIME = 2200;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome);
//        init();
//    }

    @Override
    protected int getLayoutId() {
        Fresco.initialize(this);
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        init();
    }

    public void init(){
        List<String> imgList = getImgData();
        int page = CommonUtils.getRandomNumber(0, imgList.size() - 1);

        FrescoUtils.loadAssetsPic(mImg,imgList.get(page));
        mImg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();

        Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
                WelcomeActivity.this.finish();
            }
        });
    }

    private List<String> getImgData() {
        List<String> imgs = new ArrayList<>();
        imgs.add("bg_1.jpg");
        imgs.add("bg_2.jpg");
        imgs.add("bg_3.jpg");
        imgs.add("bg_4.jpg");
        imgs.add("bg_5.jpg");
        return imgs;
    }
}
