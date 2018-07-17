package com.gdtc.sjjms;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.fresco.CommonUtils;
import com.gdtc.sjjms.ui.HomePageActivity;
import com.gdtc.sjjms.utils.FrescoUtils;
import com.gdtc.sjjms.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by wangjiawei on 2018-7-10.
 */

public class WelcomeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.aw_img)
    SimpleDraweeView mImg;
    public static final int PERMISSION = 100;

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
        /**
         * 6.0系统动态权限申请需要
         */
        String[] params = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(WelcomeActivity.this, params)) {
            Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
                    WelcomeActivity.this.finish();
                }
            });
        }
        else {
            EasyPermissions.requestPermissions(WelcomeActivity.this, "应用需要权限才能安全运行", android.R.string.ok,
                    android.R.string.cancel,PERMISSION, params);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
                WelcomeActivity.this.finish();
            }
        });
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode) {
            case PERMISSION:
                //引导用户跳转到设置界面
                new AppSettingsDialog.Builder(WelcomeActivity.this, "希望您通过权限")
                        .setTitle("权限设置")
                        .setPositiveButton("设置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setRequestCode(PERMISSION)
                        .build()
                        .show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void init(){
        List<String> imgList = getImgData();
        int page = CommonUtils.getRandomNumber(0, imgList.size() - 1);

        FrescoUtils.loadAssetsPic(mImg,imgList.get(page));
        mImg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();

//        Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
//            @Override
//            public void call(Long aLong) {
//                startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
//                WelcomeActivity.this.finish();
//            }
//        });
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

    private void skip() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
                LogUtil.d("最高可用内存:" + maxMemory);
//                startThenKill(MainActivity.class);//-------------------------------------------------------------------
                startThenKill(HomePageActivity.class);//-------------------------------------------------------------------
                WelcomeActivity.this.overridePendingTransition(R.anim.scale_in, R.anim.shrink_out);
            }
        }, 1000 * 2);
    }
}
