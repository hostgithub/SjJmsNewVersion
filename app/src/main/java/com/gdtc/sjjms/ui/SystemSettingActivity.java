package com.gdtc.sjjms.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.sjjms.ConstantValue;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.utils.SharePreferenceTools;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemSettingActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.ll_clear)
    TextView ll_clear;
    @BindView(R.id.ll_update)
    TextView ll_update;
    @BindView(R.id.ll_exit)
    TextView ll_exit;

    private SharePreferenceTools sp;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        sp=new SharePreferenceTools(getApplicationContext());
    }

    @OnClick({R.id.tv_back,R.id.ll_clear,R.id.ll_update,R.id.ll_exit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_clear:
                Toast.makeText(getApplicationContext(),"清除缓存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_update:
                Toast.makeText(getApplicationContext(),"已经是最新版本",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_exit:
                sp.remove(ConstantValue.WEIXIN_OPENID);
                sp.remove(ConstantValue.WEIXIN_HEADURL);
                sp.remove(ConstantValue.WEIXIN_NICKNAME);
                finish();
                break;
            default:
                break;
        }
    }
}
