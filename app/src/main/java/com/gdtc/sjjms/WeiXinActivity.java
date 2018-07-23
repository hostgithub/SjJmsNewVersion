package com.gdtc.sjjms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.utils.HttpCallBackListener;
import com.gdtc.sjjms.utils.HttpUtil;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.Log;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;


/**
    * 授权登录并且拿取Wx用户信息，简单三部曲：
    * 1.sendReq(req). 用户授权可以拿到 code
    *
    * 2.用code.调用Wx接口拿到 openid & accessToken
    *
    * 3.通过openid & accessToken 俩参数可以拿到最终用户信息
    *
    */

public class WeiXinActivity extends BaseActivity {

    public static final String TAG = "WeiXinActivity";
    @BindView(R.id.wechat_login)
    AutoRelativeLayout wechat_login;

    private String unionid;
    private String id;
    private String nickName = null;
    private String sex = null;
    private String city = null;
    private String province = null;
    private String country = null;
    private String headimgurl = null;


    private SharePreferenceTools sharePreferenceTools;
    private IWXAPI api;
    private ReceiveBroadCast receiveBroadCast;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weixin;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        sharePreferenceTools=new SharePreferenceTools(getApplicationContext());

        wechat_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weChatAuth();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("authlogin");
        registerReceiver(receiveBroadCast, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiveBroadCast);
    }


    private void weChatAuth() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(WeiXinActivity.this, ConstantValue.WEIXIN_APP_ID, true);
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        api.sendReq(req);
    }




    public void getAccessToken() {
        SharedPreferences WxSp = getApplicationContext()
                .getSharedPreferences(ConstantValue.spName, Context.MODE_PRIVATE);
        String code = WxSp.getString(ConstantValue.CODE, "");
        final SharedPreferences.Editor WxSpEditor = WxSp.edit();
        Log.d(TAG, "-----获取到的code----" + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + ConstantValue.WEIXIN_APP_ID
                + "&secret="
                + ConstantValue.WEIXIN_APP_Secret
                + "&code="
                + code
                + "&grant_type=authorization_code";
        Log.d(TAG, "--------即将获取到的access_token的地址--------");
        HttpUtil.sendHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {


                //解析以及存储获取到的信息
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "-----获取到的json数据1-----" + jsonObject.toString());
                    String access_token = jsonObject.getString("access_token");
                    Log.d(TAG, "--------获取到的access_token的地址--------" + access_token);
                    String openid = jsonObject.getString("openid");
                    String refresh_token = jsonObject.getString("refresh_token");
                    if (!access_token.equals("")) {
                        WxSpEditor.putString(ConstantValue.ACCESS_TOKEN, access_token);
                        WxSpEditor.apply();
                    }
                    if (!refresh_token.equals("")) {
                        WxSpEditor.putString(ConstantValue.REFRESH_TOKEN, refresh_token);
                        WxSpEditor.apply();
                    }
                    if (!openid.equals("")) {
                        WxSpEditor.putString(ConstantValue.WXOPENID, openid);
                        WxSpEditor.apply();
                        getPersonMessage(access_token, openid);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(WeiXinActivity.this, "通过code获取数据没有成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getPersonMessage(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        HttpUtil.sendHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    id = jsonObject.getString("openid");
                    nickName = jsonObject.getString("nickname");
                    sex = jsonObject.getString("sex");
                    city = jsonObject.getString("city");
                    province = jsonObject.getString("province");
                    country = jsonObject.getString("country");
                    headimgurl = jsonObject.getString("headimgurl");
                    unionid = jsonObject.getString("unionid");

                    sharePreferenceTools.putString(ConstantValue.WEIXIN_OPENID,id);
                    sharePreferenceTools.putString(ConstantValue.WEIXIN_HEADURL,headimgurl);
                    sharePreferenceTools.putString(ConstantValue.WEIXIN_NICKNAME,nickName);

//                    Intent intent1 = new Intent(WeiXinActivity.this, HomePageActivity.class);
//                    startActivity(intent1);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(WeiXinActivity.this, "通过openid获取数据没有成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ReceiveBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getAccessToken();

        }
    }
}
