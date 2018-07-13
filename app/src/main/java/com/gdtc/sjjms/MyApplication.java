package com.gdtc.sjjms;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.gdtc.sjjms.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by Administrator on 2016/12/5.
 */

public class MyApplication extends MultiDexApplication
{


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
}
