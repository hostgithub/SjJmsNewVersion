package com.gdtc.sjjms.wxapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gdtc.sjjms.ConstantValue;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by wangjiawei on 2018-7-19.
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ConstantValue.WEIXIN_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                SharedPreferences WxSp = getApplicationContext().getSharedPreferences(ConstantValue.spName, Context.MODE_PRIVATE);
                SharedPreferences.Editor WxSpEditor = WxSp.edit();
                WxSpEditor.putString(ConstantValue.CODE,code);
                WxSpEditor.apply();
                Intent intent = new Intent();
                intent.setAction("authlogin");
                WXEntryActivity.this.sendBroadcast(intent);
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                result = "发送返回";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

}
