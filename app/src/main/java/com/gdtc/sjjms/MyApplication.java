package com.gdtc.sjjms;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.gdtc.sjjms.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by Administrator on 2016/12/5.
 */

public class MyApplication extends MultiDexApplication
{
    public static IWXAPI mWxApi;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public static MyApplication application;
    private RefWatcher refWatcher;
    public static MyApplication getContext()
    {
        return application;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        application = this;
        registerToWX();
        AutoLayoutConifg.getInstance().useDeviceSize();
        //Fresco.initialize(this);

        //CrashReport.initCrashReport(getApplicationContext());
        //检查内存泄漏
        refWatcher = LeakCanary.install(this);
        LogUtil.init();
    }

    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }

    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, ConstantValue.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ConstantValue.WEIXIN_APP_ID);
    }
}
