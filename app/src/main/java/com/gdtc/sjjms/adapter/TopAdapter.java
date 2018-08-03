package com.gdtc.sjjms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.bean.Banners;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/5.
 */

public class TopAdapter extends InfinitePagerAdapter
{
    private Context mContext;
    //    private List<Banners> mList;
    private List<String> banner_img;
    private List<Banners.ResultsBean> resultsBeanList;
    private List<String> banner_url;

    public TopAdapter(Context context, List<Banners.ResultsBean> resultsBeanList, List<String> banner_url)
    {
        mContext = context;
        this.resultsBeanList = resultsBeanList;
        this.banner_url = banner_url;
    }

    @Override
    public int getItemCount()
    {
//        icon_back mList == null ? 0 : mList.size();
        return resultsBeanList == null ? 0 : resultsBeanList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container)
    {
        View view;
        ViewsHolder holder;
        if (convertView == null)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, container, false);
            holder = new ViewsHolder(view);
            view.setTag(holder);
        } else
        {
            view = convertView;
            holder = (ViewsHolder) view.getTag();
        }

        if(resultsBeanList.get(position).getBusinessTitleImage().isEmpty()){
            holder.mDraweeView.setImageResource(R.drawable.ic_empty_picture);
        }else{
            holder.mDraweeView.setImageURI(resultsBeanList.get(position).getBusinessTitleImage());
        }
        if(resultsBeanList.get(position).getBusinessName().isEmpty()){
            holder.tv_title.setText("");
        }else{
            holder.tv_title.setText(resultsBeanList.get(position).getBusinessName());
        }

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //getData(Integer.parseInt(resultsBeanList.get(position).get_id()));
            }
        });
        return view;
    }

//    private void getData(int id){
//        //使用retrofit配置api
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(Config.BANNER_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Api api =retrofit.create(Api.class);
//        Call<Detail> call=api.getDetailData(id);
//        call.enqueue(new Callback<Detail>() {
//            @Override
//            public void onResponse(Call<Detail> call, Response<Detail> response) {
//                if(response!=null){
//                    Detail detail=response.body();
//                    Detail.ResultsBean resultsBean=detail.getResults().get(0);
//                    Intent intent = new Intent(mContext, DetailActivity.class);
//                    intent.putExtra(Config.NEWS,resultsBean);
//                    mContext.startActivity(intent);
//                    Log.e("xxxxxxx",resultsBean.content);
//                }else{
//                    Toast.makeText(mContext,"数据为空!",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Detail> call, Throwable t) {
//                Toast.makeText(mContext,"请求失败!",Toast.LENGTH_SHORT).show();
//                Log.e("-------------",t.getMessage().toString());
//            }
//        });
//    }

    class ViewsHolder
    {
        @BindView(R.id.top_image)
        SimpleDraweeView mDraweeView;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public ViewsHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
