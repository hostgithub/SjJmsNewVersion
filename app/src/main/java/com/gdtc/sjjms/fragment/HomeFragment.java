package com.gdtc.sjjms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.adapter.HomeTuijianAdapter;
import com.gdtc.sjjms.adapter.TopAdapter;
import com.gdtc.sjjms.base.BaseFragment;
import com.gdtc.sjjms.base.EndLessOnScrollListener;
import com.gdtc.sjjms.bean.Banners;
import com.gdtc.sjjms.bean.HomeTuijian;
import com.gdtc.sjjms.bean.Kind;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.ui.AllKindActivity;
import com.gdtc.sjjms.ui.HomeKindActivity;
import com.gdtc.sjjms.ui.HomeMeishiTuijianActivity;
import com.gdtc.sjjms.ui.SearchActivity;
import com.gdtc.sjjms.utils.CacheUtil;
import com.gdtc.sjjms.utils.RetrofitUtils;
import com.gdtc.sjjms.widget.CustomScrollView;
import com.qbw.customview.RefreshLoadMoreLayout;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;
import com.zhy.autolayout.AutoRelativeLayout;

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

public class HomeFragment extends BaseFragment implements  RefreshLoadMoreLayout.CallBack {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static HomeFragment homeFragment;//单例模式
    public static HomeFragment getInstance() {
        if(homeFragment==null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    @BindView(R.id.refreshloadmore)
    RefreshLoadMoreLayout mRefreshloadmore;
    @BindView(R.id.custom_scrollview)
    CustomScrollView customScrollView;
    @BindView(R.id.tv_search)
    TextView tv_search;
    protected Handler mHandler = new Handler();

    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.tv_4)
    TextView tv_4;

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;

    @BindView(R.id.all_kind)
    AutoRelativeLayout allKind;

    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    @BindView(R.id.home_viewpager)
    InfiniteViewPager mHomeViewpager;
    @BindView(R.id.home_indicator)
    CirclePageIndicator mHomeIndicator;
    @BindView(R.id.home_header)
    RecyclerViewHeader mHomeHeader;
    private List<Banners.ResultsBean> resultsBeanList;
    private List<Kind.ResultsBean> kindList;
    private List<String> banner_url;
    private Unbinder mUnbinder;

    @BindView(R.id.my_listview)
    com.gdtc.sjjms.widget.MyListView myListView;

    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private ArrayList<HomeTuijian.ResultsBean> list;
    private HomeTuijianAdapter picAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hometest;
    }

    @Override
    public void initVariables() {
        banner_url = new ArrayList<>();
        resultsBeanList = new ArrayList<>();
        kindList = new ArrayList<>();
    }

    @Override
    public void initLoadData() {
    }

    @Override
    protected void lazyFetchData() {
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {

        mUnbinder = ButterKnife.bind(this, view);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //初始化  广告窗体
        mHomeHeader.attachTo(mHomeRecycler,true);
        initBannerData(); //服务器 链接不上  网页404

        initKindData();
        list=new ArrayList();
        initNewsData(1);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager.setAutoMeasureEnabled(true);

        picAdapter=new HomeTuijianAdapter(getActivity(),list);
        //条目点击事件
        picAdapter.setOnItemClickLitener(new HomeTuijianAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {//美食推荐
                //getData(Integer.parseInt(list.get(position)._id));
                Intent intent=new Intent(getContext(), HomeMeishiTuijianActivity.class);
                intent.putExtra(Config.NEWS,list.get(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(picAdapter);

        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                pages++;
                initNewsData(pages);
            }

            @Override
            public void hide() {
            }
            @Override
            public void show() {
            }
        });

    }
//    /**
//     * 设置商店评分等级
//     */
//    public void setGrade(Double grade) {
//        for (int i = 0; i < 5; i++) {
//            ImageView view = new ImageView(getActivity());
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(0, 0, 10, 0);
//            view.setLayoutParams(layoutParams);
//            if (i < (int) Math.floor(grade)) {
//                view.setImageResource(R.drawable.icon_star_full);
//            } else {
//                view.setImageResource(R.drawable.icon_star_empty);
//            }
//            ll_star.addView(view);
//        }
//        tv_grade.setText(grade + "分");
//    }


    /**
     * 网络请求第一页数据
     * @param pages
     */
    private void initNewsData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<HomeTuijian> call=api.getHomeTuijianListData(pages);
        call.enqueue(new Callback<HomeTuijian>() {
            @Override
            public void onResponse(Call<HomeTuijian> call, Response<HomeTuijian> response) {
                if(response.body().getResults().size()!=0| response.body()!=null){
                    list.addAll(response.body().getResults());
                    Log.e("xxxxxx",response.body().toString());
                    picAdapter.notifyDataSetChanged();
                    mRefreshloadmore.stopLoadMore();
                }else {
                    Toast.makeText(getActivity(),R.string.nodata,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeTuijian> call, Throwable t) {
                Toast.makeText(getActivity(),R.string.failure_tip,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //customScrollView.fullScroll(View.FOCUS_UP);
    }

    @Override
    public void onResume() {
        mHomeViewpager.startAutoScroll();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {  //没数据模仿
        initNewsData(1);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshloadmore.stopRefresh();
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {//没数据模仿
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRefreshloadmore.stopLoadMore();
//            }
//        }, 1000);
            pages++;
        Log.e("----------",String.valueOf(pages));
            initNewsData(pages);
    }


    /**
     *初始化 Banner数据
     */
    private void initBannerData() {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<Banners> call=api.getBannerData();
        call.enqueue(new Callback<Banners>() {
            @Override
            public void onResponse(Call<Banners> call, Response<Banners> response) {
                if(response.body()!=null){
                    Banners banners=response.body();
                    CacheUtil cacheUtil=CacheUtil.get(getActivity());
                    cacheUtil.put(Config.CACHE,banners);
                    resultsBeanList=banners.getResults();
                    Log.e("++++++++++",resultsBeanList.get(0).getBusinessInfoId());
                    Log.e("++++++++++",resultsBeanList.get(0).getBusinessName());
                    Log.e("++++++++++",resultsBeanList.get(0).getBusinessTitleImage());
                    Log.e("++++++++++",resultsBeanList.size()+"");

                    TopAdapter adapter = new TopAdapter(getActivity(), resultsBeanList,banner_url);

                    mHomeViewpager.setAdapter(adapter);
                    mHomeViewpager.setAutoScrollTime(3000);
                    mHomeViewpager.startAutoScroll();
                    mHomeIndicator.setViewPager(mHomeViewpager);
                }else{
                    Toast.makeText(getActivity(),"服务器暂时未响应!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {
                Toast.makeText(getActivity(),R.string.failure_tip,Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     *初始化 Banner数据
     */
    private void initKindData() {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<Kind> call=api.getFenlei(0);
        call.enqueue(new Callback<Kind>() {
            @Override
            public void onResponse(Call<Kind> call, Response<Kind> response) {
                if(response.body()!=null){
                    Kind banners=response.body();
                    CacheUtil cacheUtil=CacheUtil.get(getActivity());
                    cacheUtil.put(Config.CACHE,banners);
                    kindList=banners.getResults();
                    Log.e("++++++++++",kindList.get(0).getParentid());
                    tv_1.setText(kindList.get(0).getProductName());
                    tv_2.setText(kindList.get(1).getProductName());
                    tv_3.setText(kindList.get(2).getProductName());
                    tv_4.setText(kindList.get(3).getProductName());
                    Glide.with(getContext()).load(kindList.get(0).getImageurl()).into(image1);
                    Glide.with(getContext()).load(kindList.get(1).getImageurl()).into(image2);
                    Glide.with(getContext()).load(kindList.get(2).getImageurl()).into(image3);
                    Glide.with(getContext()).load(kindList.get(3).getImageurl()).into(image4);

                }else{
                    Toast.makeText(getActivity(),"服务器暂时未响应!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Kind> call, Throwable t) {
                Toast.makeText(getActivity(),R.string.failure_tip,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({ R.id.tv_search,R.id.image1,R.id.image2,R.id.image3,R.id.image4,R.id.all_kind})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.image1:
//                Log.e("++++++++++",kindList.get(0).getParentid());
                Intent intent1=new Intent(new Intent(getActivity(), HomeKindActivity.class));
                intent1.putExtra(Config.NEWS,kindList.get(0).getProductName());
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.image2:
//                Log.e("++++++++++",kindList.get(1).getParentid());
                Intent intent2=new Intent(new Intent(getActivity(), HomeKindActivity.class));
                intent2.putExtra(Config.NEWS,kindList.get(1).getProductName());
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.image3:
//                Log.e("++++++++++",kindList.get(2).getParentid());
                Intent intent3=new Intent(new Intent(getActivity(), HomeKindActivity.class));
                intent3.putExtra(Config.NEWS,kindList.get(2).getProductName());
                startActivity(intent3);
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.image4:
//                Log.e("++++++++++",kindList.get(3).getParentid());
                Intent intent4=new Intent(new Intent(getActivity(), HomeKindActivity.class));
                intent4.putExtra(Config.NEWS,kindList.get(3).getProductName());
                startActivity(intent4);
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.all_kind:
                startActivity(new Intent(getActivity(), AllKindActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            default:
                break;
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onLocationState(LocationStateEvent locationStateEvent) {
//        switch (locationStateEvent.getState()) {
//            case LocationStateEvent.SUCCESS:
//                tv_address.setText(locationStateEvent.getLocationListBean().getCity());
//                break;
//            case LocationStateEvent.FAIL:
//                tv_address.setText("定位中..");
//                break;
//            default:
//                break;
//        }
//    }


//    private void startPermissionsActivity() {
//        PermissionsActivity.startActivityForResult(getActivity(), REQUEST_CODE, PERMISSIONS);
//        getActivity().overridePendingTransition(R.anim.activity_open, 0);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
//        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
//            Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
//        } else if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
//            if (data != null) {
//                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                Toast.makeText(getActivity(), "扫描结果为：" + content, Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == REQUEST_LOCATION && data != null) {
//            LocationListBean locationListBean = (LocationListBean) data.getSerializableExtra("LocationListBean");
//            tv_address.setText(locationListBean.getCity());
//            Toast.makeText(getActivity(), locationListBean.getCity(), Toast.LENGTH_LONG).show();
//        }
//    }
}
