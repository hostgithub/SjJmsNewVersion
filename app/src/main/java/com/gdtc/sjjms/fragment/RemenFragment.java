package com.gdtc.sjjms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.adapter.RemenAdapter;
import com.gdtc.sjjms.base.BaseFragment;
import com.gdtc.sjjms.bean.NewCenter;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.ui.SearchActivity;
import com.gdtc.sjjms.utils.RecyclerViewSpacesItemDecoration;
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
    private ArrayList<NewCenter.ResultsBean> list;
    private RemenAdapter picAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private int pages=1;

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
                //getData(Integer.parseInt(list.get(position)._id));
                Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
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
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<NewCenter> call=api.getNewCenterData("000100050007",pages);
        call.enqueue(new Callback<NewCenter>() {
            @Override
            public void onResponse(Call<NewCenter> call, Response<NewCenter> response) {
                list.addAll(response.body().results);
                Log.e("xxxxxx",response.body().toString());
                picAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewCenter> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
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
