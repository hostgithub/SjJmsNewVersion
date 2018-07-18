package com.gdtc.sjjms.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author xiaoke
 */
public class SpUtils {
    private final static String ZTB_SP="ztb_sp";

    private final static String ISFIRST = "is_first";
    private final static String ISMAINFIRST = "is_main_first";

    private final static String CHAT_READ="chat_read";//阅后即焚用

    private final static String SCREEN_WITH="screen_with";

    private static final String COOKIE="cookie";


    private static final String MESS_SP="mess_sp";

    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(ZTB_SP, Context.MODE_PRIVATE);
    }

    /**
     * 获取消息Sp
     * @param context
     * @return
     */
    public static SharedPreferences getMessSp(Context context) {
        return context.getSharedPreferences(MESS_SP, Context.MODE_PRIVATE);
    }


    /**
     * 是否第一次进入app
     *
     * @param context
     * @param isFirst
     */
    public static void setIsFirst(Context context, boolean isFirst) {
        getSp(context).edit().putBoolean(ISFIRST, isFirst).commit();
    }

    public static boolean getIsFirst(Context context) {
        return getSp(context).getBoolean(ISFIRST, true);
    }
    public static void setMainIsFirst(Context context, boolean isFirst) {
        getSp(context).edit().putBoolean(ISMAINFIRST, isFirst).commit();
    }

    public static boolean getMainIsFirst(Context context) {
        return getSp(context).getBoolean(ISMAINFIRST, true);
    }

    public static void setRead(Context context, boolean isReadDestory){
        getSp(context).edit().putBoolean(CHAT_READ, isReadDestory).commit();
    }



    public static void setScreenWith(Context context, int screenWith){
        getSp(context).edit().putInt(SCREEN_WITH, screenWith).commit();
    }

    public static int getScreenWith(Context context){
        return getSp(context).getInt(SCREEN_WITH, 0);
    }


    /**
     * 保存cookie
     * @param context
     * @param cookie
     */
    public static void setCooike(Context context, String cookie){
        getSp(context).edit().putString(COOKIE, cookie).commit();
    }

    /**
     * 获取cookie
     * @param context
     * @return
     */
    public static String getCookie(Context context){
        return getSp(context).getString(COOKIE, "");
    }


}
