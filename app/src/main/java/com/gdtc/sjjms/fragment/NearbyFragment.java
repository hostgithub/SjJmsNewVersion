package com.gdtc.sjjms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.ConstantValue;
import com.gdtc.sjjms.MyApplication;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.adapter.NearbyAdapter;
import com.gdtc.sjjms.base.BaseFragment;
import com.gdtc.sjjms.bean.AreaOneBean;
import com.gdtc.sjjms.bean.AreaTwoBean;
import com.gdtc.sjjms.bean.NearbySellerBean;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.event.EventUtil;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.ui.NearSellerActivity;
import com.gdtc.sjjms.ui.SearchActivity;
import com.gdtc.sjjms.utils.DoubleListPopViewUtil;
import com.gdtc.sjjms.utils.RetrofitUtils;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.gdtc.sjjms.utils.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearbyFragment extends BaseFragment {

    private Unbinder mUnbinder;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<NearbySellerBean.ResultsBean> list;
    private NearbyAdapter nearbyAdapter;
    private int pages=1;
    private String nearId;

    private SharePreferenceTools sp;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String longitude;
    private String latitude;
    private String district;
    @BindView(R.id.iv_refresh)
    ImageView iv_refresh;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.rl_location)
    RelativeLayout rl_location;

    @BindView(R.id.tv_fujin)
    TextView tv_fujin;
    private int selectedPosition;//根目录被选中的节点
    private DoubleListPopViewUtil areaPopupWindow;
    private List<AreaOneBean.ResultsBean> rootList;//根目录的节点
//    private List<List<String>> subItemList;// 子目录节点
    private List<AreaTwoBean.ResultsBean> subItemList;// 子目录节点

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nearby;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        //初始化定位
        sp = new SharePreferenceTools(MyApplication.getContext());
        initLocation();
        startLocation();
        rootList = new ArrayList<>();
        subItemList = new ArrayList<>();

        initAreaOneData();

        list=new ArrayList();
//        initNearbyJWData(1,longitude,latitude);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setHasFixedSize(true);
        xrecyclerview.setNestedScrollingEnabled(false);
        linearLayoutManager.setAutoMeasureEnabled(true);


        nearbyAdapter=new NearbyAdapter(getActivity(),list);

        //条目点击事件
        nearbyAdapter.setOnItemClickLitener(new NearbyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getNearbyData(list.get(position).getBusinessInfoId(),sp.getString(ConstantValue.WEIXIN_OPENID));
//                Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getContext(), NearSellerActivity.class));
            }
        });

        xrecyclerview.setAdapter(nearbyAdapter);
//        xrecyclerview.setAdapter(nearbyAdapter);

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pages = 1;
                Log.e("-----------",tv_fujin.getText().toString());
                if(!tv_fujin.getText().toString().equals("附近")){
                    list.clear();
                    nearbyAdapter.notifyDataSetChanged();
                    initNearbyData(1,nearId);
                }else {
                    list.clear();
                    nearbyAdapter.notifyDataSetChanged();
                    initNearbyJWData(1,longitude,latitude,district);
                }
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                pages++;
                if(!tv_fujin.getText().toString().equals("附近")){
                    initNearbyData(pages,nearId);
                }else {
                    initNearbyJWData(pages,longitude,latitude,district);
                }
                xrecyclerview.loadMoreComplete();
            }
        });
    }

    @Override
    public void initLoadData() {
    }

    @Override
    protected void lazyFetchData() {

    }


    private void initAreaOneData() {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AreaOneBean> call=api.getAreaOneBeanData();
        call.enqueue(new Callback<AreaOneBean>() {
            @Override
            public void onResponse(Call<AreaOneBean> call, Response<AreaOneBean> response) {
                rootList.addAll(response.body().getResults());
                Log.e("xxxxxx",response.body().toString());
            }

            @Override
            public void onFailure(Call<AreaOneBean> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 地区选择的popupwindow
     */
    private void showAreaPopBtn() {

        areaPopupWindow = new DoubleListPopViewUtil(getContext(), tv_fujin, rootList) {
            @Override
            public void onRootListviewOnClick(View v, int position) {
                selectedPosition = position;
                //initAreaTwoData(rootList.get(position).getRegionalId());
            }

            @Override
            public void onSubListviewOnClick(View v, int position) {
                //tv_fujin.setText(subItemList.get(position).getRegionalStreet());
                list.clear();
                nearbyAdapter.notifyDataSetChanged();
                areaPopupWindow.dismiss();
            }
        };
        areaPopupWindow.show();
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(getContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }





    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }


    @OnClick({ R.id.iv_refresh,R.id.tv_fujin,R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_refresh:
                tv_fujin.setText("附近");
                startLocation();
                break;
            case R.id.tv_fujin:
//                initAreaOneData();
                showAreaPopBtn();
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            default:
                break;
        }
    }


    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        tv_location.setText("正在定位");
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
    }

    /*个人建议在onPause注册EventBus(将当前Activity注册为事件订阅者)
    *不影响功能的情况下提早解除注册，尽可能少的占用内存
    */
    @Override
    public void onPause() {
        super.onPause();
    }


    // 接收函数二
    @Subscribe
    public void onEventMainThread(EventUtil event){
        String msglog = "----onEventBackground收到了消息："+event.getStreet();
        Log.e("EventBus",msglog);
        tv_fujin.setText(event.getStreet());
        nearId=event.getId();
        Log.e("------EventBus-------",event.getId());
        initNearbyData(1,event.getId());

    }


    private void initNearbyData(int pages,String id) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<NearbySellerBean> call=api.getNearbySellerData(pages,id);
        call.enqueue(new Callback<NearbySellerBean>() {
            @Override
            public void onResponse(Call<NearbySellerBean> call, Response<NearbySellerBean> response) {

               if(response.body().getResults()==null){
                   Toast.makeText(getContext(),"暂无数据",Toast.LENGTH_SHORT).show();
               }else{
                   //list.clear();
                   list.addAll(response.body().getResults());
                   Log.e("xxxxxx",response.body().toString());
                   nearbyAdapter.notifyDataSetChanged();
               }
            }

            @Override
            public void onFailure(Call<NearbySellerBean> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getNearbyData(String id,String openId) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<NearbySellerDetailBean> call=api.getNearbySellerDetailData(id,openId);
        call.enqueue(new Callback<NearbySellerDetailBean>() {
            @Override
            public void onResponse(Call<NearbySellerDetailBean> call, Response<NearbySellerDetailBean> response) {
                if(response!=null&&response.body().getResults().size()!=0){
                    NearbySellerDetailBean.ResultsBean nearbySellerDetailBean= response.body().getResults().get(0);
                    Intent intent=new Intent(getContext(), NearSellerActivity.class);
                    intent.putExtra(Config.NEWS,nearbySellerDetailBean);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<NearbySellerDetailBean> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(final AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    tv_location.setText(location.getStreet());
                    longitude= String.valueOf(location.getLongitude());
                    latitude= String.valueOf(location.getLatitude());
                    district= location.getDistrict();
                    initNearbyJWData(1,longitude,latitude,location.getDistrict());
                    stopLocation();
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                Log.e("-----------",result);
            } else {
                tv_location.setText("定位失败，loc is null");
                stopLocation();
            }
        }
    };


    private void initNearbyJWData(int pages,String jingdu,String weidu,String district) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<NearbySellerBean> call=api.getNearbySellerListData(pages,jingdu,weidu,district);
        call.enqueue(new Callback<NearbySellerBean>() {
            @Override
            public void onResponse(Call<NearbySellerBean> call, Response<NearbySellerBean> response) {

                if(response.body().getResults()==null){
                    Toast.makeText(getContext(),"暂无数据",Toast.LENGTH_SHORT).show();
                }if(response.body().getResults().size()==0){
                    Toast.makeText(getContext(),"暂无更多数据",Toast.LENGTH_SHORT).show();
                }else{
                    list.clear();
                    list.addAll(response.body().getResults());

                    Log.e("---getBusinessInfoId--",response.body().getResults().get(0).getBusinessInfoId());
                    nearbyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NearbySellerBean> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
