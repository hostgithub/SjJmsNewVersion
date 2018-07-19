package com.gdtc.sjjms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseViewHolder;
import com.gdtc.sjjms.bean.NearbySellerBean;
import com.gdtc.sjjms.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llf on 2017/4/19.
 * 发现的适配器，分为两种样式
 */

public class NearbyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_NOIMAGE = 1;
    private static final int ITEM_HASIMAGE = 2;

    private ArrayList<NearbySellerBean.ResultsBean> datas;
    private Context mContext;
    private int viewFooter;
    private View footerView;
    private OnItemClickListener mOnItemClickListener;

    public NearbyAdapter(Context context, ArrayList<NearbySellerBean.ResultsBean> datas) {
        this.datas = datas;
        this.mContext = context;
    }

    public void replaceAll(List<NearbySellerBean.ResultsBean> elements) {
        if (datas.size() > 0) {
            datas.clear();
        }
        datas.addAll(elements);
        notifyDataSetChanged();
    }

    public void addAll(List<NearbySellerBean.ResultsBean> elements) {
        datas.addAll(elements);
        notifyDataSetChanged();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NOIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_nearby, parent, false));
        } else if (viewType == ITEM_HASIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_nearby, parent, false));
        } else {
            footerView = LayoutInflater.from(mContext).inflate(viewFooter, parent, false);
            return new BaseViewHolder(footerView);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (!(viewFooter != 0 && position == getItemCount() - 1)) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }

//            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
//            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//            holder.itemView.set

            int type = getItemViewType(position);
            final NearbySellerBean.ResultsBean item = datas.get(position);





            //CircleImageView avatar = holder.getView(R.id.avatar);
//            if(item.text.contains(";")){//返回的json中含有分号 一开始用的是startWith方法 结果有两个分号 只能去掉一个
//                String str=item.text.replace(';', ' ');
//                holder.setText(R.id.title, str);
//            }else{
//                holder.setText(R.id.title, item.text);
//            }

            ImageLoaderUtils.loadingImg(mContext, (ImageView) holder.getView(R.id.seller_image), item.getBusinessTitleImage());
            holder.setText(R.id.seller_name, item.getBusinessName());
            holder.setText(R.id.seller_price,  "人均消费:"+item.getConsumption());
            holder.setText(R.id.seller_kind,  item.getCategory());
            holder.setText(R.id.seller_location,  item.getBusinessAddress());

//            holder.setText(R.id.author, item.getAuthor());
//            holder.setText(R.id.seeNum, item.getWatch());
//            holder.setText(R.id.commentNum, item.getComments());
//            holder.setText(R.id.loveNum, item.getLike());
//            ImageLoaderUtils.loadingImg(mContext, avatar, HOST + item.getAuthorImg());
//            avatar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    WebViewActivity.lanuch(mContext, HOST + "/member/index.php?uid" + item.getAuthor());
//                }
//            });

        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size());
        if (viewFooter != 0) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_HASIMAGE;
        if (viewFooter != 0 && position == getItemCount() - 1) {
            type = TYPE_FOOTER;
            return type;
        }
        if (TextUtils.isEmpty(datas.get(position).getBusinessInfoId())) {
            type = ITEM_NOIMAGE;
        } else {
            type = ITEM_HASIMAGE;
        }
        return type;
    }

    public void addFooterView(int footerView) {
        this.viewFooter = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterVisible(int visible) {
        footerView.setVisibility(visible);
    }

    //设置点击事件
    public void setOnItemClickLitener(OnItemClickListener mLitener) {
        mOnItemClickListener = mLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
