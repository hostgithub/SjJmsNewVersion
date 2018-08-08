package com.gdtc.sjjms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.adapter.AllKindAdapter;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.bean.Kind;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.utils.RecyclerViewSpacesItemDecoration;
import com.gdtc.sjjms.utils.RetrofitUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllKindActivity extends BaseActivity {


    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private List<Kind.ResultsBean> list;
    private AllKindAdapter allKindAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_kind;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        tv_title.setText("全部分类");

        list=new ArrayList<>();

        initKindData();

        //设置布局管理器为2列，纵向
        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
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

        allKindAdapter=new AllKindAdapter(this,list);

        allKindAdapter.setOnItemClickLitener(new AllKindAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(AllKindActivity.this,""+position,Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(new Intent(AllKindActivity.this, HomeKindActivity.class));
                intent1.putExtra(Config.NEWS,list.get(position).getProductName());
                startActivity(intent1);
                overridePendingTransition(R.anim.activity_open, 0);
            }
        });

        mRecyclerView.setAdapter(allKindAdapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                list.clear();
                allKindAdapter.notifyDataSetChanged();
                initKindData();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
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
        Call<Kind> call=api.getAllKind();
        call.enqueue(new Callback<Kind>() {
            @Override
            public void onResponse(Call<Kind> call, Response<Kind> response) {
                if(response.body()!=null){
                    Log.e("++++++++++",response.body().getResults().get(0).getProductName());
                    list.addAll(response.body().getResults());
                    allKindAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(AllKindActivity.this,"服务器暂时未响应!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Kind> call, Throwable t) {
                Toast.makeText(AllKindActivity.this,R.string.failure_tip,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                AllKindActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRecyclerView != null){
            mRecyclerView.destroy(); // this will totally release XR's memory
            mRecyclerView = null;
        }
    }
}
