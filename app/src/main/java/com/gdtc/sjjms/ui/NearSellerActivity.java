package com.gdtc.sjjms.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.ConstantValue;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.WeiXinActivity;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.bean.Collect;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearSellerActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.photoview)
    PhotoView photoView;
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
    private String type;

    private SharePreferenceTools sp;

    private NearbySellerDetailBean.ResultsBean nearbySellerDetailBean;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_near_seller;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        sp=new SharePreferenceTools(getApplicationContext());

        Intent intent=getIntent();
        nearbySellerDetailBean= (NearbySellerDetailBean.ResultsBean) intent.getSerializableExtra(Config.NEWS);


        if(nearbySellerDetailBean.getType().equals("0")){
            iv_coll.setImageResource(R.drawable.food_ic_action_favorite_off_normal);
        }else {
            iv_coll.setImageResource(R.drawable.food_ic_action_favorite_on_normal);
        }
        Glide.with(NearSellerActivity.this).load(nearbySellerDetailBean.getBusinessTitleImage()).into(photoView);
        seller_name.setText(nearbySellerDetailBean.getBusinessName());
        seller_price.setText(nearbySellerDetailBean.getConsumption()+"/人");
        seller_kind.setText(nearbySellerDetailBean.getCategory());
        tv_service_time.setText("营业至"+nearbySellerDetailBean.getEndHours());
        tv_address.setText(nearbySellerDetailBean.getBusinessAddress());

        tv_tel_phone.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        tv_tel_phone.setText(nearbySellerDetailBean.getBusinessPhone());

        seller_translate.setText(nearbySellerDetailBean.getBusinessIntroduction());
        seller_stroy.setText(nearbySellerDetailBean.getBusinessStory());

    }

    @OnClick({ R.id.tv_back,R.id.iv_tuijian,R.id.tv_tel_phone,R.id.iv_coll,R.id.iv_dianping})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                NearSellerActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            case R.id.iv_tuijian://点击进入推荐菜列表

                break;
            case R.id.tv_tel_phone://点击进入推荐菜列表
                callPhone(tv_tel_phone.getText().toString());
                break;
            case R.id.iv_coll://
                if(sp.getString(ConstantValue.WEIXIN_OPENID)!=null){
                    if(nearbySellerDetailBean.getType().equals("0")){
                        getCollect(nearbySellerDetailBean.getBusinessInfoId(),sp.getString(ConstantValue.WEIXIN_OPENID),
                                sp.getString(ConstantValue.WEIXIN_NICKNAME),"0");
                    } else{
                        getCollect(nearbySellerDetailBean.getBusinessInfoId(),sp.getString(ConstantValue.WEIXIN_OPENID),
                                sp.getString(ConstantValue.WEIXIN_NICKNAME),"1");
                    }
                } else {
                    startActivity(new Intent(NearSellerActivity.this, WeiXinActivity.class));
                }
                break;
            case R.id.iv_dianping://

                break;
            default:
                break;
        }
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    /**
     * 收藏
     * @param openId
     */
    private void getCollect(String businessId,String openId,String name,String type) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<Collect> call=api.getCollectData(businessId,openId,name,type);
        call.enqueue(new Callback<Collect>() {
            @Override
            public void onResponse(Call<Collect> call, Response<Collect> response) {
                Log.e("------------------",response.body().toString());
               if(response.body().getInformation().equals("1")){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           iv_coll.setImageResource(R.drawable.food_ic_action_favorite_on_normal);
                           Toast.makeText(NearSellerActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                       }
                   });
               }else {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           iv_coll.setImageResource(R.drawable.food_ic_action_favorite_off_normal);
                           Toast.makeText(NearSellerActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                       }
                   });
               }
            }

            @Override
            public void onFailure(Call<Collect> call, Throwable t) {
                Toast.makeText(NearSellerActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
