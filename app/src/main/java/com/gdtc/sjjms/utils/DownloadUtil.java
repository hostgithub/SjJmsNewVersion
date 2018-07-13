package com.gdtc.sjjms.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by llf on 2016/10/21.
 * apk文件下载工具
 */

public class DownloadUtil {
    public static final String DOWNLOAD_APK_ID_PREFS = "download_apk_id";

    public static void downloadApk(Context context, String url, String name, String description, String storeApk) {
        if (!isDownloadManagerAvailable()||!sdAvailable()) {
            return;
        }

        DownloadManager.Request request;
        try {
            request = new DownloadManager.Request(Uri.parse(url));
        } catch (Exception e) {
            return;
        }

        request.setTitle(name);
        request.setDescription(description);
        //无线网状态下下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置漫游状态下不下载
        //request.setAllowedOverRoaming(false);
        //设置漫游状态下也下载
        request.setAllowedOverRoaming(true);
        request.setMimeType("application/vnd.android.package-archive");
        //HONEYCOMB版本是11
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //是否允许本MediaScanner扫描
            request.allowScanningByMediaScanner();
            //下载时在通知栏显示下载信息,下载完成后保留下载信息,用Request.VISIBILITY_VISIBLE，下载完成后不保留下载信息
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        //指定下载后的存储位置
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, storeApk);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long id = manager.enqueue(request);
        //存储下载的序列号，用于自动更新
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(DOWNLOAD_APK_ID_PREFS,id).apply();
    }

    // SDK大于9才能使用DownloadManager
    private static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    //判断Sd卡是否可用
    private static boolean sdAvailable(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 下载新版本
     *
     * @param context
     * @param url
     */
    public static void downLoadAPK(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        try {
            String serviceString = Context.DOWNLOAD_SERVICE;
            final DownloadManager downloadManager = (DownloadManager) context.getSystemService(serviceString);

            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.allowScanningByMediaScanner();
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setMimeType("application/vnd.android.package-archive");

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jms/","app-release-jms.gov.apk");
            if (file.exists()){
                file.delete();
            }
            request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jms/", "app-release-jms.gov.apk");
            long refernece = downloadManager.enqueue(request);
            SharePreferenceTools sp=new SharePreferenceTools(context);
            sp.putLong("refernece", refernece);
        } catch (Exception exception) {
            ToastUtil.sToastUtil.shortDuration("更新失败").show();
        }

    }
}
