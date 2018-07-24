package com.gdtc.sjjms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.MyApplication;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.adapter.TuijianListAdapter;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.bean.TuijianList;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TuijianListActivity extends BaseActivity {


    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;

    private LinearLayoutManager linearLayoutManager;
    private ArrayList<TuijianList.ResultsBean> list;
    private TuijianListAdapter nearbyAdapter;
    private int pages=1;
    private SharePreferenceTools sp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_collect;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        tv_title.setText("推荐菜");
        sp = new SharePreferenceTools(MyApplication.getContext());

        list=new ArrayList();
        initTuijianData(getIntent().getStringExtra(Config.NEWS),1);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setHasFixedSize(true);
        xrecyclerview.setNestedScrollingEnabled(false);
        linearLayoutManager.setAutoMeasureEnabled(true);


        nearbyAdapter=new TuijianListAdapter(this,list);

        //条目点击事件
        nearbyAdapter.setOnItemClickLitener(new TuijianListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(TuijianListActivity.this,PicActivity.class);
                intent.putExtra(Config.NEWS,list.get(position));
                startActivity(intent);
            }
        });

        xrecyclerview.setAdapter(nearbyAdapter);

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pages = 1;
                list.clear();
                nearbyAdapter.notifyDataSetChanged();
                initTuijianData(getIntent().getStringExtra(Config.NEWS),1);
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                pages++;
                initTuijianData(getIntent().getStringExtra(Config.NEWS),pages);
                xrecyclerview.loadMoreComplete();
            }
        });
    }


    private void initTuijianData(String id,int pages) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<TuijianList> call=api.getTuijianData(id,pages);
        call.enqueue(new Callback<TuijianList>() {
            @Override
            public void onResponse(Call<TuijianList> call, Response<TuijianList> response) {

                if(response.body().getResults().size()==0){
                    Toast.makeText(TuijianListActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                }else{
                    list.clear();
                    list.addAll(response.body().getResults());

                    Log.e("xxxxxx",response.body().toString());
                    nearbyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TuijianList> call, Throwable t) {
                Toast.makeText(TuijianListActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getNearbyData(String id,String openId) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<NearbySellerDetailBean> call=api.getNearbySellerDetailData(id,openId);
        call.enqueue(new Callback<NearbySellerDetailBean>() {
            @Override
            public void onResponse(Call<NearbySellerDetailBean> call, Response<NearbySellerDetailBean> response) {
                if(response!=null){
                    NearbySellerDetailBean.ResultsBean nearbySellerDetailBean= response.body().getResults().get(0);
                    Intent intent=new Intent(TuijianListActivity.this, NearSellerActivity.class);
                    intent.putExtra(Config.NEWS,nearbySellerDetailBean);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<NearbySellerDetailBean> call, Throwable t) {
                Toast.makeText(TuijianListActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                TuijianListActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(xrecyclerview != null){
            xrecyclerview.destroy(); // this will totally release XR's memory
            xrecyclerview = null;
        }
    }
}
