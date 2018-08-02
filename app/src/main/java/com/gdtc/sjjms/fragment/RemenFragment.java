package com.gdtc.sjjms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.ConstantValue;
import com.gdtc.sjjms.MyApplication;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.adapter.RemenAdapter;
import com.gdtc.sjjms.base.BaseFragment;
import com.gdtc.sjjms.bean.NearbySellerBean;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.ui.NearSellerActivity;
import com.gdtc.sjjms.ui.SearchActivity;
import com.gdtc.sjjms.utils.RecyclerViewSpacesItemDecoration;
import com.gdtc.sjjms.utils.RetrofitUtils;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemenFragment extends BaseFragment {


    private static final String TAG = RemenFragment.class.getSimpleName();
    private static RemenFragment remenFragment;//单例模式
    public static RemenFragment getInstance() {
        if(remenFragment==null){
            remenFragment = new RemenFragment();
        }
        return remenFragment;
    }
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;
    private ArrayList<NearbySellerBean.ResultsBean> list;
    private RemenAdapter picAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private int pages=1;

    private SharePreferenceTools sp;

    private Unbinder mUnbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_remen;
    }

    @Override
    public void initVariables() {

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

        sp = new SharePreferenceTools(MyApplication.getContext());
        list=new ArrayList();
        initNewsData(1);
        //设置布局管理器为2列，纵向
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,1);//top间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,1);//底部间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,10);//左间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,10);//右间距

        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        picAdapter=new RemenAdapter(getActivity(),list);
        //条目点击事件
        picAdapter.setOnItemClickLitener(new RemenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getNearbyData(list.get(position).getBusinessInfoId(),sp.getString(ConstantValue.WEIXIN_OPENID));
                //Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(picAdapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pages = 1;
                list.clear();
                initNewsData(1);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                pages++;
                initNewsData(pages);
                mRecyclerView.loadMoreComplete();
            }
        });

    }

    /**
     * 图片瀑布流 初始化 网络请求第一页数据
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
        Call<NearbySellerBean> call=api.getHotListData(pages);
        call.enqueue(new Callback<NearbySellerBean>() {
            @Override
            public void onResponse(Call<NearbySellerBean> call, Response<NearbySellerBean> response) {
                if(response==null| response.body().getResults().size()==0){
                    Toast.makeText(getActivity(),"暂无数据",Toast.LENGTH_SHORT).show();
                }else {
                    list.addAll(response.body().getResults());
                    Log.e("xxxxxx",response.body().toString());
                    picAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NearbySellerBean> call, Throwable t) {
                Toast.makeText(getActivity(),R.string.failure_tip,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(),R.string.failure_tip,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mRecyclerView != null){
            mRecyclerView.destroy(); // this will totally release XR's memory
            mRecyclerView = null;
        }
    }

    @OnClick({ R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            default:
                break;
        }
    }

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
